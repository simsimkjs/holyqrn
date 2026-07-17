package com.quranplayer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SurahInfo {

    @JsonProperty("number")
    private int number;

    @JsonProperty("name")
    private String name;

    @JsonProperty("file")
    private String jsonFile;

    @JsonProperty("audio")
    private String audioFile;

    @JsonProperty("ayahs")
    private int ayahCount;

    public SurahInfo() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    public int getAyahCount() {
        return ayahCount;
    }

    public void setAyahCount(int ayahCount) {
        this.ayahCount = ayahCount;
    }

    @Override
    public String toString() {
        return number + " - " + name;
    }
}