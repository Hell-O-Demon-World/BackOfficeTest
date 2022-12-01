package com.golfzonaca.backoffice.service.image;

import com.golfzonaca.backoffice.domain.Place;
import com.golfzonaca.backoffice.domain.PlaceImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Transactional
public class StorePlaceImage {

    @Value("${file.dir}")
    private String fileDir;

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
        String fullPath = getFullPath(storeFileName, place);
        File file = new File(fullPath);
        if (!file.exists()) {
            makeDirectory(file);
        }
        multipartFile.transferTo(file);
        return new PlaceImage(originalFilename, storeFileName, fullPath, place);
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extracted(originalFilename);
        return uuid + "." + ext;
    }

    public String getFullPath(String filename, Place place) {
        return fileDir + place.getId() + "/" + filename;
    }

    public void makeDirectory(File file) {
        file.mkdirs();
    }

    private String extracted(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
