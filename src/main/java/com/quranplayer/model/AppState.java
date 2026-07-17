package com.quranplayer.model;

public class AppState {

    private Surah currentSurah;
    private Ayah currentAyah;

    public Surah getCurrentSurah() {
        return currentSurah;
    }

    public void setCurrentSurah(Surah currentSurah) {
        this.currentSurah = currentSurah;
    }

    public Ayah getCurrentAyah() {
        return currentAyah;
    }

    public void setCurrentAyah(Ayah currentAyah) {
        this.currentAyah = currentAyah;
    }

    public void clear() {
        currentSurah = null;
        currentAyah = null;
    }
}