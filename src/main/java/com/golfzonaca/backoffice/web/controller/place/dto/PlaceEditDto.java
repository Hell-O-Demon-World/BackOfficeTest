package com.golfzonaca.backoffice.web.controller.place.dto;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class PlaceEditDto {

    private String placeName;
    private String placeDescription;
    private List<String> placeOpenDays;
    private String placeStart;
    private String placeEnd;
    private List<String> placeAddInfo;
    private Long addressId;
}