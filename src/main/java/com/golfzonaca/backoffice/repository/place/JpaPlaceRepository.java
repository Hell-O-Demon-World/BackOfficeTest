package com.golfzonaca.backoffice.repository.place;

import com.golfzonaca.backoffice.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface JpaPlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findAllByCompanyId(Long companyId);
}
