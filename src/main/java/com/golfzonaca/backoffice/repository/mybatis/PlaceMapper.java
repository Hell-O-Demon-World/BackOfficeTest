package com.golfzonaca.backoffice.repository.mybatis;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.repository.dto.PlaceUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PlaceMapper {

    void save(Place place);

    void update(@Param("id") Long id, @Param("updateParam") PlaceUpdateDto updateParam);

    void delete(Long id);

    Optional<Place> findById(Long Id);

    List<Place> findAll(Long companyId);
}
