package com.electrolux.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {
    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd");

    public static Date converter(String sDate) {
        try {
            Date date = formatter.parse(sDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
