package com.golfzonaca.backoffice.service.profit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfitDto {
    private Long id;
    private String placeName;
    private Long reservationQuantity;
    private Long profit;
}
