package com.golfzonaca.backoffice.domain;

import com.golfzonaca.backoffice.domain.type.PayType;
import com.golfzonaca.backoffice.domain.type.RoomType;
import lombok.Getter;

@Getter
public class RoomKind {

    private Long id;
    private RoomType roomType;
    private PayType price;
    
}
