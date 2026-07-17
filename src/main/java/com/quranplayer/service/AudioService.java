package com.quranplayer.service;

import com.quranplayer.config.AppConfig;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;

public class AudioService {

    private MediaPlayer player;

    public void load(String audioFile) {

        stop();

        try {

            Path path = AppConfig.AUDIO_DIR.resolve(audioFile);

            if (!Files.exists(path)) {
                throw new IllegalArgumentException(
                        "Audio file not found : " + path);
            }

            Media media = new Media(path.toUri().toString());

            player = new MediaPlayer(media);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }
    public void seek(long millis) {

        if (player == null) {
            return;
        }

        player.seek(Duration.millis(millis));

    }
    public void play() {

        if (player != null) {
            player.play();
        }

    }

    public void pause() {

        if (player != null) {
            player.pause();
        }

    }

    public void stop() {

        if (player != null) {

            player.stop();
            player.dispose();
            player = null;

        }

    }

    public void seek(Duration duration) {

        if (player != null) {
            player.seek(duration);
        }

    }

    public void setVolume(double value) {

        if (player != null) {
            player.setVolume(value);
        }

    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public ReadOnlyObjectProperty<Duration> currentTimeProperty() {

        if (player == null) {
            return null;
        }

        return player.currentTimeProperty();

    }

}