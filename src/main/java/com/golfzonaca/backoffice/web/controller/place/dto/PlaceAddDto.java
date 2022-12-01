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
    private List<MultipartFile> deskRoomImage;
    private List<MultipartFile> meetingRoom4RoomImage;
    private List<MultipartFile> meetingRoom6RoomImage;
    private List<MultipartFile> meetingRoom10RoomImage;
    private List<MultipartFile> meetingRoom20RoomImage;
    private List<MultipartFile> office20RoomImage;
    private List<MultipartFile> office40RoomImage;
    private List<MultipartFile> office70RoomImage;
    private List<MultipartFile> office100RoomImage;
}