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

    public Reservation findById(Long reservationId) {
        return jpaRepository.findFirstById(reservationId).orElseThrow(()-> new RuntimeException("예약이 없습니다."));
    }

    public List<Reservation> findByPlaceIdAndPeriod(Long placeId, LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return queryRepository.findByPlaceId(placeId);
        } else {
            return queryRepository.findByPlaceIdAndPeriod(placeId, startDate, endDate);
        }
    }

    public List<Reservation> findByCondition(Long roomId, ReservationSearchCond data) {
        if (StringUtils.hasText(data.getSearchWord())) {
            return queryRepository.findByCondition(roomId, data);
        } else {
            return jpaRepository.findByRoomId(roomId);
        }
    }

    public List<Reservation> findByResStartTime(Long roomId, LocalDate date, LocalTime time) {
        return queryRepository.findByResStartTime(roomId, date, time);
    }

    public void delete(Reservation reservation) {
        jpaRepository.delete(reservation);
    }
}
