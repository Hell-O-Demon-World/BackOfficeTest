package com.golfzonaca.backoffice.service.image;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.PlaceImage;
import com.golfzonaca.backoffice.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final StorePlaceImage storePlaceImage;
    private final ImageRepository imageRepository;

    public void save(List<MultipartFile> multipartFiles, Place place) throws IOException {
        List<PlaceImage> placeImages = storePlaceImage.storePlaceFiles(multipartFiles, place);
        for (PlaceImage placeImage : placeImages) {
            imageRepository.savePlaceImage(placeImage);
        }
    }
}
