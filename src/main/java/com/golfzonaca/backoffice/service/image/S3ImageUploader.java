package com.golfzonaca.backoffice.service.image;

import com.amazonaws.services.s3.AmazonS3Client;
import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.PlaceImage;
import com.golfzonaca.backoffice.domain.RoomImage;
import com.golfzonaca.backoffice.domain.RoomKind;
import com.golfzonaca.backoffice.exception.FileConvertFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3ImageUploader {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<PlaceImage> storePlaceFiles(List<MultipartFile> multipartFiles, Place place) throws IOException {
        List<PlaceImage> storePlaceFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storePlaceFileResult.add(storePlaceFile(multipartFile, place));
            }
        }
        return storePlaceFileResult;
    }

    public PlaceImage storePlaceFile(MultipartFile multipartFile, Place place) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        File file = convert(multipartFile, storeFileName).orElseThrow(() -> new NoSuchElementException("파일 변환에 실패하였습니다."));
        amazonS3Client.putObject(bucket, place.getId().toString() + "/" + "PLACE" + "/" + file.getName(), file);
        String fullPath = getUrl(place.getId().toString() + "/" + "PLACE" + "/" + file.getName());
        file.delete();
        return new PlaceImage(originalFilename, storeFileName, fullPath, place);
    }

    public List<RoomImage> storeRoomFiles(List<MultipartFile> multipartFiles, Place place, RoomKind roomKind) throws IOException {
        List<RoomImage> storeRoomFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeRoomFileResult.add(storeRoomFile(multipartFile, place, roomKind));
            }
        }
        return storeRoomFileResult;
    }

    public RoomImage storeRoomFile(MultipartFile multipartFile, Place place, RoomKind roomKind) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        File file = convert(multipartFile, storeFileName).orElseThrow(() -> new FileConvertFailException("파일 변환에 실패하였습니다."));
        amazonS3Client.putObject(bucket, place.getId().toString() + "/" + roomKind.getRoomType() + "/" + file.getName(), file);
        String fullPath = getUrl(place.getId().toString() + "/" + roomKind.getRoomType() + "/" + file.getName());
        file.delete();
        return new RoomImage(originalFilename, storeFileName, fullPath, place, roomKind);
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extracted(originalFilename);
        return uuid + "." + ext;
    }

    private String extracted(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    private Optional<File> convert(MultipartFile multipartFile, String storeFileName) throws IOException {
        File file = new File(storeFileName);
        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }

    public String getUrl(String filename) {
        return amazonS3Client.getUrl(bucket, filename).toString();
    }
}
