package com.quranplayer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.quranplayer.model.LastRead;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LastReadService {

    private static final Path FILE =
            Path.of("data", "state.json");

    private final ObjectMapper mapper =
            new ObjectMapper()
                    .enable(SerializationFeature.INDENT_OUTPUT);

    private LastRead state;

    public LastReadService() {

        load();

    }

    public LastRead getState() {
        return state;
    }

    public void updateSurah(int number) {

        state.setSurahNumber(number);

        save();

    }

    public void updateAyah(int number) {

        state.setAyahNumber(number);

        save();

    }

    public void updatePosition(long millis) {

        state.setPosition(millis);

        save();

    }

    public void updateVolume(double volume) {

        state.setVolume(volume);

        save();

    }

    private void load() {

        try {

            if (Files.exists(FILE)) {

                state = mapper.readValue(FILE.toFile(),
                        LastRead.class);

            } else {

                state = new LastRead();

            }

        } catch (IOException ex) {

            state = new LastRead();

        }

    }

    private void save() {

        try {

            Files.createDirectories(FILE.getParent());

            mapper.writeValue(FILE.toFile(), state);

        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }

}