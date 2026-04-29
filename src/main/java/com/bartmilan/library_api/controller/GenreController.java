package com.bartmilan.library_api.controller;

import com.bartmilan.library_api.model.Genre;
import com.bartmilan.library_api.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAll() {
        return ResponseEntity.ok(genreService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.getById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Genre>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(genreService.getByName(name));
    }

    @PostMapping
    public ResponseEntity<Genre> create(@RequestParam String name, @RequestParam String polishName) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.create(name, polishName));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Genre> update(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String polishName
    ) {
        return ResponseEntity.ok(genreService.update(id, name, polishName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();k
    }

}
