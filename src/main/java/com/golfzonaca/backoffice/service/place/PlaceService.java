package com.golfzonaca.backoffice.service.place;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.Room;
import com.golfzonaca.backoffice.domain.type.RoomType;
import com.golfzonaca.backoffice.repository.place.PlaceRepository;
import com.golfzonaca.backoffice.web.controller.place.dto.PlaceEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository repository;

    public Place save(Place place) {
        return repository.save(place);
    }

    public Place findById(Long placeId) {
        return repository.findById(placeId);
    }

    public Place update(Place place, PlaceEditDto data) {
        return repository.update(place, data);
    }

    public void delete(Place place) {
        repository.delete(place);
    }

    public Map<String, Integer> calculateRoomQuantity(Place place) {
        Map<String, Integer> roomQuantity = new LinkedHashMap<>();
        RoomType[] roomTypes = RoomType.values();
        for (RoomType roomType : roomTypes) {
            int count = 0;
            for (Room room : place.getRooms()) {
                if (roomType.name().equals(room.getRoomKind().getRoomType())) {
                    count++;
                }
            }
            roomQuantity.put(roomType.getDescription(), count);
        }
        return roomQuantity;
    }
}
