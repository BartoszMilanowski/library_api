package com.bartmilan.library_api.repository;

import com.bartmilan.library_api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE " +
            "LOWER(a.firstName) LIKE LOWER(CONCAT('%', :phrase, '%')) OR " +
            "LOWER(a.lastName) LIKE LOWER(CONCAT('%', :phrase, '%'))")
    List<Author> searchByName(@Param("phrase") String phrase);

    List<Author> findByBooks_Id(Long bookId);

    @Query("SELECT a FROM Author a JOIN a.books b WHERE " +
            "LOWER(b.polishTitle) LIKE LOWER(CONCAT('%', :phrase, '%')) OR " +
            "LOWER(b.originalTitle) LIKE LOWER(CONCAT('%', :phrase, '%'))")
    List<Author> findByBookTitle(@Param("phrase") String phrase);
}
