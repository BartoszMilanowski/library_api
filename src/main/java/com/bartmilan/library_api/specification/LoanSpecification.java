package com.bartmilan.library_api.specification;

import com.bartmilan.library_api.model.BookCopy;
import com.bartmilan.library_api.model.Loan;
import com.bartmilan.library_api.model.enums.LoanDateType;
import com.bartmilan.library_api.model.enums.LoanStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoanSpecification {

    public static Specification<Loan> bookCopyIdEquals(Long bookCopyId) {
        return (root, query, cb) ->
                cb.equal(root.get("bookCopy").get("id"), bookCopyId);
    }

    public static Specification<Loan> userIdEquals(Long userId) {
        return (root, query, cb) ->
                cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Loan> statusEquals(LoanStatus status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }

    public static Specification<Loan> dateBetween(LoanDateType dateType, LocalDate from, LocalDate to) {

        String fieldName = switch (dateType) {
            case LOAN -> "loanDate";
            case DUE -> "dueDate";
            case RETURN -> "returnDate";
        };

        return (root, query, cb) -> {
            if (from != null && to != null) {
                return cb.between(root.get(fieldName), from, to);
            } else if (from != null) {
                return cb.greaterThanOrEqualTo(root.get(fieldName), from);
            } else if (to != null) {
                return cb.lessThanOrEqualTo(root.get(fieldName), to);
            }
            return cb.conjunction();
        };
    }

    public static Specification<Loan> bookIdEquals(Long bookId) {
        return (root, query, cb) -> {
            Join<Loan, BookCopy> copy = root.join("bookCopy");
            return cb.equal(copy.get("book").get("id"), bookId);
        };
    }
}
