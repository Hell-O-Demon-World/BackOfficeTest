package com.golfzonaca.backoffice.repository;

import com.golfzonaca.backoffice.domain.Location;
import com.golfzonaca.backoffice.repository.dto.LocationUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class LocationRepositoryTest {

    @Autowired
    LocationRepository locationRepository;

    @Test
    void 주소저장() {
        //Given
        Location location = new Location("경기도 성남시 분당구", "11111");
        //When
        Location savedLocation = locationRepository.save(location);
        //Then
        Location findLocation = locationRepository.findByAddressId(savedLocation.getId());
        assertThat(savedLocation).isEqualTo(findLocation);
    }

    @Test
    void 주소수정() {
        //Given
        Location location = new Location("경기도 성남시 분당구", "11111");
        Location savedLocation = locationRepository.save(location);
        long AddressId = savedLocation.getId();
        LocationUpdateDto updateParam = new LocationUpdateDto("서울시 강남구", "00000");
        //When
        locationRepository.update(AddressId, updateParam);
        //Then
        Location findLocation = locationRepository.findByAddressId(AddressId);
        assertThat(findLocation.getAddress()).isEqualTo(updateParam.getAddress());
        assertThat(findLocation.getPostalCode()).isEqualTo(updateParam.getPostalCode());
    }

    @Test
    void 주소조회() {
        //Given
        Location location = new Location("경기도 성남시 분당구", "11111");
        Location savedLocation = locationRepository.save(location);
        long AddressId = savedLocation.getId();
        //When
        Location findLocation = locationRepository.findByAddressId(AddressId);
        //Then
        assertThat(findLocation).isEqualTo(savedLocation);
    }
}