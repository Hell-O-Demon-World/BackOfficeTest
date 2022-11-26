package com.golfzonaca.backoffice.repository.ratepoint;

import com.golfzonaca.backoffice.domain.RatePoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RatePointRepository {

    private final JpaRatePointRepository jpaRepository;


    public RatePoint save(RatePoint ratePoint) {
        return jpaRepository.save(ratePoint);
    }

    public void delete(RatePoint ratePoint) {
        jpaRepository.delete(ratePoint);
    }
}
