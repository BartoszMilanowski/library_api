package com.bartmilan.library_api.repository;

import com.bartmilan.library_api.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("SELECT p FROM Publisher p WEHRE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :phrase, '%'))")
    List<Publisher> findByName(@Param("phrase") String phrase);

    Optional<Publisher> findByBooks_Id(Long bookId);

    @Query("SELECT p FROM Publisher p JOIN p.books b WHERE " +
            "LOWER(b.polishTitle) LIKE LOWER(CONCAT('%', :phrase, '%')) OR " +
            "LOWER(b.originalTitle) LIKE LOWER(CONCAT('%', :phrase, '%'))")
    List<Publisher> findByBookTitle(@Param("phrase") String phrase);


}
