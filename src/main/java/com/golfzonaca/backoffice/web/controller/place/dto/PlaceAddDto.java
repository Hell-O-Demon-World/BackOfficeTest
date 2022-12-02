package com.golfzonaca.backoffice.web.controller.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceAddDto {

    private Long id;
    private String placeName;
    private String placeDescription;
    private List<String> placeOpenDays;
    private String placeStart;
    private String placeEnd;
    private List<String> placeAddInfo;
    private String address;
    private String postalCode;
    private List<Integer> roomQuantity;
    private List<MultipartFile> placeImage;
    private List<MultipartFile> deskImage;
    private List<MultipartFile> meetingRoom4Image;
    private List<MultipartFile> meetingRoom6Image;
    private List<MultipartFile> meetingRoom10Image;
    private List<MultipartFile> meetingRoom20Image;
    private List<MultipartFile> office20Image;
    private List<MultipartFile> office40Image;
    private List<MultipartFile> office70Image;
    private List<MultipartFile> office100Image;
}