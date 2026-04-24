package com.bartmilan.library_api.mapper;

import com.bartmilan.library_api.dto.BookDtos.AuthorToBookResponseDto;
import com.bartmilan.library_api.dto.BookDtos.BookRequestDto;
import com.bartmilan.library_api.dto.BookDtos.BookResponseDto;
import com.bartmilan.library_api.dto.BookDtos.PublisherToBookResponseDto;
import com.bartmilan.library_api.model.Author;
import com.bartmilan.library_api.model.Book;
import com.bartmilan.library_api.model.Publisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {

    public BookResponseDto toDto(Book b) {

        List<Author> authors = b.getAuthors();
        List<AuthorToBookResponseDto> authorsDto = new ArrayList<>();

        for (Author a : authors) {

            AuthorToBookResponseDto authorDto = new AuthorToBookResponseDto(
                    a.getId(),
                    a.getFullName()
            );
            authorsDto.add(authorDto);
        }

        PublisherToBookResponseDto publisherDto = new PublisherToBookResponseDto(
                b.getPublisher().getId(),
                b.getPublisher().getName()
        );

        return new BookResponseDto(
                b.getId(),
                b.getPolishTitle(),
                b.getOriginalTitle(),
                b.getDescription(),
                b.getReleaseDate(),
                b.getIsbn(),
                authorsDto,
                publisherDto,
                b.getCoverUrl()
        );
    }

    public Book toEntity(BookRequestDto b, List<Author> authors, Publisher publisher) {
        return new Book(
                b.getPolishTitle(),
                b.getOriginalTitle(),
                b.getDescription(),
                b.getReleaseDate(),
                b.getIsbn(),
                authors,
                publisher,
                b.getCoverUrl()
        );
    }
}
