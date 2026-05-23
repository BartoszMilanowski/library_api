package com.bartmilan.library_api.dto.LoanDtos;

import com.bartmilan.library_api.model.enums.LoanStatus;

import java.time.LocalDate;

public class LoanResponseDto {

    private Long id;
    private Long bookCopyId;
    private String bookTitle;
    private Long userId;
    private String userMail;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanStatus status;

    public LoanResponseDto(Long id, Long bookCopyId, String bookTitle,
                           Long userId, String userMail, LocalDate loanDate, LocalDate returnDate,
                           LocalDate dueDate, LoanStatus status) {
        this.id = id;
        this.bookCopyId = bookCopyId;
        this.bookTitle = bookTitle;
        this.userId = userId;
        this.userMail = userMail;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getBookCopyId() {
        return bookCopyId;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getUserMail() {
        return userMail;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
