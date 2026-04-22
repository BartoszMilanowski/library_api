package com.bartmilan.library_api.repository;

import com.bartmilan.library_api.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("SELECT g FROM Genre g WHERE " +
            "LOWER(g.name) LIKE LOWER(CONCAT('%', :phrase, '%')) OR " +
            "LOWER(g.polishName) LIKE LOWER(CONCAT('%', :phrase, '%'))")
    List<Genre> searchByName(@Param("phrase") String phrase);
}
