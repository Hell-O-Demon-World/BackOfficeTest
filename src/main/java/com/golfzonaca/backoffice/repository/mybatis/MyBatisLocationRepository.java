package com.golfzonaca.backoffice.repository.mybatis;

import com.golfzonaca.backoffice.domain.Location;
import com.golfzonaca.backoffice.repository.LocationRepository;
import com.golfzonaca.backoffice.repository.dto.LocationUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisLocationRepository implements LocationRepository {

    private final LocationMapper locationMapper;

    @Override
    public Location save(Location address) {
        locationMapper.save(address);
        return address;
    }

    @Override
    public void update(long id, LocationUpdateDto updateParam) {
        locationMapper.update(id, updateParam);
    }

    @Override
    public void delete(long id) {
        locationMapper.delete(id);
    }

    @Override
    public Location findByAddressId(long id) {
        return locationMapper.findByAddressId(id);
    }
}
