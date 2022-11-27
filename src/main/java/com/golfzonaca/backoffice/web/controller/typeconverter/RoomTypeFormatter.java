package com.golfzonaca.backoffice.web.controller.typeconverter;

public class RoomTypeFormatter {
    public static String valueToDescription(String value) {
        if (value.equals("DESK")) {
            value = value.replace("DESK", "데스크");
        } else if (value.contains("MEETINGROOM")) {
            value = value.replace("MEETINGROOM", "") + "인 회의실";
        } else if (value.contains("OFFICE")) {
            value = value.replace("OFFICE", "") + "평 오피스";
        }
        return value;
    }
}
