package com.bartmilan.library_api.service;

import com.bartmilan.library_api.exception.DuplicateResourceException;
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

    public Genre create(String name, String polishName) {
        if (!genreRepository.searchByName(name).isEmpty() || !genreRepository.searchByName(polishName).isEmpty()) {
            throw new DuplicateResourceException("Genre with name: " + name + " and polish name: " + polishName + " already exists");
        }
        return genreRepository.save(new Genre(name, polishName));
    }

    public Genre update(Long id, String name, String polishName) {
        Genre current = getById(id);

        current.setName(name);
        current.setPolishName(polishName);

        return genreRepository.save(current);
    }

    public void delete(Long id) {
        Genre genre = getById(id);
        genreRepository.delete(genre);
    }
}
