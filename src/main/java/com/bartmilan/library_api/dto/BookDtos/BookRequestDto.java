package com.bartmilan.library_api.dto.BookDtos;

import java.time.LocalDate;
import java.util.List;

public class BookRequestDto {

    private String polishTitle;
    private String originalTitle;
    private String description;
    private LocalDate releaseDate;
    private String isbn;
    private List<Long> authorsIds;
    private Long publisherId;
    private String coverUrl;

    public BookRequestDto() {
    }

    public void setPolishTitle(String polishTitle) {
        this.polishTitle = polishTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAuthorsIds(List<Long> authorsIds) {
        this.authorsIds = authorsIds;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
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

    public List<Long> getAuthorsIds() {
        return authorsIds;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }
}
