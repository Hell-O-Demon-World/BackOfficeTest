package com.golfzonaca.backoffice.web.controller.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
