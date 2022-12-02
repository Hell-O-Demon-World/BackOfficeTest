package com.golfzonaca.backoffice.service.room;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.Room;
import com.golfzonaca.backoffice.domain.RoomKind;
import com.golfzonaca.backoffice.repository.room.RoomRepository;
import com.golfzonaca.backoffice.repository.roomkind.RoomKindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomKindRepository roomKindRepository;

    public List<RoomKind> save(Place place, List<Integer> roomQuantity) {
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

    private List<RoomKind> saveRooms(Place place, List<Integer> roomQuantity) {
        List<RoomKind> roomKindList = new LinkedList<>();
        for (long i = 0; i < roomQuantity.size(); i++) {
            RoomKind roomKind = roomKindRepository.findById(i + 1);
            for (int j = 0; j < roomQuantity.get((int) i); j++) {
                Room room = roomRepository.save(new Room(roomKind, place));
                roomKindList.add(room.getRoomKind());
            }
        }
        return roomKindList;
    }
}
