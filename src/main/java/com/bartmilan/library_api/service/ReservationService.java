package com.bartmilan.library_api.service;

import com.bartmilan.library_api.dto.ReservationDtos.ReservationRequestDto;
import com.bartmilan.library_api.dto.ReservationDtos.ReservationResponseDto;
import com.bartmilan.library_api.exception.BookNotAvailableException;
import com.bartmilan.library_api.exception.DuplicateResourceException;
import com.bartmilan.library_api.exception.ResourceNotFoundException;
import com.bartmilan.library_api.mapper.ReservationMapper;
import com.bartmilan.library_api.model.Book;
import com.bartmilan.library_api.model.Reservation;
import com.bartmilan.library_api.model.enums.ReservationDateType;
import com.bartmilan.library_api.model.enums.ReservationStatus;
import com.bartmilan.library_api.model.User;
import com.bartmilan.library_api.repository.ReservationRepository;
import com.bartmilan.library_api.specification.ReservationSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookService bookService;
    private final UserService userService;
    private final BookCopyService bookCopyService;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository reservationRepository,
                              BookService bookService,
                              UserService userService,
                              BookCopyService bookCopyService,
                              ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.bookService = bookService;
        this.userService = userService;
        this.bookCopyService = bookCopyService;
        this.reservationMapper = reservationMapper;
    }

    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
    }

    public ReservationResponseDto getDtoById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
    }

    public List<ReservationResponseDto> getAll() {

        return reservationRepository
                .findAll()
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    public List<ReservationResponseDto> search(Long bookId, Long userId,
                                               ReservationStatus status, LocalDate reservationDateFrom,
                                               LocalDate reservationDateTo,
                                               LocalDate expirationDateFrom,
                                               LocalDate expirationDateTo) {

        Specification<Reservation> spec = (root, query, cb) -> cb.conjunction();

        if (bookId != null) spec = spec.and(ReservationSpecification.bookIdEquals(bookId));
        if (userId != null) spec = spec.and(ReservationSpecification.userIdEquals(userId));
        if (status != null) spec = spec.and(ReservationSpecification.statusEquals(status));
        if (reservationDateFrom != null || reservationDateTo != null) spec =
                spec.and(ReservationSpecification.dateBetween(ReservationDateType.RESERVATION, reservationDateFrom, reservationDateTo));
        if (expirationDateFrom != null || expirationDateTo != null) spec =
                spec.and(ReservationSpecification.dateBetween(ReservationDateType.EXPIRATION, expirationDateFrom, expirationDateTo));

        return reservationRepository.findAll(spec)
                .stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    public List<Reservation> searchForEntity(Long bookId, Long userId,
                                               ReservationStatus status, LocalDate reservationDateFrom,
                                               LocalDate reservationDateTo,
                                               LocalDate expirationDateFrom,
                                               LocalDate expirationDateTo) {

        Specification<Reservation> spec = (root, query, cb) -> cb.conjunction();

        if (bookId != null) spec = spec.and(ReservationSpecification.bookIdEquals(bookId));
        if (userId != null) spec = spec.and(ReservationSpecification.userIdEquals(userId));
        if (status != null) spec = spec.and(ReservationSpecification.statusEquals(status));
        if (reservationDateFrom != null || reservationDateTo != null) spec =
                spec.and(ReservationSpecification.dateBetween(ReservationDateType.RESERVATION, reservationDateFrom, reservationDateTo));
        if (expirationDateFrom != null || expirationDateTo != null) spec =
                spec.and(ReservationSpecification.dateBetween(ReservationDateType.EXPIRATION, expirationDateFrom, expirationDateTo));

        return reservationRepository.findAll(spec)
                .stream()
                .toList();
    }

    public ReservationResponseDto create(ReservationRequestDto r) {

        Book b = bookService.getById(r.getBookId());
        User u = userService.getById(r.getUserId());

        if (!bookCopyService.isBookAvailable(r.getBookId())) {
            throw new BookNotAvailableException("No available copies for book with id: " + r.getBookId());
        }

        if (hasActiveReservation(r.getBookId(), r.getUserId())) {
            throw new DuplicateResourceException("User already has an active reservation for book with id: " + r.getBookId());
        }

        Reservation reservation = new Reservation(
                b,
                u,
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                ReservationStatus.PENDING
        );

        Reservation saved = reservationRepository.save(reservation);

        return reservationMapper.toDto(saved);
    }

    public void fulfill(Long id) {
        Reservation reservation = getById(id);
        reservation.setStatus(ReservationStatus.FULFILLED);
        reservationRepository.save(reservation);
    }

    public void cancel(Long id) {
        Reservation reservation = getById(id);
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    public void delete(Long id) {
        reservationRepository.delete(getById(id));
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void cancelExpiredReservation() {
        List<Reservation> expired = reservationRepository
                .findAll(ReservationSpecification.statusEquals(ReservationStatus.PENDING)
                        .and(ReservationSpecification.dateBetween(
                                ReservationDateType.EXPIRATION, null, LocalDate.now()
                        )));

        expired.forEach(r -> {
            r.setStatus(ReservationStatus.CANCELLED);
            reservationRepository.save(r);
        });
    }

    public boolean hasActiveReservation(Long bookId, Long userId) {

        return !reservationRepository
                .findAll(ReservationSpecification.bookIdEquals(bookId)
                        .and(ReservationSpecification.userIdEquals(userId))
                        .and(ReservationSpecification.statusEquals(ReservationStatus.PENDING))
                )
                .isEmpty();
    }

}
