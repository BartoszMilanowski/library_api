package com.bartmilan.library_api.dto.ReservationDtos;

public class ReservationRequestDto {

    private Long bookId;
    private Long userId;

    public ReservationRequestDto() {
    }

    public ReservationRequestDto(Long bookId, Long userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public Long getUserId() {
        return userId;
    }
}
