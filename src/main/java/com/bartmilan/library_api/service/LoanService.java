package com.bartmilan.library_api.service;

import com.bartmilan.library_api.dto.LoanDtos.LoanRequestDto;
import com.bartmilan.library_api.dto.LoanDtos.LoanResponseDto;
import com.bartmilan.library_api.exception.BookNotAvailableException;
import com.bartmilan.library_api.exception.ResourceNotFoundException;
import com.bartmilan.library_api.mapper.LoanMapper;
import com.bartmilan.library_api.model.*;
import com.bartmilan.library_api.model.enums.LoanDateType;
import com.bartmilan.library_api.model.enums.LoanStatus;
import com.bartmilan.library_api.model.enums.ReservationStatus;
import com.bartmilan.library_api.repository.LoanRepository;
import com.bartmilan.library_api.specification.LoanSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookCopyService bookCopyService;
    private final UserService userService;
    private final ReservationService reservationService;
    private final BookService bookService;
    private final LoanMapper loanMapper;

    public LoanService(LoanRepository loanRepository, BookCopyService bookCopyService,
                       UserService userService, ReservationService reservationService,
                       BookService bookService, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.bookCopyService = bookCopyService;
        this.userService = userService;
        this.reservationService = reservationService;
        this.bookService = bookService;
        this.loanMapper = loanMapper;
    }

    public List<LoanResponseDto> getAll() {
        return loanRepository.findAll()
                .stream()
                .map(loanMapper::toDto)
                .toList();
    }

    public Loan getById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));
    }

    public LoanResponseDto getDtoById(Long id) {
        return loanRepository.findById(id)
                .map(loanMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));
    }

    public List<LoanResponseDto> search(Long bookCopyId, Long userId, LoanStatus status, LocalDate loanDateFrom, LocalDate loanDateTo,
                                        LocalDate dueDateFrom, LocalDate dueDateTo, LocalDate returnDateFrom,
                                        LocalDate returnDateTo, Long bookId) {
        Specification<Loan> spec = (root, query, cb) -> cb.conjunction();

        if (bookCopyId != null) spec = spec.and(LoanSpecification.bookCopyIdEquals(bookCopyId));
        if (userId != null) spec = spec.and(LoanSpecification.userIdEquals(userId));
        if (status != null) spec = spec.and(LoanSpecification.statusEquals(status));
        if (loanDateFrom != null || loanDateTo != null) spec =
                spec.and(LoanSpecification.dateBetween(LoanDateType.LOAN, loanDateFrom, loanDateTo));
        if (dueDateFrom != null || dueDateTo != null) spec =
                spec.and(LoanSpecification.dateBetween(LoanDateType.DUE, dueDateFrom, dueDateTo));
        if (returnDateFrom != null || returnDateTo != null) spec =
                spec.and(LoanSpecification.dateBetween(LoanDateType.RETURN, returnDateFrom, returnDateTo));
        if (bookId != null) spec = spec.and(LoanSpecification.bookIdEquals(bookId));

        return loanRepository.findAll(spec)
                .stream()
                .map(loanMapper::toDto)
                .toList();
    }

    @Transactional
    public LoanResponseDto create(LoanRequestDto l) {

        Book book = bookService.searchByBookCopyId(l.getBookCopyId());
        BookCopy bookCopy = bookCopyService.getById(l.getBookCopyId());
        User user = userService.getById(l.getUserId());

        Optional<Reservation> r = reservationService.searchForEntity(
                        book.getId(), user.getId(),
                        ReservationStatus.PENDING,
                        null, null, null, null
                )
                .stream()
                .findFirst();

        if (r.isEmpty() && !bookCopyService.isBookAvailable(book.getId())) {
            throw new BookNotAvailableException("Not available copies for book with id: " + book.getId() +
                    ". Check reservations");
        }

        Loan loan = new Loan(
                bookCopy,
                user,
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                null,
                LoanStatus.ACTIVE
        );

        Loan saved = loanRepository.save(loan);
        bookCopyService.borrowCopy(bookCopy.getId());

        r.ifPresent(reservation -> reservationService.fulfill(reservation.getId()));

        return loanMapper.toDto(saved);
    }

    public void returnBook(Long loanId) {
        Loan loan = getById(loanId);
        bookCopyService.returnCopy(loan.getBookCopy().getId());

        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnDate(LocalDate.now());

        loanRepository.save(loan);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void markAsOverDue() {
        List<Loan> overdue = loanRepository
                .findAll(LoanSpecification.statusEquals(LoanStatus.ACTIVE)
                        .and(LoanSpecification.dateBetween(LoanDateType.DUE, null, LocalDate.now()))
                );

        overdue.forEach(l -> l.setStatus(LoanStatus.OVERDUE));
        loanRepository.saveAll(overdue);
    }

    public void delete(Long id) {
        loanRepository.delete(getById(id));
    }


}
