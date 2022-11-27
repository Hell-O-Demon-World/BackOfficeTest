package com.golfzonaca.backoffice.web.controller.typeconverter;

public class PresentReservationFormatter {
    public static String booleanToString(Boolean status) {
        if (status) {
            return status.toString().replace("true", "예약 없음");
        } else {
            return status.toString().replace("false", "예약 있음");
        }
    }
}
