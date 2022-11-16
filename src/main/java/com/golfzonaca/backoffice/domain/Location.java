package com.golfzonaca.backoffice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private long id;
    private String Address;
    private String postalCode;

    public Location(String address, String postalCode) {
        Address = address;
        this.postalCode = postalCode;
    }
}

