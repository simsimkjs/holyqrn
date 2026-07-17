package com.quranplayer.util;

public final class SearchSnippet {

    private SearchSnippet() {
    }

    public static String build(String text,
                               String keyword,
                               int around) {

        if (text == null || keyword == null || keyword.isBlank())
            return text;

        String lower =
                ArabicNormalizer.normalize(text);

        String key =
                ArabicNormalizer.normalize(keyword);

        int pos = lower.indexOf(key);

        if (pos < 0)
            return text;

        int start = Math.max(0, pos - around);

        int end = Math.min(text.length(),
                           pos + keyword.length() + around);

        String result =
                text.substring(start, end);

        if (start > 0)
            result = "... " + result;

        if (end < text.length())
            result += " ...";

        return result;

    }

}