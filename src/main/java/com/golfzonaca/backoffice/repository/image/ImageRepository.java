package com.golfzonaca.backoffice.repository.image;

import com.golfzonaca.backoffice.domain.PlaceImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class ImageRepository {
    private final JpaPlaceImageRepository jpaPlaceImageRepository;

    public void savePlaceImage(PlaceImage placeImage) {
        jpaPlaceImageRepository.save(placeImage);
    }
}
