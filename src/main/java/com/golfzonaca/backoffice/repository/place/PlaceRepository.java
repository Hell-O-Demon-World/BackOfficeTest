package com.golfzonaca.backoffice.repository.place;

import com.golfzonaca.backoffice.domain.Address;
import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.web.controller.place.dto.PlaceEditDto;
import com.golfzonaca.backoffice.web.controller.typeconverter.TimeFormatter;
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

    public Place update(Place place, PlaceEditDto data) {
        place.updatePlaceName(data.getPlaceName());
        place.updateDescription(data.getPlaceDescription());
        place.updateOpenDays(data.getPlaceOpenDays().toString());
        place.updatePlaceStart(TimeFormatter.toLocalTime(data.getPlaceStart()));
        place.updatePlaceEnd(TimeFormatter.toLocalTime(data.getPlaceEnd()));
        place.updateAddress(new Address(data.getAddress(), data.getPostalCode()));
        place.updatePlaceAddInfo(data.getPlaceAddInfo().toString());
        return place;
    }

    public void delete(Place place) {
        jpaRepository.delete(place);
    }

    public Place findByCompanyId(Long companyId) {
        return jpaRepository.findFirstByCompanyId(companyId).orElseThrow(()-> new RuntimeException("존재하지 않는 place 입니다."));
    }
}
