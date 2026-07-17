package com.quranplayer.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppConfig {

    private AppConfig() {
    }

    // External data
    public static final Path DATA_DIR =
            Paths.get("data");

    public static final Path JSON_DIR =
            DATA_DIR.resolve("json");

    public static final Path AUDIO_DIR =
            DATA_DIR.resolve("audio");
    
    public static final Path STATE_FILE =
        DATA_DIR.resolve("state.json");

    // Resources
    public static final String MAIN_FXML = "/fxml/Main.fxml";

    public static final String LIGHT_THEME = "/css/light.css";

    public static final String DARK_THEME = "/css/style.css";

}