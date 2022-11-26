package com.golfzonaca.backoffice.repository.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressUpdateDto {

    String address;
    String postalCode;
}
