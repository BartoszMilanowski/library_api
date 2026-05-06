package com.bartmilan.library_api.service;

import com.bartmilan.library_api.dto.ReservationDtos.ReservationRequestDto;
import com.bartmilan.library_api.exception.DuplicateResourceException;
import com.bartmilan.library_api.exception.ResourceNotFoundException;
import com.bartmilan.library_api.model.Book;
import com.bartmilan.library_api.model.Reservation;
import com.bartmilan.library_api.model.enums.ReservationDateType;
import com.bartmilan.library_api.model.enums.ReservationStatus;
import com.bartmilan.library_api.model.User;
import com.bartmilan.library_api.repository.ReservationRepository;
import com.bartmilan.library_api.specification.ReservationSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private BookService bookService;
    private UserService userService;

    public ReservationService(ReservationRepository reservationRepository,
                              BookService bookService,
                              UserService userService) {
        this.reservationRepository = reservationRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public List<Reservation> search(Long bookId, Long userId,
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

    public Reservation create(ReservationRequestDto r) {

        Book b = bookService.getById(r.getBookId());
        User u = userService.getById(r.getUserId());

        boolean alreadyReserved = !reservationRepository
                .findAll(ReservationSpecification.bookIdEquals(r.getBookId())
                        .and(ReservationSpecification.userIdEquals(r.getUserId()))
                        .and(ReservationSpecification.statusEquals(ReservationStatus.PENDING))
                )
                .isEmpty();

        if (alreadyReserved) {
            throw new DuplicateResourceException("User already has an active reservation for book with id: " + r.getBookId());
        }

        Reservation reservation = new Reservation(
                b,
                u,
                LocalDate.now(),
                LocalDate.now().plusDays(3),
                ReservationStatus.PENDING
        );

        return reservationRepository.save(reservation);
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

}
