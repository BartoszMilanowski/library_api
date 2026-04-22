package com.bartmilan.library_api.service;

import com.bartmilan.library_api.exception.DuplicateResourceException;
import com.bartmilan.library_api.exception.ResourceNotFoundException;
import com.bartmilan.library_api.model.Publisher;
import com.bartmilan.library_api.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher getById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher didn't find with id: " + id));
    }

    public List<Publisher> getAll() {
        return publisherRepository.findAll();
    }

    public List<Publisher> getByName(String name) {
        return publisherRepository.searchByName(name);
    }

    public Publisher getByBookId(Long bookId) {
        return publisherRepository.findByBooks_Id(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher didn't find for book with id" + bookId));
    }

    public List<Publisher> getByBookTitle(String bookTitle) {
        return publisherRepository.findByBookTitle(bookTitle);
    }

    public Publisher create(Publisher publisher) {
        if (publisherRepository.findByName(publisher.getName()).isPresent()) {
            throw new DuplicateResourceException("Publisher with name: " + publisher.getName() + " already exists");
        }
        return publisherRepository.save(publisher);
    }

    public void delete(Long id) {
        Publisher publisher = getById(id);
        publisherRepository.delete(publisher);
    }

}
