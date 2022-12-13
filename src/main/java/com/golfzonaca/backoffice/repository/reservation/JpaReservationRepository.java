package com.golfzonaca.backoffice.repository.reservation;

import com.golfzonaca.backoffice.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRoomId(Long roomId);

    Optional<Reservation> findFirstById(Long reservationId);
}
