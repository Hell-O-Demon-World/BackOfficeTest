package com.golfzonaca.backoffice.service.room;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.Room;
import com.golfzonaca.backoffice.domain.RoomKind;
import com.golfzonaca.backoffice.repository.room.RoomRepository;
import com.golfzonaca.backoffice.repository.roomkind.RoomKindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomKindRepository roomKindRepository;

    public void save(Place place, List<Integer> roomQuantity) {
        for (long i = 0; i < roomQuantity.size(); i++) {
            RoomKind roomKind = roomKindRepository.findById(i + 1);
            for (int j = 0; j < roomQuantity.get((int) i); j++) {
                roomRepository.save(new Room(roomKind, place));
            }
        }
    }


    public List<Room> findRoomsByRoomType(String roomType) {
        return roomRepository.findRoomsByRoomType(roomType);
    }


}
