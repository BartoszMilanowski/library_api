package com.bartmilan.library_api.specification;

import com.bartmilan.library_api.model.Author;
import com.bartmilan.library_api.model.Book;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BookSpecification {

    public static Specification<Book> titleContains(String phrase) {
        return (root, query, cb) ->
                cb.or(
                        cb.like(cb.lower(root.get("polishTitle")), "%" + phrase.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("originalTitle")), "%" + phrase.toLowerCase() + "%")
                );
    }

    public static Specification<Book> authorNameContains(String phrase) {
        return (root, query, cb) -> {
            Join<Book, Author> authors = root.join("authors");
            return cb.or(
                    cb.like(cb.lower(authors.get("firstName")), "%" + phrase.toLowerCase() + "%"),
                    cb.like(cb.lower(authors.get("lastName")), "%" + phrase.toLowerCase() + "%")
            );
        };
    }

    public static Specification<Book> authorIdEquals(Long authorId) {
        return (root, query, cb) ->
                cb.equal(root.join("authors").get("id"), authorId);
    }

    public static Specification<Book> publisherIdEquals(Long publisherId) {
        return (root, query, cb) ->
                cb.equal(root.get("publisher").get("id"), publisherId);
    }

    public static Specification<Book> publisherNameContains(String phrase) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("publisher").get("name")), "%" + phrase.toLowerCase() + "%");
    }

    public static Specification<Book> releaseDateYearIsEqual(Integer year) {
        return (root, query, cb) ->
                cb.equal(
                        cb.function("YEAR", Integer.class, root.get("releaseDate")),
                        year
                );
    }

}
