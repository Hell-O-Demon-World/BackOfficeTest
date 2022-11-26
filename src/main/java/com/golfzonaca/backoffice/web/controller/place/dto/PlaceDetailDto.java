package com.golfzonaca.backoffice.web.controller.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDetailDto {

    private Long id;
    private String placeName;
    private String placeDescription;
    private List<String> placeOpenDays;
    private String placeStart;
    private String placeEnd;
    private List<String> placeAddInfo;
    private String address;
    private String postalCode;
    private Map<String, Integer> roomQuantity;
}