package com.golfzonaca.backoffice.repository.mybatis;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.repository.PlaceRepository;
import com.golfzonaca.backoffice.repository.dto.PlaceUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisPlaceRepository implements PlaceRepository {

    private final PlaceMapper placeMapper;

    @Override
    public Place save(Place place) {
        placeMapper.save(place);
        return place;
    }

    @Override
    public void update(Long id, PlaceUpdateDto updateParam) {
        placeMapper.update(id, updateParam);
    }

    @Override
    public void delete(Long id) {
        placeMapper.delete(id);
    }

    @Override
    public Optional<Place> findById(Long id) {
        return placeMapper.findById(id);
    }

    @Override
    public List<Place> findAll(Long companyId) {
        return placeMapper.findAll(companyId);
    }
}
