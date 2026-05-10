package com.bartmilan.library_api.dto.LoanDtos;

public class LoanRequestDto {

    private Long bookCopyId;
    private Long userId;

    public LoanRequestDto() {
    }

    public LoanRequestDto(Long bookCopyId, Long userId) {
        this.bookCopyId = bookCopyId;
        this.userId = userId;
    }

    public Long getBookCopyId() {
        return bookCopyId;
    }

    public Long getUserId() {
        return userId;
    }
}
