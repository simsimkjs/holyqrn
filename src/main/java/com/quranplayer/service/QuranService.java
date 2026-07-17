package com.quranplayer.service;

import com.quranplayer.model.Surah;
import com.quranplayer.model.SurahInfo;
import com.quranplayer.repository.JsonQuranRepository;
import com.quranplayer.repository.QuranRepository;

import java.io.IOException;
import java.util.List;

public class QuranService {

    private final QuranRepository repository;

    public QuranService() {
        this(new JsonQuranRepository());
    }

    public QuranService(QuranRepository repository) {
        this.repository = repository;
    }

    /**
     * قائمة السور
     */
    public List<SurahInfo> getSurahList() throws IOException {
        return repository.loadSurahList();
    }

    /**
     * تحميل سورة كاملة
     */
    public Surah loadSurah(int number) throws IOException {
        return repository.loadSurah(number);
    }

}