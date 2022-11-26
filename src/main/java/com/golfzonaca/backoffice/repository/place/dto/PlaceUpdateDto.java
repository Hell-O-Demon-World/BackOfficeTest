package com.golfzonaca.backoffice.repository.place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class PlaceUpdateDto {

    private String placeName;
    private String placeDescription;
    private String placeOpenDays;
    private LocalTime placeStart;
    private LocalTime placeEnd;
    private String placeAddInfo;
    private String postalCode;
    private String Address;

    public PlaceUpdateDto() {
    }
}