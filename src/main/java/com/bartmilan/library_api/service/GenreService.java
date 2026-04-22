package com.bartmilan.library_api.service;

import com.bartmilan.library_api.model.Genre;
import com.bartmilan.library_api.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import com.bartmilan.library_api.exception.ResourceNotFoundException;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    public Genre getById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + id));
    }

    public List<Genre> getByName(String name) {
        return genreRepository.searchByName(name);
    }

    public Genre create(Genre genre){
        return genreRepository.save(genre);
    }

    public void delete(Long id){
        Genre genre = getById(id);
        genreRepository.delete(genre);
    }
}
