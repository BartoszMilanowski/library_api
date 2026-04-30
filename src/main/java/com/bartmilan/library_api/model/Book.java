package com.bartmilan.library_api.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String polishTitle;

    @Column(nullable = false)
    private String originalTitle;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private String isbn;

    @ManyToMany
    @JoinTable(name = "books_authors")
    private List<Author> authors = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column
    private String coverUrl;

    public Book() {
    }

    public Book(String polishTitle, String originalTitle, String description, LocalDate releaseDate, String isbn,
                List<Author> authors, Publisher publisher, String coverUrl) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolishTitle() {
        return polishTitle;
    }

    public void setPolishTitle(String polishTitle) {
        this.polishTitle = polishTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
