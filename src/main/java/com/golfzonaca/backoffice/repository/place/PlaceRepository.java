package com.golfzonaca.backoffice.repository.place;

import com.golfzonaca.backoffice.domain.Address;
import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.repository.place.dto.PlaceUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Repository
@Transactional
@RequiredArgsConstructor
public class PlaceRepository {

    private final JpaPlaceRepository jpaRepository;
    private final QueryPlaceRepository queryRepository;

    public Place save(Place place) {
        return jpaRepository.save(place);
    }

    public Place findById(Long id) {
        return jpaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 공간입니다."));
    }

    public Place update(Place place, PlaceUpdateDto data) {
        place.updatePlaceName(data.getPlaceName());
        place.updateDescription(data.getPlaceDescription());
        place.updateOpenDays(data.getPlaceOpenDays());
        place.updatePlaceStart(data.getPlaceStart());
        place.updatePlaceEnd(data.getPlaceEnd());
        place.updateAddress(new Address(data.getAddress(), data.getPostalCode()));
        place.updatePlaceAddInfo(data.getPlaceAddInfo());
        return place;
    }

    public void delete(Place place) {
        jpaRepository.delete(place);
    }
}
