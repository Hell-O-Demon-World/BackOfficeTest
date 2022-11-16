package com.golfzonaca.backoffice.web.form.place;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class PlaceEditForm {

    private String placeName;
    private String placeDescription;
    private List<String> placeOpenDays;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss", timezone = "Asia/Seoul")
    private String placeStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss", timezone = "Asia/Seoul")
    private String placeEnd;
    private List<String> placeAddInfo;
    private Long addressId; //우편번호

    public PlaceEditForm() {
    }

    public PlaceEditForm(String placeName, String placeDescription, List<String> placeOpenDays, String placeStart, String placeEnd, List<String> placeAddInfo, Long addressId) {
        this.placeName = placeName;
        this.placeDescription = placeDescription;
        this.placeOpenDays = placeOpenDays;
        this.placeStart = placeStart;
        this.placeEnd = placeEnd;
        this.placeAddInfo = placeAddInfo;
        this.addressId = addressId;
    }
}