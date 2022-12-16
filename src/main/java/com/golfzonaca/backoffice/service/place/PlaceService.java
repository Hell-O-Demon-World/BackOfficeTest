package com.golfzonaca.backoffice.service.place;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.golfzonaca.backoffice.domain.*;
import com.golfzonaca.backoffice.domain.type.RoomType;
import com.golfzonaca.backoffice.exception.WrongAddressException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;
    private final RatePointRepository ratePointRepository;
    private final RoomService roomService;
    private final ImageService imageService;

    @Value("${kakao.map.apiKey}")
    private String kakaoMapApiKey;

    public Place save(PlaceAddDto placeAddDto, AddressDto addressDto, Company company) {
        Map<String, String> coordinate = getCoordinate(addressDto.getAddress());

        Address address = addressRepository.save(new Address(coordinate.get("roadAddress"), coordinate.get("postalCode"), Double.valueOf(coordinate.get("longitude")), Double.valueOf(coordinate.get("latitude"))));
        RatePoint ratePoint = ratePointRepository.save(new RatePoint(0F));
        Place place = placeRepository.save(new Place(company, ratePoint, placeAddDto.getPlaceName(), placeAddDto.getPlaceDescription(), DataTypeFormatter.listToString(placeAddDto.getPlaceOpenDays()), TimeFormatter.toLocalTime(placeAddDto.getPlaceStart()), TimeFormatter.toLocalTime(placeAddDto.getPlaceEnd()), DataTypeFormatter.listToString(placeAddDto.getPlaceAddInfo()), address));
        Set<RoomKind> roomKindList = roomService.save(place, placeAddDto.getRoomQuantity());
        imageService.savePlaceImage(placeAddDto.getPlaceImage(), place);
        storeRoomImages(placeAddDto, place, roomKindList);
        return place;
    }

    private void storeRoomImages(PlaceAddDto placeAddDto, Place place, Set<RoomKind> roomKindList) {
        for (RoomKind roomKind : roomKindList) {
            switch (roomKind.getRoomType()) {
                case "DESK":
                    imageService.saveRoomImage(placeAddDto.getDeskImage(), place, roomKind);
                    break;
                case "MEETINGROOM4":
                    imageService.saveRoomImage(placeAddDto.getMeetingRoom4Image(), place, roomKind);
                    break;
                case "MEETINGROOM6":
                    imageService.saveRoomImage(placeAddDto.getMeetingRoom6Image(), place, roomKind);
                    break;
                case "MEETINGROOM10":
                    imageService.saveRoomImage(placeAddDto.getMeetingRoom10Image(), place, roomKind);
                    break;
                case "MEETINGROOM20":
                    imageService.saveRoomImage(placeAddDto.getMeetingRoom20Image(), place, roomKind);
                    break;
                case "OFFICE20":
                    imageService.saveRoomImage(placeAddDto.getOffice20Image(), place, roomKind);
                    break;
                case "OFFICE40":
                    imageService.saveRoomImage(placeAddDto.getOffice40Image(), place, roomKind);
                    break;
                case "OFFICE70":
                    imageService.saveRoomImage(placeAddDto.getOffice70Image(), place, roomKind);
                    break;
                case "OFFICE100":
                    imageService.saveRoomImage(placeAddDto.getOffice100Image(), place, roomKind);
                    break;
            }
        }
    }

    public Place findById(Long placeId) {
        return placeRepository.findById(placeId);
    }

    public Place update(Long placeId, PlaceEditDto data) {
        Place place = placeRepository.findById(placeId);
        Map<String, String> coordinate = getCoordinate(data.getAddress());
        place.updatePlaceName(data.getPlaceName());
        place.updateDescription(data.getPlaceDescription());
        place.updateOpenDays(DataTypeFormatter.listToString(data.getPlaceOpenDays()));
        place.updatePlaceStart(TimeFormatter.toLocalTime(data.getPlaceStart()));
        place.updatePlaceEnd(TimeFormatter.toLocalTime(data.getPlaceEnd()));
        place.getAddress().updateAddress(coordinate.get("postalCode"), coordinate.get("roadAddress"), Double.valueOf(coordinate.get("longitude")), Double.valueOf(coordinate.get("latitude")));
        place.updatePlaceAddInfo(DataTypeFormatter.listToString(data.getPlaceAddInfo()));
        return place;
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

    public List<Place> findByCompanyId(Long companyId) {
        return placeRepository.findByCompanyId(companyId);
    }


    private Map<String, String> getCoordinate(String address) {
        Map<String, String> coordinate = new LinkedHashMap<>();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        String url = UriComponentsBuilder.fromHttpUrl("https://dapi.kakao.com/v2/local/search/address.json")
                .queryParam("query", "{query}")
                .encode()
                .toUriString();

        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        headers.set("AUTHORIZATION", kakaoMapApiKey);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        Map<String, Object> params = new HashMap<>();
        params.put("query", address);

        Object object = restTemplate.exchange(url, HttpMethod.GET, request, Object.class, params).getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.convertValue(object, Map.class);
        List<Map<String, Object>> elements = (List<Map<String, Object>>) map.get("documents");
        if (elements.isEmpty()) {
            throw new WrongAddressException("존재하지 않는 주소입니다.");
        }
        Map<String, Object> coordinateMap = elements.get(0);
        Map<String, String> road_address = (Map<String, String>) coordinateMap.get("road_address");
        String roadAddress = road_address.get("address_name");
        String postalCode = road_address.get("zone_no");
        String longitude = road_address.get("x");
        String latitude = road_address.get("y");
        coordinate.put("roadAddress", roadAddress);
        coordinate.put("postalCode", postalCode);
        coordinate.put("longitude", longitude);
        coordinate.put("latitude", latitude);
        return coordinate;
    }
}
