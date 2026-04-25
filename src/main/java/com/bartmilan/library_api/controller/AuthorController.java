package com.bartmilan.library_api.controller;

import com.bartmilan.library_api.dto.AuthorDtos.AuthorRequestDto;
import com.bartmilan.library_api.dto.AuthorDtos.AuthorResponseDto;
import com.bartmilan.library_api.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> getAll() {
        return ResponseEntity.ok(authorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorDtoById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AuthorResponseDto>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long bookId,
            @RequestParam(required = false) String bookTitle
    ) {
        return ResponseEntity.ok(authorService.search(name, bookId, bookTitle));
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> create(@RequestBody AuthorRequestDto author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.create(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> update(
            @PathVariable Long id,
            @RequestBody AuthorRequestDto author
    ) {
        return ResponseEntity.ok(authorService.update(id, author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
