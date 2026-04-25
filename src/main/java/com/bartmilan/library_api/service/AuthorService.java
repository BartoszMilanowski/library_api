package com.bartmilan.library_api.service;

import com.bartmilan.library_api.dto.AuthorDtos.AuthorRequestDto;
import com.bartmilan.library_api.dto.AuthorDtos.AuthorResponseDto;
import com.bartmilan.library_api.exception.ResourceNotFoundException;
import com.bartmilan.library_api.mapper.AuthorMapper;
import com.bartmilan.library_api.model.Author;
import com.bartmilan.library_api.repository.AuthorRepository;
import com.bartmilan.library_api.specification.AuthorSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public Author getById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author didn't find with id: " + id));
    }

    public AuthorResponseDto getAuthorDtoById(Long id) {
        return authorRepository.findById(id)
                .map(authorMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Author didn't find with id: " + id));
    }

    public List<AuthorResponseDto> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDto)
                .toList();
    }

    public List<AuthorResponseDto> search(String name, Long bookId, String bookTitle) {

        Specification<Author> spec = (root, query, cb) -> cb.conjunction();

        if (name != null) spec = spec.and(AuthorSpecification.nameContains(name));
        if (bookId != null) spec = spec.and(AuthorSpecification.bookIdEquals(bookId));
        if (bookTitle != null) spec = spec.and(AuthorSpecification.bookTitleContains(bookTitle));

        return authorRepository.findAll(spec)
                .stream()
                .map(authorMapper::toDto)
                .toList();
    }

    public AuthorResponseDto create(AuthorRequestDto author) {
        return authorMapper.toDto(authorRepository.save(authorMapper.toEntity(author)));
    }

    public AuthorResponseDto update(Long id, AuthorRequestDto author) {
        Author current = getById(id);

        current.setLastName(author.getLastName());
        current.setFirstName(author.getFirstName());
        current.setBirthDate(author.getBirthDate());
        current.setDeathDate(author.getDeathDate());
        current.setDescription(author.getDescription());

        Author saved = authorRepository.save(current);

        return authorMapper.toDto(saved);
    }

    public void delete(Long id) {
        Author author = getById(id);
        authorRepository.delete(author);
    }

}
