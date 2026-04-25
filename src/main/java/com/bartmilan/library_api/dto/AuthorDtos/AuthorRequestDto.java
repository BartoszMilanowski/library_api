package com.bartmilan.library_api.dto.AuthorDtos;

import java.time.LocalDate;

public class AuthorRequestDto {

    private String lastName;
    private String firstName;
    private String description;
    private LocalDate birthDate;
    private LocalDate deathDate;

    public AuthorRequestDto(String lastName, String firstName, String description,
                            LocalDate birthDate, LocalDate deathDate) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.description = description;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public AuthorRequestDto() {
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
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
}
