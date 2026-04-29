package com.bartmilan.library_api.mapper;

import com.bartmilan.library_api.dto.PublisherDtos.PublisherResponseDto;
import com.bartmilan.library_api.dto.shared.BookBasicsDto;
import com.bartmilan.library_api.model.Publisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublisherMapper {

    private final BookMapper bookMapper;

    public PublisherMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public PublisherResponseDto toDto(Publisher p) {

        List<BookBasicsDto> booksDto = p.getBooks()
                .stream()
                .map(bookMapper::toBasicDto)
                .toList();

        return new PublisherResponseDto(
                p.getId(),
                p.getName(),
                booksDto
        );
    }
}
