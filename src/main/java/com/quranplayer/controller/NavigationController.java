package com.quranplayer.controller;

import com.quranplayer.model.Surah;
import com.quranplayer.model.SurahInfo;
import com.quranplayer.service.AudioService;
import com.quranplayer.service.QuranService;
import com.quranplayer.service.TimelineService;
import java.io.IOException;
import java.util.List;

public class NavigationController {

    private final QuranService quranService;
    private final AudioService audioService;
    private final TimelineService timelineService;

    public NavigationController(
            QuranService quranService,
            AudioService audioService,
            TimelineService timelineService) {

        this.quranService = quranService;
        this.audioService = audioService;
        this.timelineService = timelineService;

    }

    public List<SurahInfo> loadSurahList() throws IOException {

        return quranService.getSurahList();

    }

    public Surah openSurah(int number) throws IOException {

        Surah surah =
                quranService.loadSurah(number);

        timelineService.setAyahs(
                surah.getAyahs());

        audioService.load(
                surah.getAudioFileName());

        return surah;

    }

}