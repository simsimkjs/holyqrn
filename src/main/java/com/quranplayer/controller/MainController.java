package com.quranplayer.controller;

import com.quranplayer.config.AppContext;
import com.quranplayer.model.AppState;
import com.quranplayer.model.Ayah;
import com.quranplayer.model.LastRead;
import com.quranplayer.model.SearchResult;
import com.quranplayer.model.Surah;
import com.quranplayer.model.SurahInfo;
import com.quranplayer.service.AudioService;
import com.quranplayer.service.LastReadService;
import com.quranplayer.service.QuranIndexer;
import com.quranplayer.service.QuranService;
import com.quranplayer.service.SearchService;
import com.quranplayer.service.TimelineService;
import com.quranplayer.ui.AyahHighlighter;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ListView;
import com.quranplayer.ui.VerseCell;
import javafx.util.Duration;
import com.quranplayer.ui.SearchResultCell;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

public class MainController {

    private static final Logger LOG =
            Logger.getLogger(MainController.class.getName());

    @FXML
    private Button playButton;

    @FXML
    private Button stopButton;
    @FXML
    private Button pauseButton;

    @FXML
    private Slider progressSlider;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Label currentTimeLabel;

    @FXML
    private Label totalTimeLabel;    
    
    @FXML
    private ListView<Ayah> verseList;
     @FXML
     private ComboBox<SurahInfo> surahCombo;

    @FXML
    private Label statusLabel;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private VBox searchPanel;

    @FXML
    private Label searchTitle;

    @FXML
    private Label searchCountLabel;

    @FXML
    private ListView<SearchResult> searchResults;
    
    private PlayerController playerController;
    private List<SurahInfo> surahList;
    private final AudioService audioService =  new AudioService();
    private final AppState appState = new AppState();
    private final TimelineService timelineService = new TimelineService();
    private AyahHighlighter highlighter;
    private NavigationController navigationController;
    private String currentSearchKeyword = "";
    
    private final QuranService quranService = AppContext.getQuranService();

    private final QuranIndexer indexer =   AppContext.getIndexer();
    private final SearchService searchService =AppContext.getSearchService();
    
    private final LastReadService lastReadService = AppContext.getLastReadService();
    private long lastSavedPosition = -1000;
    
    @FXML
    private void onPlay() {
        playerController.play();
    }

    @FXML
    public void onPause() {

        playerController.pause();

        MediaPlayer player = audioService.getPlayer();

        if (player != null) {

            lastReadService.updatePosition(
                    (long) player.getCurrentTime().toMillis());

        }

    }
    

    @FXML
    private void onStop() {
        playerController.stop();
    } 
    
    
      @FXML
    private void initialize() {

        configureUI();

        highlighter = new AyahHighlighter(verseList);
        playerController = new PlayerController( audioService,timelineService, progressSlider, volumeSlider, currentTimeLabel, totalTimeLabel);
        navigationController = new NavigationController(quranService, audioService,timelineService);
        playerController.setOnAyahChanged( this::updateCurrentAyah);

        //verseList.setCellFactory(list -> new VerseCell());
        //verseList.setCellFactory(v ->  new VerseCell(() -> currentSearchKeyword));
        verseList.setCellFactory(v -> new VerseCell(() -> currentSearchKeyword));
        searchResults.setCellFactory(v -> new SearchResultCell(() -> currentSearchKeyword));
        //searchResults.setCellFactory(v -> new SearchResultCell());

        searchPanel.setVisible(true);
        searchPanel.setManaged(true);

        
        searchResults.setPrefHeight(400);
        searchResults.setMinHeight(400);
        searchResults.toFront();
        
        verseList.setPlaceholder(new Label("لا توجد آيات"));

        verseList.setOnMouseClicked(e -> {

            if (e.getClickCount() == 2) {

                Ayah ayah =
                        verseList.getSelectionModel().getSelectedItem();

                playerController.playFromAyah(ayah);

            }

        });

        surahCombo.getSelectionModel()
                .selectedIndexProperty()
                .addListener((obs, oldValue, newValue) -> {

                    if (newValue.intValue() >= 0) {

                        openSurah(
                                surahList.get(newValue.intValue())
                                         .getNumber());

                    }

                });

        progressSlider.setOnMouseReleased(e -> {

            audioService.seek(
                    Duration.seconds(progressSlider.getValue()));

        });

        volumeSlider.valueProperty().addListener((obs,o,n)->{

            audioService.setVolume(n.doubleValue());

            lastReadService.updateVolume(n.doubleValue());

        });

        loadApplication();
        LastRead state = lastReadService.getState();

        System.out.println("Surah    : " + state.getSurahNumber());
        System.out.println("Ayah     : " + state.getAyahNumber());
        System.out.println("Position : " + state.getPosition());
        System.out.println("Volume   : " + state.getVolume());

        restoreLastRead();
        
        setupSearch();

       searchResults.setOnMouseClicked(e -> {

        if (e.getClickCount() == 2) {

            SearchResult result =
                    searchResults.getSelectionModel().getSelectedItem();

            if (result != null) {

                Platform.runLater(() -> openSearchResult(result));

            }

        }

    });

    }
    
    private void openSearchResult(SearchResult result) {

        currentSearchKeyword = searchField.getText();

        openSurah(result.getSurahNumber());

        verseList.refresh();

        int index = findAyahIndex(result.getAyahNumber());

        if (index >= 0) {

            final int target = index;

            Platform.runLater(() -> {

                verseList.getSelectionModel().select(target);

                verseList.scrollTo(Math.max(0, target - 6));

                verseList.requestFocus();

            });
        }
    }
    
    
    private void setupSearch() {

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {

            if (newValue == null || newValue.isBlank()) {

                searchPanel.setVisible(false);
                searchPanel.setManaged(false);

                searchResults.getItems().clear();
                return;
            }

            List<SearchResult> result = searchService.search(newValue);

            searchResults.getItems().setAll(result);

            searchPanel.setVisible(true);
            searchPanel.setManaged(true);

            searchCountLabel.setText(result.size() + " نتيجة");
        });
    }
    public void loadApplication() {

        LOG.info("Application Started");
        try {

            status("Loading...");

            loadSurahList();
            
            indexer.buildIndex();

            System.out.println( "Indexed : " + indexer.getIndex().size() + " ayahs");
            status("Ready");

        }
        catch (IOException ex) {

            LOG.log(Level.SEVERE,
                    ex.getMessage(),
                    ex);

            status(ex.getMessage());

        }

    }

    private void configureUI() {

        
        if (playButton != null) {
            playButton.setDisable(true);
        }

        if (pauseButton != null) {
            pauseButton.setDisable(true);
        }

        if (stopButton != null) {
            stopButton.setDisable(true);
        }

    }

    private void status(String text) {

        statusLabel.setText(text);

    }
    

    
    private void loadSurahList() throws IOException {

        surahList = navigationController.loadSurahList();

        surahCombo.getItems().setAll(surahList);

        if (!surahList.isEmpty()) {

            surahCombo.getSelectionModel().selectFirst();

        }

    }
    private void openSurah(int number) {

    try {

        Surah surah = navigationController.openSurah(number);
        
        appState.setCurrentSurah(surah);
        
        timelineService.setAyahs( surah.getAyahs());
        
        verseList.getItems().setAll( surah.getAyahs());

        lastReadService.updateSurah(number);       
        
        playerController.bindPlayer();
        
        audioService.currentTimeProperty().addListener((obs,oldTime,newTime)->{

            lastReadService.updatePosition(
                    (long)newTime.toMillis());

        });

        playButton.setDisable(false);
        pauseButton.setDisable(false);
        stopButton.setDisable(false);

        status(appState.getCurrentSurah().getSurahName());

        System.out.println("Surah = " + appState.getCurrentSurah().getSurahName());
        System.out.println("Ayahs = " + appState.getCurrentSurah().getAyahs().size());

    }
    catch (IOException ex) {

        status(ex.getMessage());

    }

}

    private void updateCurrentAyah(Ayah ayah) {

        int index = verseList.getItems().indexOf(ayah);

        if (index < 0)
            return;

        verseList.getSelectionModel().select(index);
        lastReadService.updateAyah(ayah.getAyahNumber());

        lastReadService.updatePosition(
                (long) audioService.getPlayer()
                                   .getCurrentTime()
                                   .toMillis());
        verseList.scrollTo(Math.max(0, index - 6));
    }
    
    private void playFromAyah(Ayah ayah) {

        if (ayah == null)
            return;

        audioService.seek(ayah.getStartMs());

        updateCurrentAyah(ayah);

        audioService.play();

    }
   
    private int findAyahIndex(int ayahNumber) {

        for (int i = 0; i < verseList.getItems().size(); i++) {

            if (verseList.getItems()
                         .get(i)
                         .getAyahNumber() == ayahNumber) {

                return i;
            }
        }

        return -1;
    }
    
    private void restoreLastRead() {

        LastRead state = lastReadService.getState();

        if (state.getSurahNumber() <= 0)
            return;

        playerController.setOnPlayerReady(() -> {

            audioService.seek(state.getPosition());
            playerController.setOnPlayerReady(null);


        });

        int index = findAyahIndex(state.getAyahNumber());

        if (index >= 0) {

            Platform.runLater(() -> {

                verseList.getSelectionModel().select(index);

                verseList.scrollTo(Math.max(0, index - 6));

                MediaPlayer player = audioService.getPlayer();

                    if (player != null) {

                        player.currentTimeProperty().addListener((obs, oldTime, newTime) -> {

                            long millis = (long) newTime.toMillis();

                            if (millis - lastSavedPosition >= 1000) {

                                lastSavedPosition = millis;

                                lastReadService.updatePosition(millis);

                            }

                        });

                    }

            });

        }

        volumeSlider.setValue(state.getVolume());
        audioService.setVolume(state.getVolume());

    }
}
