package com.bartmilan.library_api.model;

import jakarta.persistence.*;

import java.util.Date;
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
    private Date birthDate;

    @Column
    private Date deathDate;

    @ManyToMany
    @JoinTable(name = "books_authors")
    private List<Book> books;

    public Author() {
    }

    public Author(String lastName, String firstName, String description, Date birthDate, Date deathDate, List<Book> books) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.description = description;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.books = books;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
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
