package com.bartmilan.library_api.controller;

import com.bartmilan.library_api.dto.PublisherDtos.PublisherResponseDto;
import com.bartmilan.library_api.service.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<List<PublisherResponseDto>> getAll() {
        return ResponseEntity.ok(publisherService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(publisherService.getPublisherDtoById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PublisherResponseDto>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long bookId,
            @RequestParam(required = false) String bookTitle
    ) {
        return ResponseEntity.ok(publisherService.search(name, bookId, bookTitle));
    }

    @PostMapping
    public ResponseEntity<PublisherResponseDto> create(
            @RequestParam String name
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherService.create(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherResponseDto> update(
            @PathVariable Long id,
            @RequestParam String name
    ) {
        return ResponseEntity.ok(publisherService.update(id, name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
