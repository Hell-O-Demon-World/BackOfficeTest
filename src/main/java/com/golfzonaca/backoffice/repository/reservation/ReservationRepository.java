package com.golfzonaca.backoffice.repository.reservation;

import com.golfzonaca.backoffice.domain.Reservation;
import com.golfzonaca.backoffice.web.controller.reservation.dto.ReservationSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ReservationRepository {
    private final JpaReservationRepository jpaRepository;
    private final QueryReservationRepository queryRepository;

    public Reservation findById(Long placeId, Long reservationId) {
        return queryRepository.findByIdAndPlaceId(placeId, reservationId);
    }

    public List<Reservation> findByCondition(Long placeId, ReservationSearchCond data) {
        if (StringUtils.hasText(data.getSearchWord())) {
            return queryRepository.findByCondition(placeId, data);
        } else {
            return jpaRepository.findByPlaceId(placeId);
        }
    }

    public List<Reservation> findByResStartTime(Long roomId, LocalDate date, LocalTime time) {
        return queryRepository.findByResStartTime(roomId, date, time);
    }

    public void delete(Reservation reservation) {
        jpaRepository.delete(reservation);
    }
}
