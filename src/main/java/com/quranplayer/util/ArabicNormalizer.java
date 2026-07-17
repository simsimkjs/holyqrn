package com.quranplayer.util;

public final class ArabicNormalizer {

    private ArabicNormalizer() {
    }

    public static String normalize(String text) {

        if (text == null)
            return "";

        return text

                .replaceAll("[\\u064B-\\u065F]", "")   // التشكيل

                .replace("آ", "ا")
                .replace("أ", "ا")
                .replace("إ", "ا")

                .replace("ى", "ي")

                .replace("ؤ", "و")

                .replace("ئ", "ي")

                .replace("ـ", "")

                .trim()

                .toLowerCase();
    }

}