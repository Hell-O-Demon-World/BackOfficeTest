package com.golfzonaca.backoffice.web.controller.typeconverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataTypeFormatter {
    public static String listToString(List<String> list) {
        return list.toString().replace("[", "").replace("]", "");
    }

    public static List<String> stringToList(String string) {
        return new ArrayList<>(Arrays.asList(string.split(", ")));
    }
}
