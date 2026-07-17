package com.quranplayer.ui;

import com.quranplayer.util.ArabicNormalizer;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public final class ArabicTextHighlighter {

    private ArabicTextHighlighter() {
    }

    public static TextFlow build(String verse, String keyword) {

        TextFlow flow = new TextFlow();

        if (verse == null) {
            return flow;
        }

        if (keyword == null || keyword.isBlank()) {

            Text text = new Text(verse);
            text.getStyleClass().add("ayah-text");

            flow.getChildren().add(text);

            return flow;
        }

        String normalizedVerse =
                ArabicNormalizer.normalize(verse);

        String normalizedKeyword =
                ArabicNormalizer.normalize(keyword);

        int index =
                normalizedVerse.indexOf(normalizedKeyword);

        if (index < 0) {

            Text text = new Text(verse);
            text.getStyleClass().add("ayah-text");

            flow.getChildren().add(text);

            return flow;
        }

        int end = index + keyword.length();

        Text before =
                new Text(verse.substring(0, index));

        before.getStyleClass().add("ayah-text");

        Text hit =
                new Text(verse.substring(index, end));

        hit.getStyleClass().add("search-hit");

        Text after =
                new Text(verse.substring(end));

        after.getStyleClass().add("ayah-text");

        flow.getChildren().addAll(
                before,
                hit,
                after);

        
        return flow;
    }

    private static Text createNormal(String s) {

        Text t = new Text(s);

        t.setStyle("""
            -fx-font-family:'Amiri';
            -fx-font-size:24px;
            -fx-fill:#222222;
            """);

        return t;
    }
    
    private static Text createHighlight(String s) {

        Text t = new Text(s);

        t.setStyle("""
            -fx-font-family:'Amiri';
            -fx-font-size:24px;
            -fx-font-weight:bold;
            -fx-fill:#C62828;
            """);

        return t;
    }
}