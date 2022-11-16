package com.golfzonaca.backoffice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place {

    private long id; //대여공간식별번호
    private long companyId; //업체식별번호
    private String placeName;
    private String placeDescription;
    private String placeOpenDays;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss", timezone = "Asia/Seoul")
    private String placeStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss", timezone = "Asia/Seoul")
    private String placeEnd;
    private String placeAddInfo;
    private long addressId; //우편번호

    @Autowired
    public Place(long companyId, String placeName, String placeDescription, String placeOpenDays, String placeStart, String placeEnd, String placeAddInfo, long addressId) {
        this.companyId = companyId;
        this.placeName = placeName;
        this.placeDescription = placeDescription;
        this.placeOpenDays = placeOpenDays;
        this.placeStart = placeStart;
        this.placeEnd = placeEnd;
        this.placeAddInfo = placeAddInfo;
        this.addressId = addressId;
    }
}