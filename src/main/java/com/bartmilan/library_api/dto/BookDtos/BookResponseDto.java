package com.bartmilan.library_api.dto.BookDtos;

import java.time.LocalDate;
import java.util.List;

public class BookResponseDto {

    private Long id;
    private String polishTitle;
    private String originalTitle;
    private String description;
    private LocalDate releaseDate;
    private String isbn;
    private List<AuthorToBookResponseDto> authors;
    private PublisherToBookResponseDto publisher;
    private String coverUrl;

    public BookResponseDto(Long id, String polishTitle, String originalTitle, String description,
                           LocalDate releaseDate, String isbn, List<AuthorToBookResponseDto> authors,
                           PublisherToBookResponseDto publisher, String coverUrl) {
        this.id = id;
        this.polishTitle = polishTitle;
        this.originalTitle = originalTitle;
        this.description = description;
        this.releaseDate = releaseDate;
        this.isbn = isbn;
        this.authors = authors;
        this.publisher = publisher;
        this.coverUrl = coverUrl;
    }

    public Long getId() {
        return id;
    }

    public String getPolishTitle() {
        return polishTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public List<AuthorToBookResponseDto> getAuthors() {
        return authors;
    }

    public PublisherToBookResponseDto getPublisher() {
        return publisher;
    }

    public String getCoverUrl() {
        return coverUrl;
    }
}
