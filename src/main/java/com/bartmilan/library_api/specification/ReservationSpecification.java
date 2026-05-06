package com.bartmilan.library_api.specification;

import com.bartmilan.library_api.model.Reservation;
import com.bartmilan.library_api.model.enums.ReservationDateType;
import com.bartmilan.library_api.model.enums.ReservationStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ReservationSpecification {

    public static Specification<Reservation> bookIdEquals(Long bookId) {
        return (root, query, cb) ->
                cb.equal(root.get("book").get("id"), bookId);
    }

    public static Specification<Reservation> userIdEquals(Long userId) {
        return (root, query, cb) ->
                cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Reservation> statusEquals(ReservationStatus status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }

    public static Specification<Reservation> dateBetween(ReservationDateType dateType, LocalDate from, LocalDate to) {

        String fieldName = switch (dateType) {
            case RESERVATION -> "reservationDate";
            case EXPIRATION -> "expirationDate";
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
}
