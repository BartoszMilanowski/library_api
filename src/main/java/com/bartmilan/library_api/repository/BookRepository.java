package com.bartmilan.library_api.repository;

import com.bartmilan.library_api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByPolishTitleOrOriginalTitle(String polishTitle, String originalTitle);

    @Query("SELECT b FROM Book b WHERE LOWER(b.polishTitle) LIKE LOWER(CONCAT '%', :phrase, '%')) OR " +
            "LOWER(b.originalTitle) LIKE LOWER(CONCAT('%', :phrase, '%'))")
    List<Book> searchByTitle(@Param("phrase") String phrase);

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByAuthors_Id(Long authorId);

    List<Book> findByAuthors_LastName(String authorLastName);

    List<Book> findByPublisher_Id(Long publisherId);

    List<Book> findByPublisher_Name(String publisherName);
}
