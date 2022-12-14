package com.golfzonaca.backoffice.repository.room;

import com.golfzonaca.backoffice.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findFirstByPlaceId(Long placeId);
}
