package com.golfzonaca.backoffice.service.reservation;

import com.golfzonaca.backoffice.domain.Reservation;
import com.golfzonaca.backoffice.repository.reservation.ReservationRepository;
import com.golfzonaca.backoffice.web.controller.reservation.dto.ReservationSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation findById(Long placeId, Long reservationId) {
        return reservationRepository.findById(placeId, reservationId);
    }

    public List<Reservation> findByCondition(Long roomId, ReservationSearchCond data) {
        return reservationRepository.findByCondition(roomId, data);
    }

    public void delete(Long placeId, Long reservationId) {
        reservationRepository.delete(reservationRepository.findById(placeId, reservationId));
    }

    public List<Reservation> findByResStartTime(Long roomId, LocalDate date, LocalTime time) {
        return reservationRepository.findByResStartTime(roomId, date, time);
    }
}
