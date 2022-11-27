package com.golfzonaca.backoffice.web.controller.typeconverter;

public class RoomStatusFormatter {
    public static String booleanToString(Boolean status) {
        if (status) {
            return status.toString().replace("true", "사용 가능");
        } else {
            return status.toString().replace("false", "사용 불가");
        }
    }
}
