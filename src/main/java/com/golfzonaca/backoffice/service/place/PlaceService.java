package com.golfzonaca.backoffice.service.place;

import com.golfzonaca.backoffice.auth.token.JwtRepository;
import com.golfzonaca.backoffice.domain.*;
import com.golfzonaca.backoffice.domain.type.RoomType;
import com.golfzonaca.backoffice.repository.address.AddressRepository;
import com.golfzonaca.backoffice.repository.company.CompanyRepository;
import com.golfzonaca.backoffice.repository.place.PlaceRepository;
import com.golfzonaca.backoffice.repository.ratepoint.RatePointRepository;
import com.golfzonaca.backoffice.service.image.ImageService;
import com.golfzonaca.backoffice.service.room.RoomService;
import com.golfzonaca.backoffice.web.controller.place.dto.AddressDto;
import com.golfzonaca.backoffice.web.controller.place.dto.PlaceAddDto;
import com.golfzonaca.backoffice.web.controller.place.dto.PlaceEditDto;
import com.golfzonaca.backoffice.web.controller.typeconverter.DataTypeFormatter;
import com.golfzonaca.backoffice.web.controller.typeconverter.TimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final CompanyRepository companyRepository;
    private final JwtRepository jwtRepository;
    private final AddressRepository addressRepository;
    private final RatePointRepository ratePointRepository;
    private final RoomService roomService;
    private final ImageService imageService;

    public Place save(PlaceAddDto placeAddDto, AddressDto addressDto) {
        Company company = companyRepository.findById(jwtRepository.getUserId());
        Address address = addressRepository.save(new Address(addressDto.getAddress(), addressDto.getPostalCode()));
        RatePoint ratePoint = ratePointRepository.save(new RatePoint(0F));
        Place place = placeRepository.save(new Place(company, ratePoint, placeAddDto.getPlaceName(), placeAddDto.getPlaceDescription(), DataTypeFormatter.listToString(placeAddDto.getPlaceOpenDays()), TimeFormatter.toLocalTime(placeAddDto.getPlaceStart()), TimeFormatter.toLocalTime(placeAddDto.getPlaceEnd()), DataTypeFormatter.listToString(placeAddDto.getPlaceAddInfo()), address));
        List<RoomKind> roomKindList = roomService.save(place, placeAddDto.getRoomQuantity());
        imageService.savePlaceImage(placeAddDto.getPlaceImage(), place);
        for (RoomKind roomKind : roomKindList) {
            imageService.saveRoomImage(placeAddDto.getDeskImage(), place, roomKind);
        }
        return place;
    }

    public Place findById(Long placeId) {
        return placeRepository.findById(placeId);
    }

    public Place update(Place place, PlaceEditDto data) {
        return placeRepository.update(place, data);
    }

    public void delete(Place place) {
        placeRepository.delete(place);
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
