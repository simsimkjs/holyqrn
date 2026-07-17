package com.quranplayer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ayah {

    @JsonProperty("index")
    private int index;

    @JsonProperty("ayah_number")
    private int ayahNumber;

    @JsonProperty("text")
    private String text;

    @JsonProperty("start_ms")
    private long startMs;

    @JsonProperty("export")
    private boolean export;

    public Ayah() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getAyahNumber() {
        return ayahNumber;
    }

    public void setAyahNumber(int ayahNumber) {
        this.ayahNumber = ayahNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getStartMs() {
        return startMs;
    }

    public void setStartMs(long startMs) {
        this.startMs = startMs;
    }

    public boolean isExport() {
        return export;
    }

    public void setExport(boolean export) {
        this.export = export;
    }

    @Override
    public String toString() {
        return ayahNumber + " - " + text;
    }

}