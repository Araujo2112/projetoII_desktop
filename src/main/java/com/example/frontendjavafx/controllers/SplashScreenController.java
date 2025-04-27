package com.example.frontendjavafx.controllers;

import com.example.frontendjavafx.utils.SceneManager; // <- este é o mais importante
import javafx.animation.PauseTransition;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class SplashScreenController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Splash iniciado...");  // DEBUG
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            System.out.println("A mudar para signup.fxml...");  // DEBUG
            SceneManager.switchScene("signup.fxml");
        });
        pause.play();
    }

}
