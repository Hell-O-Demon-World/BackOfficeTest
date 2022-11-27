package com.golfzonaca.backoffice.web.controller.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomDto {
    private String id;
    private String roomType;
    private String reservationStatus;
    private String usingStatus;
}
