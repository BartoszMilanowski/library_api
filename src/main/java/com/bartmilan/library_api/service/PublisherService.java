package com.bartmilan.library_api.service;

import com.bartmilan.library_api.dto.PublisherDtos.PublisherResponseDto;
import com.bartmilan.library_api.exception.DuplicateResourceException;
import com.bartmilan.library_api.exception.ResourceNotFoundException;
import com.bartmilan.library_api.mapper.PublisherMapper;
import com.bartmilan.library_api.model.Publisher;
import com.bartmilan.library_api.repository.PublisherRepository;
import com.bartmilan.library_api.specification.PublisherSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    public PublisherService(PublisherRepository publisherRepository, PublisherMapper publisherMapper) {
        this.publisherRepository = publisherRepository;
        this.publisherMapper = publisherMapper;
    }

    public Publisher getById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher didn't find with id: " + id));
    }

    public PublisherResponseDto getPublisherDtoById(Long id) {
        return publisherRepository.findById(id)
                .map(publisherMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher didn't find with id: " + id));
    }

    public List<PublisherResponseDto> getAll() {
        return publisherRepository.findAll()
                .stream()
                .map(publisherMapper::toDto)
                .toList();
    }

    public List<PublisherResponseDto> search(String name, Long bookId, String bookTitle) {

        Specification<Publisher> spec = (root, query, cb) -> cb.conjunction();

        if (name != null) spec = spec.and(PublisherSpecification.nameContains(name));
        if (bookId != null) spec = spec.and(PublisherSpecification.bookIdEquals(bookId));
        if (bookTitle != null) spec = spec.and(PublisherSpecification.bookTitleContains(bookTitle));

        return publisherRepository.findAll(spec)
                .stream()
                .map(publisherMapper::toDto)
                .toList();
    }

    public PublisherResponseDto create(String name) {
        if (publisherRepository.findByName(name).isPresent()) {
            throw new DuplicateResourceException("Publisher with name: " + name + " already exists");
        }
        return publisherMapper.toDto(publisherRepository.save(new Publisher(name)));
    }

    public PublisherResponseDto update(Long id, String name) {
        Publisher current = getById(id);

        current.setName(name);

        Publisher saved = publisherRepository.save(current);

        return publisherMapper.toDto(saved);
    }

    public void delete(Long id) {
        Publisher publisher = getById(id);
        publisherRepository.delete(publisher);
    }

}
