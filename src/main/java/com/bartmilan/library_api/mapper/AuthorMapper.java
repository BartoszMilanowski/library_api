package com.bartmilan.library_api.mapper;

import com.bartmilan.library_api.dto.AuthorDtos.AuthorRequestDto;
import com.bartmilan.library_api.dto.AuthorDtos.AuthorResponseDto;
import com.bartmilan.library_api.dto.shared.BookBasicsDto;
import com.bartmilan.library_api.model.Author;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorMapper {

    private final BookMapper bookMapper;

    public AuthorMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public AuthorResponseDto toDto(Author a) {

        List<BookBasicsDto> booksDto = a.getBooks()
                .stream()
                .map(bookMapper::toBasicDto)
                .toList();

        return new AuthorResponseDto(
                a.getId(),
                a.getFullName(),
                a.getDescription(),
                a.getBirthDate(),
                a.getDeathDate(),
                booksDto
        );
    }

    public Author toEntity(AuthorRequestDto a) {
        return new Author(
                a.getLastName(),
                a.getFirstName(),
                a.getDescription(),
                a.getBirthDate(),
                a.getDeathDate()
        );
    }
}
