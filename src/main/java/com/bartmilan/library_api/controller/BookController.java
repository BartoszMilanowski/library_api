package com.bartmilan.library_api.controller;

import com.bartmilan.library_api.dto.BookDtos.BookRequestDto;
import com.bartmilan.library_api.dto.BookDtos.BookResponseDto;
import com.bartmilan.library_api.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAll() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookDtoById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponseDto> getByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getByIsbn(isbn));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDto>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String publisherName,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long publisherId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) boolean available
    ) {
        return ResponseEntity.ok(bookService.search(title, authorName, authorId, publisherId,
                publisherName, year, available));
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> create(@RequestBody BookRequestDto book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> update(
            @PathVariable Long id,
            @RequestBody BookRequestDto book
    ) {
        return ResponseEntity.ok(bookService.update(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
