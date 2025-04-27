package com.example.frontendjavafx.utils;

import com.example.frontendjavafx.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneManager {
    public static void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/frontendjavafx/" + fxmlFile));
            Scene scene = new Scene(loader.load());

            Main.stage.hide();                 // ← Oculta temporariamente
            Main.stage.setScene(scene);
            Main.stage.setMaximized(true);     // ← Força maximização após aplicar scene
            Main.stage.show();                 // ← Reapresenta a janela

        } catch (IOException e) {
            System.err.println("Erro ao carregar a cena: " + fxmlFile);
            e.printStackTrace();
        }
    }
}
