package com.bartmilan.library_api.mapper;

import com.bartmilan.library_api.dto.AuthorDtos.AuthorRequestDto;
import com.bartmilan.library_api.dto.AuthorDtos.AuthorResponseDto;
import com.bartmilan.library_api.dto.AuthorDtos.BookToAuthorResponseDto;
import com.bartmilan.library_api.dto.BookDtos.BookRequestDto;
import com.bartmilan.library_api.model.Author;
import com.bartmilan.library_api.model.Book;

import java.util.ArrayList;
import java.util.List;

public class AuthorMapper {

    public AuthorResponseDto toDto(Author a) {

        List<Book> books = a.getBooks();
        List<BookToAuthorResponseDto> booksDto = new ArrayList<>();

        for (Book b : books) {

            BookToAuthorResponseDto bookDto = new BookToAuthorResponseDto(
                    b.getId(),
                    b.getPolishTitle(),
                    b.getOriginalTitle(),
                    b.getCoverUrl()
            );

            booksDto.add(bookDto);
        }

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
