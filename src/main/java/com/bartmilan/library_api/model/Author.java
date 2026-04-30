package com.bartmilan.library_api.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String firstName;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column
    private LocalDate deathDate;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public Author(String lastName, String firstName, String description, LocalDate birthDate, LocalDate deathDate, List<Book> books) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.description = description;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.books = books;
    }

    public Author(String lastName, String firstName, String description, LocalDate birthDate, LocalDate deathDate) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.description = description;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getFullName() {
        if (firstName == null) {
            return this.lastName;
        }

        return this.firstName + " " + this.lastName;
    }

}
