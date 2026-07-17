package com.quranplayer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Surah {

    @JsonProperty("surah_number")
    private int surahNumber;

    @JsonProperty("surah_name")
    private String surahName;

    @JsonProperty("audio_file_name")
    private String audioFileName;

    @JsonProperty("ayahs")
    private List<Ayah> ayahs = new ArrayList<>();

    public Surah() {
    }

    public int getSurahNumber() {
        return surahNumber;
    }

    public void setSurahNumber(int surahNumber) {
        this.surahNumber = surahNumber;
    }

    public String getSurahName() {
        return surahName;
    }

    public void setSurahName(String surahName) {
        this.surahName = surahName;
    }

    public String getAudioFileName() {
        return audioFileName;
    }

    public void setAudioFileName(String audioFileName) {
        this.audioFileName = audioFileName;
    }

    public List<Ayah> getAyahs() {
        return ayahs;
    }

    public void setAyahs(List<Ayah> ayahs) {
        this.ayahs = ayahs;
    }

    @Override
    public String toString() {
        return surahNumber + " - " + surahName;
    }
}