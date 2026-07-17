package com.quranplayer;

import com.quranplayer.controller.MainController;
import com.quranplayer.util.Resources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(Resources.mainFXML());

        BorderPane root = loader.load();

        Scene scene = new Scene(root);

        scene.getStylesheets().add(Resources.darkCSS());


        // إذا كان initialize() يستدعي loadApplication()
        // فاحذف السطر التالي.
        // وإذا لم يكن كذلك فاتركه.

        stage.setTitle("Quran Player FX");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(700);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println(Resources.mainFXML());
        System.out.println(Resources.darkCSS());
        launch(args);
  
    }
}