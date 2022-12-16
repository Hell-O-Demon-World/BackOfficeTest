package com.golfzonaca.backoffice.service.image;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.PlaceImage;
import com.golfzonaca.backoffice.domain.RoomImage;
import com.golfzonaca.backoffice.domain.RoomKind;
import com.golfzonaca.backoffice.exception.FileUploadFailureException;
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
    private final S3ImageUploader s3ImageUploader;
    private final ImageRepository imageRepository;

    public void savePlaceImage(List<MultipartFile> multipartFiles, Place place) {
        try {
            List<PlaceImage> placeImages = s3ImageUploader.storePlaceFiles(multipartFiles, place);
            for (PlaceImage placeImage : placeImages) {
                imageRepository.savePlaceImage(placeImage);
            }
        } catch (IOException e) {
            throw new FileUploadFailureException("공간 이미지 업로드에 실패하였습니다.");
        }
    }

    public void saveRoomImage(List<MultipartFile> multipartFiles, Place place, RoomKind roomKind) {
        try {
            List<RoomImage> roomImages = s3ImageUploader.storeRoomFiles(multipartFiles, place, roomKind);
            for (RoomImage roomImage : roomImages) {
                imageRepository.saveRoomImage(roomImage);
            }
        } catch (IOException e) {
            throw new FileUploadFailureException("사무공간 이미지 업로드에 실패하였습니다.");
        }
    }
}
