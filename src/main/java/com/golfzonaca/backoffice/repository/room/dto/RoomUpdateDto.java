package com.golfzonaca.backoffice.repository.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomUpdateDto {
    private long roomKindId;
    private int totalNum;
    private Boolean roomState;
}
