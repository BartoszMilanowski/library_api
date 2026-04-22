package com.bartmilan.library_api.service;

import com.bartmilan.library_api.exception.DuplicateResourceException;
import com.bartmilan.library_api.exception.ResourceNotFoundException;
import com.bartmilan.library_api.model.Book;
import com.bartmilan.library_api.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public List<Book> getByTitle(String title) {
        return bookRepository.searchByTitle(title);
    }

    public Book getByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with isbn: " + isbn));
    }

    public List<Book> getByAuthorId(Long authorId) {
        return bookRepository.findByAuthors_Id(authorId);
    }

    public List<Book> getByAuthorsName(String authorName) {
        return bookRepository.searchByAuthorsName(authorName);
    }

    public List<Book> getByPublisherId(Long publisherId) {
        return bookRepository.findByPublisher_Id(publisherId);
    }

    public List<Book> getByPublisherName(String publisherName) {
        return bookRepository.findByPublisher_Name(publisherName);
    }

    public Book create(Book book) {
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new DuplicateResourceException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        Book book = getById(id);
        bookRepository.delete(book);
    }

}
