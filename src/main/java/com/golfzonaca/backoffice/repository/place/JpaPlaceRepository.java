package com.golfzonaca.backoffice.repository.place;

import com.golfzonaca.backoffice.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface JpaPlaceRepository extends JpaRepository<Place, Long> {
}
