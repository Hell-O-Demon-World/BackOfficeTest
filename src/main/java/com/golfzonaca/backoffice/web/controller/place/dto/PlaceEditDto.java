package com.golfzonaca.backoffice.web.controller.place.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlaceEditDto {

    private String placeName;
    private String placeDescription;
    private List<String> placeOpenDays;
    private String placeStart;
    private String placeEnd;
    private List<String> placeAddInfo;
    private String address;
    private String postalCode;
}