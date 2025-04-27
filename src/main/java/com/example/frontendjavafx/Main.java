package com.example.frontendjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Scene splashScene = new Scene(FXMLLoader.load(getClass().getResource("/com/example/frontendjavafx/splash.fxml")));
        stage.setMaximized(true);
        stage.setScene(splashScene);
        stage.setTitle("SQUAD BOSS");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
