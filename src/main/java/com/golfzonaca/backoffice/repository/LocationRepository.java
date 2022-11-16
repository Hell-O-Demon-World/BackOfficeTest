package com.golfzonaca.backoffice.repository;

import com.golfzonaca.backoffice.domain.Location;
import com.golfzonaca.backoffice.repository.dto.LocationUpdateDto;

public interface LocationRepository {

    Location save(Location address);

    void update(long id, LocationUpdateDto updateParam);

    void delete(long id);

    Location findByAddressId(long id);

}
