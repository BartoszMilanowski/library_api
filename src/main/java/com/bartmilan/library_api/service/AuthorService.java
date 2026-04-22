package com.bartmilan.library_api.service;

import com.bartmilan.library_api.exception.ResourceNotFoundException;
import com.bartmilan.library_api.model.Author;
import com.bartmilan.library_api.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author didn't find with id: " + id));
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public List<Author> getByName(String name) {
        return authorRepository.searchByName(name);
    }

    public List<Author> getByBookId(Long bookId) {
        return authorRepository.findByBooks_Id(bookId);
    }

    public List<Author> getByBookTitle(String bookTitle) {
        return authorRepository.findByBookTitle(bookTitle);
    }

    public Author create(Author author){
        return authorRepository.save(author);
    }

    public void delete(Long id){
        Author author = getById(id);
        authorRepository.delete(author);
    }

}
