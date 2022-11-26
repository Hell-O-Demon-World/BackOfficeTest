package com.golfzonaca.backoffice.service.ratepoint;

import com.golfzonaca.backoffice.domain.RatePoint;
import com.golfzonaca.backoffice.repository.ratepoint.RatePointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RatePointService {

    private final RatePointRepository ratePointRepository;

    public RatePoint save(RatePoint ratePoint) {
        return ratePointRepository.save(ratePoint);
    }

    public void delete(RatePoint ratePoint) {
        ratePointRepository.delete(ratePoint);
    }
}
