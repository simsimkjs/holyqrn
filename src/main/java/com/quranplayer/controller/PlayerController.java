package com.quranplayer.controller;

import com.quranplayer.config.AppContext;
import com.quranplayer.model.Ayah;
import com.quranplayer.service.AudioService;
import com.quranplayer.service.LastReadService;
import com.quranplayer.service.TimelineService;
import com.quranplayer.util.TimeFormatter;
import java.util.function.Consumer;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class PlayerController {

    private final AudioService audioService;
    private final TimelineService timelineService;
    private final LastReadService lastReadService =  AppContext.getLastReadService();
    private long lastSavedSecond = -1;
    private Runnable onPlayerReady;
    private final Slider progressSlider;
    private final Slider volumeSlider;

    private final Label currentTimeLabel;
    private final Label totalTimeLabel;

    private Consumer<Ayah> ayahChanged;

    public PlayerController(
            AudioService audioService,
            TimelineService timelineService,
            Slider progressSlider,
            Slider volumeSlider,
            Label currentTimeLabel,
            Label totalTimeLabel) {

        this.audioService = audioService;
        this.timelineService = timelineService;
        this.progressSlider = progressSlider;
        this.volumeSlider = volumeSlider;
        this.currentTimeLabel = currentTimeLabel;
        this.totalTimeLabel = totalTimeLabel;
    }

    public void setOnAyahChanged(Consumer<Ayah> listener) {
        this.ayahChanged = listener;
    }

   public void bindPlayer() {

        MediaPlayer player = audioService.getPlayer();

        if (player == null)
            return;

        player.setOnReady(() -> {

            Duration total = player.getTotalDuration();

            progressSlider.setMin(0);
            progressSlider.setMax(total.toSeconds());

            totalTimeLabel.setText(TimeFormatter.format(total));

            if (onPlayerReady != null)
                onPlayerReady.run();

        });

        player.currentTimeProperty().addListener((obs, oldTime, newTime) -> {

            progressSlider.setValue(newTime.toSeconds());

            currentTimeLabel.setText(
                    TimeFormatter.format(newTime));

            Ayah ayah =
                    timelineService.getCurrentAyah(
                            (long)newTime.toMillis());

            if (ayahChanged != null && ayah != null)
                ayahChanged.accept(ayah);

        });

    }

    public void play() {
        audioService.play();
    }

    public void pause() {
        audioService.pause();
    }

    public void stop() {
        audioService.stop();
    }
    
    public void playFromAyah(Ayah ayah) {

        if (ayah == null)
            return;

        audioService.seek(
                Duration.millis(
                        ayah.getStartMs()));

        audioService.play();

    }
    public void setOnPlayerReady(Runnable action) {

        this.onPlayerReady = action;

    }

}