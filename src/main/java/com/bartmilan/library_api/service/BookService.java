package com.bartmilan.library_api.service;


import com.bartmilan.library_api.dto.BookDtos.BookRequestDto;
import com.bartmilan.library_api.dto.BookDtos.BookResponseDto;
import com.bartmilan.library_api.exception.DuplicateResourceException;
import com.bartmilan.library_api.exception.ResourceNotFoundException;
import com.bartmilan.library_api.mapper.BookMapper;

import com.bartmilan.library_api.model.Author;
import com.bartmilan.library_api.model.Book;
import com.bartmilan.library_api.model.Publisher;
import com.bartmilan.library_api.repository.BookRepository;
import com.bartmilan.library_api.specyfication.BookSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    public BookService(BookRepository bookRepository, BookMapper bookMapper,
                       AuthorService authorService, PublisherService publisherService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public BookResponseDto getBookDtoById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public List<BookResponseDto> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public List<BookResponseDto> getByTitle(String title) {
        return bookRepository.searchByTitle(title)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public BookResponseDto getByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with isbn: " + isbn));
    }

    public List<BookResponseDto> getByAuthorId(Long authorId) {
        return bookRepository.findByAuthors_Id(authorId)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public List<BookResponseDto> getByAuthorsName(String authorName) {
        return bookRepository.searchByAuthorsName(authorName)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public List<BookResponseDto> getByPublisherId(Long publisherId) {
        return bookRepository.findByPublisher_Id(publisherId)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public List<BookResponseDto> getByPublisherName(String publisherName) {
        return bookRepository.findByPublisher_Name(publisherName)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public BookResponseDto create(BookRequestDto bookRequestDto) {
        if (bookRepository.findByIsbn(bookRequestDto.getIsbn()).isPresent()) {
            throw new DuplicateResourceException("Book with ISBN " + bookRequestDto.getIsbn() + " already exists");
        }

        List<Author> authors = bookRequestDto.getAuthorsIds()
                .stream()
                .map(authorService::getById)
                .toList();

        Publisher publisher = publisherService.getById(bookRequestDto.getPublisherId());

        Book saved = bookRepository.save(bookMapper.toEntity(bookRequestDto, authors, publisher));

        return bookMapper.toDto(saved);
    }

    public List<BookResponseDto> search(String title, String authorName, Long authorId,
                                        Long publisherId, String publisherName) {

        Specification<Book> spec = (root, query, cb) -> cb.conjunction();

        if (title != null) spec = spec.and(BookSpecification.titleContains(title));
        if (authorName != null) spec = spec.and(BookSpecification.authorNameContains(authorName));
        if (authorId != null) spec = spec.and(BookSpecification.authorIdEquals(authorId));
        if (publisherId != null) spec = spec.and(BookSpecification.publisherIdEquals(publisherId));
        if (publisherName != null) spec = spec.and(BookSpecification.publisherNameContains(publisherName));

        return bookRepository.findAll(spec)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public BookResponseDto update(Long id, BookRequestDto requestDto) {

        Book current = getById(id);

        List<Author> authors = requestDto.getAuthorsIds()
                .stream()
                .map(authorService::getById)
                .toList();

        Publisher publisher = publisherService.getById(requestDto.getPublisherId());

        current.setPolishTitle(requestDto.getPolishTitle());
        current.setOriginalTitle(requestDto.getOriginalTitle());
        current.setDescription(requestDto.getDescription());
        current.setReleaseDate(requestDto.getReleaseDate());
        current.setIsbn(requestDto.getIsbn());
        current.setAuthors(authors);
        current.setPublisher(publisher);
        current.setCoverUrl(requestDto.getCoverUrl());

        Book saved = bookRepository.save(current);
        return bookMapper.toDto(saved);

    }

    public void delete(Long id) {
        Book book = getById(id);
        bookRepository.delete(book);
    }

}
