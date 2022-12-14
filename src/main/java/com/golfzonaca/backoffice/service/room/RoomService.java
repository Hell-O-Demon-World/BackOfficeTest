package com.golfzonaca.backoffice.service.room;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.Room;
import com.golfzonaca.backoffice.domain.RoomKind;
import com.golfzonaca.backoffice.domain.RoomStatus;
import com.golfzonaca.backoffice.repository.room.RoomRepository;
import com.golfzonaca.backoffice.repository.roomkind.RoomKindRepository;
import com.golfzonaca.backoffice.repository.roomstatus.RoomStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomKindRepository roomKindRepository;
    private final RoomStatusRepository roomStatusRepository;

    public Set<RoomKind> save(Place place, List<Integer> roomQuantity) {
        return saveRooms(place, roomQuantity);
    }

    public Room findById(Long roomId) {
        return roomRepository.findById(roomId);
    }

    public List<Room> findRoomsByRoomType(String roomType) {
        return roomRepository.findRoomsByRoomType(roomType);
    }

    public Room updateStatus(Long roomId, boolean status) {
        Room room = roomRepository.findById(roomId);
        room.getRoomStatus().updateStatus(status);
        return room;
    }

    private Set<RoomKind> saveRooms(Place place, List<Integer> roomQuantity) {
        Set<RoomKind> roomKindList = new LinkedHashSet<>();
        for (long i = 0; i < roomQuantity.size(); i++) {
            RoomKind roomKind = roomKindRepository.findById(i + 1);
            for (int j = 0; j < roomQuantity.get((int) i); j++) {
                Room room = roomRepository.save(new Room(roomKind, place));
                roomStatusRepository.save(new RoomStatus(room, true));
                roomKindList.add(room.getRoomKind());
            }
        }
        return roomKindList;
    }

    public Room findByPlaceId(Long placeId) {
        return roomRepository.findByPlaceId(placeId);
    }
}
