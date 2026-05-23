package com.bartmilan.library_api.mapper;

import com.bartmilan.library_api.dto.LoanDtos.LoanResponseDto;
import com.bartmilan.library_api.model.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanResponseDto toDto(Loan l) {
        return new LoanResponseDto(
                l.getId(),
                l.getBookCopy().getId(),
                l.getBookCopy().getBook().getPolishTitle(),
                l.getUser().getId(),
                l.getUser().getEmail(),
                l.getLoanDate(),
                l.getDueDate(),
                l.getReturnDate(),
                l.getStatus()
        );
    }
}
