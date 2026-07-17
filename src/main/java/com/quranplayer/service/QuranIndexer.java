package com.quranplayer.service;

import com.quranplayer.model.Ayah;
import com.quranplayer.model.SearchResult;
import com.quranplayer.model.Surah;
import com.quranplayer.model.SurahInfo;
import com.quranplayer.util.ArabicNormalizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuranIndexer {

    private final QuranService quranService;

    private final List<SearchResult> index =
            new ArrayList<>();

    public QuranIndexer(QuranService quranService) {
        this.quranService = quranService;
    }

    /**
     * بناء الفهرس مرة واحدة عند بدء البرنامج.
     */
    public void buildIndex() throws IOException {

        index.clear();

        List<SurahInfo> surahs =
                quranService.getSurahList();

        for (SurahInfo info : surahs) {

            Surah surah =
                    quranService.loadSurah(info.getNumber());

            for (Ayah ayah : surah.getAyahs()) {

                // تجاهل الآية رقم صفر إن وجدت
                if (ayah.getAyahNumber() == 0) {
                    continue;
                }

            String text = ayah.getText();
           if (text.contains("قلوبهم")) {
                System.out.println("Original = " + text);
                System.out.println("Normalized = " + ArabicNormalizer.normalize(text));
                break;
            }
            index.add(
                new SearchResult(
                    surah.getSurahNumber(),
                    ayah.getAyahNumber(),
                    surah.getSurahName(),
                    text
                )
            );

            }

        }

        System.out.println(
                "Indexed verses = " + index.size());

    }

    /**
     * الفهرس الكامل.
     */
    public List<SearchResult> getIndex() {

        return Collections.unmodifiableList(index);

    }

}