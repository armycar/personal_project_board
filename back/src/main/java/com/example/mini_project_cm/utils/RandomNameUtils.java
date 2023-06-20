package com.example.mini_project_cm.utils;

import java.time.LocalDateTime;

public class RandomNameUtils {
    public static String MakeRandomUrl(String type, Long seq) {
        LocalDateTime time = LocalDateTime.now();
        String year = Integer.toString(time.getYear() - 2023);
        String day = Integer.toString(time.getDayOfYear());
        Integer typeNo = Integer.parseInt(type, 32);
        String h = Integer.toString(time.getHour());
        String m = Integer.toString(time.getMinute());
        String s = Integer.toString(time.getSecond());
        String ns = Integer.toString(time.getNano());

        String randomUrl = year+day+Long.toString(seq)+typeNo+h+m+s+ns;
        return randomUrl;
    }
}
