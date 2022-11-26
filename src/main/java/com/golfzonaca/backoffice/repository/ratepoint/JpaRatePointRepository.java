package com.golfzonaca.backoffice.repository.ratepoint;

import com.golfzonaca.backoffice.domain.RatePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface JpaRatePointRepository extends JpaRepository<RatePoint, Long> {
}
