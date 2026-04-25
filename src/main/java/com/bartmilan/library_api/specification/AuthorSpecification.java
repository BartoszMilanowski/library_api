package com.bartmilan.library_api.specification;

import com.bartmilan.library_api.model.Author;
import com.bartmilan.library_api.model.Book;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class AuthorSpecification {

    public static Specification<Author> nameContains(String phrase) {
        return (root, query, cb) ->
                cb.or(
                        cb.like(cb.lower(root.get("firstName")), "%" + phrase.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("lastName")), "%" + phrase.toLowerCase() + "%")

                );
    }

    public static Specification<Author> bookIdEquals(Long bookId) {
        return (root, query, cb) ->
                cb.equal(root.join("books").get("id"), bookId);
    }

    public static Specification<Author> bookTitleContains(String phrase) {
        return (root, query, cb) -> {
            Join<Author, Book> books = root.join("books");
            return cb.or(
                    cb.like(cb.lower(books.get("polishTitle")), "%" + phrase.toLowerCase() + "%"),
                    cb.like(cb.lower(books.get("originalTitle")), "%" + phrase.toLowerCase() + "%")
            );
        };
    }
}
