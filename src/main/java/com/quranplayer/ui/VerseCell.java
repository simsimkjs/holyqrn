package com.quranplayer.ui;

import com.quranplayer.model.Ayah;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import java.util.function.Supplier;
import javafx.css.PseudoClass;
import javafx.scene.text.TextFlow;

public class VerseCell extends ListCell<Ayah> {

    private final Label number = new Label();
    
    private final TextFlow textContainer = new TextFlow();    
    private final Supplier<String> searchSupplier;
    
    private final StackPane numberPane =  new StackPane(number);

    private final HBox root =    new HBox(15);
    

    public VerseCell(Supplier<String> searchSupplier) {

        this.searchSupplier = searchSupplier;

        setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        numberPane.getStyleClass().add("ayah-number");

        textContainer.getStyleClass().add("ayah-text");

        textContainer.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        HBox.setHgrow(textContainer, Priority.ALWAYS);

        root.getChildren().addAll(numberPane, textContainer);

    }

    @Override
    protected void updateItem(Ayah ayah, boolean empty) {

        super.updateItem(ayah, empty);

        if (empty || ayah == null) {
            setGraphic(null);
            return;
        }

        number.setText(String.valueOf(ayah.getAyahNumber()));
        
    textContainer.getChildren().setAll(
            ArabicTextHighlighter.build(
                    ayah.getText(),
                    searchSupplier.get())
                    .getChildren()
    );


    root.pseudoClassStateChanged(PseudoClass.getPseudoClass("current"), isSelected());
        setGraphic(root);
    }

}