package com.bartmilan.library_api.controller;

import com.bartmilan.library_api.dto.LoanDtos.LoanRequestDto;
import com.bartmilan.library_api.model.Loan;
import com.bartmilan.library_api.model.enums.LoanStatus;
import com.bartmilan.library_api.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAll() {
        return ResponseEntity.ok(loanService.getAll());
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getById(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.getById(loanId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Loan>> search(
            @RequestParam(required = false) Long bookCopyId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) LoanStatus status,
            @RequestParam(required = false) LocalDate loanDateFrom,
            @RequestParam(required = false) LocalDate loanDateTo,
            @RequestParam(required = false) LocalDate dueDateFrom,
            @RequestParam(required = false) LocalDate dueDateTo,
            @RequestParam(required = false) LocalDate returnDateFrom,
            @RequestParam(required = false) LocalDate returnDateTo,
            @RequestParam(required = false) Long bookId
    ) {
        return ResponseEntity.ok(loanService.search(bookCopyId, userId, status, loanDateFrom, loanDateTo,
                dueDateFrom, dueDateTo, returnDateFrom, returnDateTo, bookId));
    }

    @PostMapping
    public ResponseEntity<Loan> create(@RequestBody LoanRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loanService.create(dto));
    }

    @PutMapping("/return/{loanId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long loanId) {
        loanService.returnBook(loanId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> delete(@PathVariable Long loanId) {
        loanService.delete(loanId);
        return ResponseEntity.noContent().build();
    }
}
