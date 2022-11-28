package com.golfzonaca.backoffice.web.controller.typeconverter;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class TimeFormatter {
    public static LocalDate toLocalDate(String day) {
        if (StringUtils.hasText(day)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d");
            return LocalDate.parse(day, formatter);
        }
        return null;
    }

    public static LocalTime toLocalTime(String hour) {
        if (StringUtils.hasText(hour)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");
            return LocalTime.parse(hour, formatter);
        }
        return null;
    }

    public static LocalDateTime toLocalDateTime(String dateTime) {
        if (StringUtils.hasText(dateTime)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d H:m:s");
            return LocalDateTime.parse(dateTime, formatter);
        }
        return null;
    }

    public static String toDayOfTheWeek(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US);
    }
}
