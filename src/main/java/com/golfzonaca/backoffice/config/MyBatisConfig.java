package com.golfzonaca.backoffice.config;

import com.golfzonaca.backoffice.repository.CompanyRepository;
import com.golfzonaca.backoffice.repository.LocationRepository;
import com.golfzonaca.backoffice.repository.PlaceRepository;
import com.golfzonaca.backoffice.repository.RoomRepository;
import com.golfzonaca.backoffice.repository.mybatis.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {
    private final PlaceMapper placeMapper;
    private final CompanyMapper companyMapper;
    private final LocationMapper locationMapper;
    private final RoomMapper roomMapper;

    @Bean
    public PlaceRepository placeRepository() {
        return new MyBatisPlaceRepository(placeMapper);
    }

    @Bean
    public CompanyRepository companyRepository() {
        return new MyBatisCompanyRepository(companyMapper);
    }

    @Bean
    public LocationRepository addressRepository() {
        return new MyBatisLocationRepository(locationMapper);
    }
    
    @Bean
    public RoomRepository roomRepository() {
        return new MyBatisRoomRepository(roomMapper);
    }
}