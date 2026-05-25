package com.bartmilan.library_api.dto.ReservationDtos;

import com.bartmilan.library_api.model.enums.ReservationStatus;

import java.time.LocalDate;

public class ReservationResponseDto {

    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long userId;
    private String userEmail;
    private LocalDate reservationDate;
    private LocalDate expirationDate;
    private ReservationStatus status;

    public ReservationResponseDto(Long id, Long bookId, String bookTitle, Long userId, String userEmail,
                                  LocalDate reservationDate, LocalDate expirationDate, ReservationStatus status) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.userId = userId;
        this.userEmail = userEmail;
        this.reservationDate = reservationDate;
        this.expirationDate = expirationDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }
}
