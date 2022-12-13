package com.golfzonaca.backoffice.service.reservation;

import com.golfzonaca.backoffice.domain.Payment;
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

    public List<Reservation> findByCondition(Long roomId, ReservationSearchCond data) {
        return reservationRepository.findByCondition(roomId, data);
    }

    public void cancelReservation(Long reservationId) {
        Reservation findReservation = reservationRepository.findById(reservationId);
        reservationRepository.delete(findReservation);
    }

    public List<Reservation> findByResStartTime(Long roomId, LocalDate date, LocalTime time) {
        return reservationRepository.findByResStartTime(roomId, date, time);
    }
}
