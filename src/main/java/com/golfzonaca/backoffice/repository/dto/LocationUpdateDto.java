package com.golfzonaca.backoffice.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationUpdateDto {

    String address;
    String postalCode;
}
