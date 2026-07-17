package com.quranplayer.util;

import javafx.util.Duration;

public class TimeFormatter {

    public static String format(Duration d) {

        if (d == null || d.isUnknown()) {
            return "00:00";
        }

        int seconds = (int) d.toSeconds();

        int min = seconds / 60;
        int sec = seconds % 60;

        return String.format("%02d:%02d", min, sec);
    }
}