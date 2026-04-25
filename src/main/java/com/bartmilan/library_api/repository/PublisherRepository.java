package com.bartmilan.library_api.repository;

import com.bartmilan.library_api.model.Publisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long>,
        JpaSpecificationExecutor<Publisher> {

    Optional<Publisher> findByName(String name);

    List<Publisher> findAll(Specification<Publisher> spec);

}
