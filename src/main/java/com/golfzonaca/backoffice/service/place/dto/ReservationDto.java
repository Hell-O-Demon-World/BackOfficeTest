package com.golfzonaca.backoffice.service.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private String userName;
    private String userTel;
    private String userEmail;
    private Long roomId;
    private String roomType;
    private String startDateTime;
    private String endDateTime;
    private String resStatus;
    private String fixStatus;
}
