package com.golfzonaca.backoffice.repository.mybatis;

import com.golfzonaca.backoffice.domain.Location;
import com.golfzonaca.backoffice.repository.dto.LocationUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LocationMapper {

    void save(Location address);

    void update(@Param("id") long id, @Param("updateParam") LocationUpdateDto updateParam);

    void delete(long id);

    Location findByAddressId(long id);

}
