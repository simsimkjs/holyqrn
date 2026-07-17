package com.quranplayer.repository;

import com.quranplayer.model.Surah;
import com.quranplayer.model.SurahInfo;

import java.io.IOException;
import java.util.List;

/**
 * مصدر بيانات القرآن.
 *
 * يمكن أن يكون JSON أو Database أو API.
 */
public interface QuranRepository {

    /**
     * تحميل قائمة السور.
     */
    List<SurahInfo> loadSurahList() throws IOException;

    /**
     * تحميل سورة كاملة.
     */
    Surah loadSurah(int surahNumber) throws IOException;

}