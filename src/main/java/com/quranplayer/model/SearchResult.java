package com.quranplayer.model;


import com.quranplayer.util.ArabicNormalizer;

public class SearchResult {

    private final int surahNumber;
    private final int ayahNumber;
    private final String surahName;
    private final String text;
    private final String normalizedText;

    public SearchResult(
            int surahNumber,
            int ayahNumber,
            String surahName,
            String text) {

        this.surahNumber = surahNumber;
        this.ayahNumber = ayahNumber;
        this.surahName = surahName;
        this.text = text;
        this.normalizedText =
                ArabicNormalizer.normalize(text);
    }

    public int getSurahNumber() {
        return surahNumber;
    }

    public int getAyahNumber() {
        return ayahNumber;
    }

    public String getSurahName() {
        return surahName;
    }

    public String getText() {
        return text;
    }

    public String getNormalizedText() {
        return normalizedText;
    }
}