package com.bartmilan.library_api.dto.PublisherDtos;

import com.bartmilan.library_api.dto.shared.BookBasicsDto;

import java.util.List;

public class PublisherResponseDto {

    private Long id;
    private String name;
    private List<BookBasicsDto> books;

    public PublisherResponseDto(Long id, String name, List<BookBasicsDto> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BookBasicsDto> getBooks() {
        return books;
    }
}
