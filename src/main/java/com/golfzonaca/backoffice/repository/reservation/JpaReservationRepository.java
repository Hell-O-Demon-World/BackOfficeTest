package com.golfzonaca.backoffice.repository.reservation;

import com.golfzonaca.backoffice.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByPlaceId(Long placeId);
}
