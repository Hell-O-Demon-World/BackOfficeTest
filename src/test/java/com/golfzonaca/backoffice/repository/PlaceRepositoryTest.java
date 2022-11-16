package com.golfzonaca.backoffice.repository;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.repository.dto.PlaceUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class PlaceRepositoryTest {

    @Autowired
    PlaceRepository placeRepository;

    @Test
    void 공간저장() {
        //Given
        Place place = new Place(2L, "테스트1", "시험1입니다.", "[Mon, Tue, Wed]", "11:00:00", "22:00:00", "[Wifi, Coffee]", 1L);
        //When
        Place savedPlace = placeRepository.save(place);
        //Then
        Place findPlace = placeRepository.findById(place.getId()).get();
        assertThat(findPlace).isEqualTo(savedPlace);
    }

    @Test
    void 공간수정() {
        //Given
        Place place = new Place(2L, "테스트1", "시험1입니다.", "[Mon, Tue, Wed]", "11:00:00", "22:00:00", "[Wifi, Coffee]", 1L);
        Place savedPlace = placeRepository.save(place);
        Long placeId = savedPlace.getId();

        //When
        PlaceUpdateDto updateParam = new PlaceUpdateDto("테스트2", "시험2입니다.", "[Sat, Sun]", "10:00:00", "12:00:00", "[Wifi, Coffee, Parking]", 2L);
        placeRepository.update(placeId, updateParam);

        //Then
        Place findPlace = placeRepository.findById(placeId).get();
        assertThat(findPlace.getPlaceName()).isEqualTo(updateParam.getPlaceName());
        assertThat(findPlace.getPlaceDescription()).isEqualTo(updateParam.getPlaceDescription());
        assertThat(findPlace.getPlaceOpenDays()).isEqualTo(updateParam.getPlaceOpenDays());
        assertThat(findPlace.getPlaceStart()).isEqualTo(updateParam.getPlaceStart());
        assertThat(findPlace.getPlaceEnd()).isEqualTo(updateParam.getPlaceEnd());
        assertThat(findPlace.getPlaceAddInfo()).isEqualTo(updateParam.getPlaceAddInfo());
        assertThat(findPlace.getAddressId()).isEqualTo(updateParam.getAddressId());
    }

    @Test
    void 공간삭제() {
        //Given
        Place place = new Place(2L, "테스트1", "시험1입니다.", "[Mon, Tue, Wed]", "11:00:00", "22:00:00", "[Wifi, Coffee]", 1L);
        Place savedPlace = placeRepository.save(place);
        Long placeId = savedPlace.getId();

        //When
        placeRepository.delete(placeId);

        //Then
        Optional<Place> result = placeRepository.findById(placeId);
        assertThat(result).isEmpty();

    }

    @Test
    void findById() {
        //given
        Place place = new Place(2L, "테스트1", "시험1입니다.", "[Mon, Tue, Wed]", "11:00:00", "22:00:00", "[Wifi, Coffee]", 1L);
        Place savedPlace = placeRepository.save(place);
        Long placeId = savedPlace.getId();

        //When
        Optional<Place> result = placeRepository.findById(placeId);

        //Then
        assertThat(result.get()).isEqualTo(savedPlace);
    }

    @Test
    void findAllByCompanyId() {
        //given
        Long companyId = 1L;
        Place placeA = new Place(companyId, "테스트1", "시험1입니다.", "[Mon, Tue, Wed]", "11:00:00", "22:00:00", "[Wifi, Coffee]", 1L);
        Place savedPlaceA = placeRepository.save(placeA);
        Place placeB = new Place(companyId, "테스트2", "시험2입니다.", "[Mon, Tue, Wed]", "11:00:00", "22:00:00", "[Wifi, Coffee]", 1L);
        Place savedPlaceB = placeRepository.save(placeB);

        //when
        List<Place> result = placeRepository.findAll(companyId);

        //then
        assertThat(result).contains(savedPlaceA, savedPlaceB);
    }

}