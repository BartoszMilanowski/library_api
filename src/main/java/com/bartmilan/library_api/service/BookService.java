package com.bartmilan.library_api.service;

import com.bartmilan.library_api.dto.AuthorToBookResponseDto;
import com.bartmilan.library_api.dto.BookResponseDto;
import com.bartmilan.library_api.dto.PublisherToBookResponseDto;
import com.bartmilan.library_api.exception.DuplicateResourceException;
import com.bartmilan.library_api.exception.ResourceNotFoundException;
import com.bartmilan.library_api.model.Author;
import com.bartmilan.library_api.model.Book;
import com.bartmilan.library_api.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponseDto toDto(Book b) {

        List<Author> authors = b.getAuthors();
        List<AuthorToBookResponseDto> authorsDto = new ArrayList<>();

        for (Author a : authors) {

            AuthorToBookResponseDto authorDto = new AuthorToBookResponseDto(
                    a.getId(),
                    a.getFullName()
            );
            authorsDto.add(authorDto);
        }

        PublisherToBookResponseDto publisherDto = new PublisherToBookResponseDto(
                b.getPublisher().getId(),
                b.getPublisher().getName()
        );

        return new BookResponseDto(
                b.getId(),
                b.getPolishTitle(),
                b.getOriginalTitle(),
                b.getDescription(),
                b.getReleaseDate(),
                b.getIsbn(),
                authorsDto,
                publisherDto,
                b.getCoverUrl()
        );
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public BookResponseDto getBookDtoById(Long id) {
        return bookRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public List<BookResponseDto> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<BookResponseDto> getByTitle(String title) {
        return bookRepository.searchByTitle(title)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public BookResponseDto getByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with isbn: " + isbn));
    }

    public List<BookResponseDto> getByAuthorId(Long authorId) {
        return bookRepository.findByAuthors_Id(authorId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<BookResponseDto> getByAuthorsName(String authorName) {
        return bookRepository.searchByAuthorsName(authorName)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<BookResponseDto> getByPublisherId(Long publisherId) {
        return bookRepository.findByPublisher_Id(publisherId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<BookResponseDto> getByPublisherName(String publisherName) {
        return bookRepository.findByPublisher_Name(publisherName)
                .stream()
                .map(this::toDto)
                .toList();
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
