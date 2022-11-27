package com.golfzonaca.backoffice.web.controller.reservation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDto {
    private String id;
    private String userName;
    private String userTel;
    private String userEmail;
    private String startDateTime;
    private String endDateTime;
}
