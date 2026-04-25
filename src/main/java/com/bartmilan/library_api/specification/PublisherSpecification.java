package com.bartmilan.library_api.specification;

import com.bartmilan.library_api.model.Book;
import com.bartmilan.library_api.model.Publisher;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class PublisherSpecification {

    public static Specification<Publisher> nameContains(String phrase) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + phrase.toLowerCase() + "%");
    }

    public static Specification<Publisher> bookIdEquals(Long bookId) {
        return (root, query, cb) ->
                cb.equal(root.join("books").get("id"), bookId);
    }

    public static Specification<Publisher> bookTitleContains(String phrase) {
        return (root, query, cb) -> {
            Join<Publisher, Book> books = root.join("books");
            return cb.or(
                    cb.like(cb.lower(books.get("polishTitle")), "%" + phrase.toLowerCase() + "%"),
                    cb.like(cb.lower(books.get("originalTitle")), "%" + phrase.toLowerCase() + "%")
            );
        };
    }
}
