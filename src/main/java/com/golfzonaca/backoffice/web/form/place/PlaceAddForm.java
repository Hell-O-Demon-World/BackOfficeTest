package com.golfzonaca.backoffice.web.form.place;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class PlaceAddForm {

    private Long id;
    private Long companyId;
    private String placeName;
    private String placeDescription;
    private List<String> placeOpenDays;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss", timezone = "Asia/Seoul")
    private String placeStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss", timezone = "Asia/Seoul")
    private String placeEnd;
    private List<String> placeAddInfo;
    private Long addressId;

    public PlaceAddForm() {
    }
}