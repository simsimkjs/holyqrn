package com.quranplayer.util;

import com.quranplayer.config.AppConfig;

import java.net.URL;
import java.util.Objects;

public final class Resources {

    private Resources() {
    }

    /**
     * الحصول على Resource من داخل src/main/resources
     */
    public static URL get(String resource) {

        URL url = Resources.class.getResource(resource);

        if (url == null) {
            throw new IllegalArgumentException(
                    "Resource not found : " + resource);
        }

        return url;

    }

    /**
     * Main.fxml
     */
    public static URL mainFXML() {

        return get(AppConfig.MAIN_FXML);

    }

    /**
     * light.css
     */
    public static String lightCSS() {

        return Objects.requireNonNull(
                get(AppConfig.LIGHT_THEME))
                .toExternalForm();

    }

    /**
     * dark.css
     */
    public static String darkCSS() {

        return Objects.requireNonNull(
                get(AppConfig.DARK_THEME))
                .toExternalForm();

    }

}