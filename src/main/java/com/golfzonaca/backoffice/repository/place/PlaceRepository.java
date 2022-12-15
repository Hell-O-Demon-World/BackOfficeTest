package com.golfzonaca.backoffice.repository.place;

import com.golfzonaca.backoffice.domain.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public void delete(Place place) {
        jpaRepository.delete(place);
    }

    public List<Place> findByCompanyId(Long companyId) {
        return jpaRepository.findAllByCompanyId(companyId);
    }
}
