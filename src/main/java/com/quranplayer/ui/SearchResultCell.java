package com.quranplayer.ui;

import com.quranplayer.model.SearchResult;
import com.quranplayer.util.SearchSnippet;
import java.util.function.Supplier;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class SearchResultCell extends ListCell<SearchResult> {

    private final Label surah = new Label();

    private final Label ayah = new Label();

    private final TextFlow preview = new TextFlow();

    private final VBox info = new VBox(8);

    private final HBox header = new HBox();
    
    

    private final BorderPane root = new BorderPane();
    
    private final Supplier<String> keywordSupplier;




    public SearchResultCell(Supplier<String> keywordSupplier) {

        this.keywordSupplier = keywordSupplier;

        surah.getStyleClass().add("search-surah");

        ayah.getStyleClass().add("search-ayah");

        preview.getStyleClass().add("search-preview");

        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(
                surah,
                spacer,
                ayah);

        info.getChildren().addAll(
                header,
                preview);

        root.setCenter(info);

        root.getStyleClass().add("search-card");

    }


    @Override
    protected void updateItem(SearchResult item, boolean empty) {

        super.updateItem(item, empty);

        if (empty || item == null) {

            setGraphic(null);
            return;

        }

        surah.setText(item.getSurahName());

        ayah.setText("الآية " + item.getAyahNumber());

        String keyword = keywordSupplier.get();

        String snippet =
                SearchSnippet.build(
                        item.getText(),
                        keyword,
                        25);

        preview.getChildren().setAll(
                ArabicTextHighlighter.build(
                        snippet,
                        keyword));

        setGraphic(root);

    }
}