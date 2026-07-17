package com.quranplayer.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quranplayer.config.AppConfig;
import com.quranplayer.model.Surah;
import com.quranplayer.model.SurahInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class JsonQuranRepository implements QuranRepository {

    private final ObjectMapper mapper;

    public JsonQuranRepository() {

        mapper = new ObjectMapper();

        mapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);

    }

    @Override
    public List<SurahInfo> loadSurahList() throws IOException {

        List<SurahInfo> list = new ArrayList<>();

        for (int i = 1; i <= 114; i++) {

            Path file = buildPath(i);

            if (!Files.exists(file)) {
                continue;
            }

            Surah surah =
                    mapper.readValue(file.toFile(), Surah.class);

            SurahInfo info = new SurahInfo();

            info.setNumber(surah.getSurahNumber());
            info.setName(surah.getSurahName());
            info.setAyahCount(surah.getAyahs().size());

            info.setJsonFile(
                    String.format(Locale.US, "%03d.json", surah.getSurahNumber()));

            info.setAudioFile(surah.getAudioFileName());

            list.add(info);

        }

        return list;

    }

    @Override
    public Surah loadSurah(int surahNumber) throws IOException {

        Path file = buildPath(surahNumber);

        if (!Files.exists(file)) {

            throw new IOException(
                    "Missing file : " + file.toAbsolutePath());

        }

        return mapper.readValue(
                file.toFile(),
                Surah.class);

    }

    private Path buildPath(int number) {

        String fileName =
                String.format(
                        Locale.US,
                        "%03d.json",
                        number);

        return AppConfig.JSON_DIR.resolve(fileName);

    }

}