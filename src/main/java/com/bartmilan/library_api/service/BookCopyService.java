package com.bartmilan.library_api.service;

import com.bartmilan.library_api.exception.BookNotAvailableException;
import com.bartmilan.library_api.exception.ResourceNotFoundException;
import com.bartmilan.library_api.model.BookCopy;
import com.bartmilan.library_api.model.enums.BookCopyStatus;
import com.bartmilan.library_api.repository.BookCopyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCopyService {

    private BookCopyRepository bookCopyRepository;

    public BookCopyService(BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }

    public BookCopy getById(Long id) {
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Didn't found book copy with id: " + id));
    }

    public List<BookCopy> getAvailableCopiesByBookId(Long bookId) {
        return bookCopyRepository.findByBook_IdAndStatus(bookId, BookCopyStatus.AVAILABLE);
    }

    public boolean isBookAvailable(Long bookId) {
        return bookCopyRepository.existByBook_IdAndStatus(bookId, BookCopyStatus.AVAILABLE);
    }

    public BookCopy borrowCopy(Long bookId) {
        BookCopy copy = bookCopyRepository.findByBook_IdAndStatus(bookId, BookCopyStatus.AVAILABLE)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BookNotAvailableException("No available copies for book with id: " + bookId));

        copy.setStatus(BookCopyStatus.BORROWED);
        return bookCopyRepository.save(copy);
    }

    public void returnCopy(Long copyId) {
        BookCopy copy = getById(copyId);
        copy.setStatus(BookCopyStatus.AVAILABLE);
        bookCopyRepository.save(copy);
    }

    public void discontinueCopy(Long copyId) {
        BookCopy copy = getById(copyId);
        copy.setStatus(BookCopyStatus.DISCONTINUED);
        bookCopyRepository.save(copy);
    }


}
