package com.example.frontendjavafx.utils;

import com.example.frontendjavafx.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;

import java.io.IOException;

public class SceneManager {
    public static void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/frontendjavafx/" + fxmlFile));
            Parent root = loader.load();

            // Obter tamanho do ecrã
            double width = Screen.getPrimary().getVisualBounds().getWidth();
            double height = Screen.getPrimary().getVisualBounds().getHeight();

            // Forçar scene com essas dimensões
            Scene newScene = new Scene(root, width, height);
            Main.stage.setScene(newScene);
            Main.stage.setMaximized(true); // opcional agora, mas mantém-se
        } catch (IOException e) {
            System.err.println("Erro ao carregar a cena: " + fxmlFile);
            e.printStackTrace();
        }
    }
}
