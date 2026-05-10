package com.bartmilan.library_api.service;

import com.bartmilan.library_api.exception.BookNotAvailableException;
import com.bartmilan.library_api.exception.ResourceNotFoundException;
import com.bartmilan.library_api.model.BookCopy;
import com.bartmilan.library_api.model.enums.BookCopyStatus;
import com.bartmilan.library_api.model.enums.ReservationStatus;
import com.bartmilan.library_api.repository.BookCopyRepository;
import com.bartmilan.library_api.repository.ReservationRepository;
import com.bartmilan.library_api.specification.ReservationSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final ReservationRepository reservationRepository;

    public BookCopyService(BookCopyRepository bookCopyRepository, ReservationRepository reservationRepository) {

        this.bookCopyRepository = bookCopyRepository;
        this.reservationRepository = reservationRepository;
    }

    public BookCopy getById(Long id) {
        return bookCopyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Didn't found book copy with id: " + id));
    }

    public List<BookCopy> getAvailableCopiesByBookId(Long bookId) {
        return bookCopyRepository.findByBook_IdAndStatus(bookId, BookCopyStatus.AVAILABLE);
    }

    public BookCopy borrowCopy(Long bookCopyId) {

        BookCopy copy = getById(bookCopyId);
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

    public int countAvailableCopies(Long bookId) {
        int available = bookCopyRepository
                .findByBook_IdAndStatus(bookId, BookCopyStatus.AVAILABLE)
                .size();

        int pendingReservations = reservationRepository
                .findAll(ReservationSpecification.bookIdEquals(bookId)
                        .and(ReservationSpecification.statusEquals(ReservationStatus.PENDING)))
                .size();

        return available - pendingReservations;
    }

    public boolean isBookAvailable(Long bookId) {
        return countAvailableCopies(bookId) > 0;
    }


}
