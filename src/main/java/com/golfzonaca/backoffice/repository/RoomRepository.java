package com.golfzonaca.backoffice.repository;

import com.golfzonaca.backoffice.domain.Room;
import com.golfzonaca.backoffice.repository.dto.RoomStatusDto;
import com.golfzonaca.backoffice.repository.dto.RoomUpdateDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoomRepository {

    Room save(Room room);

    void updateInfo(long id, RoomUpdateDto updateParam);

    void updateStatus(long id, RoomStatusDto updateStatus);

    void deleteByPlaceId(long placeId);

    Optional<Room> findByRoomId(long id);

    List<Room> findAllByPlaceId(long id);

    List<Map<Integer, Integer>> countByRoomType(long placeId);
}
