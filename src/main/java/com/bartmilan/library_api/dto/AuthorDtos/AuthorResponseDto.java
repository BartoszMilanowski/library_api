package com.bartmilan.library_api.dto.AuthorDtos;

import com.bartmilan.library_api.dto.shared.BookBasicsDto;

import java.time.LocalDate;
import java.util.List;

public class AuthorResponseDto {

    private Long id;
    private String fullName;
    private String description;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private List<BookBasicsDto> books;

    public AuthorResponseDto(Long id, String fullName, String description,
                             LocalDate birthDate, LocalDate deathDate,
                             List<BookBasicsDto> books) {
        this.id = id;
        this.fullName = fullName;
        this.description = description;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public List<BookBasicsDto> getBooks() {
        return books;
    }
}
