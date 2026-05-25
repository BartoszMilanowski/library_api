package com.bartmilan.library_api.mapper;

import com.bartmilan.library_api.dto.ReservationDtos.ReservationResponseDto;
import com.bartmilan.library_api.model.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationResponseDto toDto(Reservation r) {
        return new ReservationResponseDto(
                r.getId(),
                r.getBook().getId(),
                r.getBook().getOriginalTitle(),
                r.getUser().getId(),
                r.getUser().getEmail(),
                r.getReservationDate(),
                r.getExpirationDate(),
                r.getStatus()
        );
    }
}
