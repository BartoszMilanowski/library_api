package com.bartmilan.library_api.controller;

import com.bartmilan.library_api.dto.ReservationDtos.ReservationRequestDto;

import com.bartmilan.library_api.dto.ReservationDtos.ReservationResponseDto;
import com.bartmilan.library_api.model.Reservation;
import com.bartmilan.library_api.model.enums.ReservationStatus;
import com.bartmilan.library_api.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAll() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponseDto> getById(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.getDtoById(reservationId));
    }k

    @GetMapping("/search")
    public ResponseEntity<List<ReservationResponseDto>> search(
            @RequestParam(required = false) Long bookId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) ReservationStatus status,
            @RequestParam(required = false) LocalDate reservationDateFrom,
            @RequestParam(required = false) LocalDate reservationDateTo,
            @RequestParam(required = false) LocalDate expirationDateFrom,
            @RequestParam(required = false) LocalDate expirationDateTo
    ) {
        return ResponseEntity.ok(reservationService.search(bookId, userId, status, reservationDateFrom,
                reservationDateTo, expirationDateFrom, expirationDateTo));
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> reserveBook(@RequestBody ReservationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.create(dto));
    }

    @PutMapping("/fulfill/{reservationId}")
    public ResponseEntity<Void> fulfill(@PathVariable Long reservationId) {
        reservationService.fulfill(reservationId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancel/{reservationId}")
    public ResponseEntity<Void> cancel(@PathVariable Long reservationId) {
        reservationService.cancel(reservationId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> delete(@PathVariable Long reservationId) {
        reservationService.delete(reservationId);
        return ResponseEntity.noContent().build();
    }
}

