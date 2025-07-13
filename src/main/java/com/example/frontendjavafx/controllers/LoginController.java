package com.example.frontendjavafx.controllers;

import com.example.frontendjavafx.utils.DashboardRouter;
import com.example.frontendjavafx.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import com.example.frontendjavafx.service.AuthService;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    private final AuthService authService = new AuthService();

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Campos Inválidos", "Por favor, preencha todos os campos.");
            return;
        }

        try {
            String tipoUtilizador = authService.login(email, password);

            if (tipoUtilizador != null) {
                DashboardRouter.openDashboard(tipoUtilizador);
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro de Login", "Usuário ou senha inválidos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro na autenticação.");
        }
    }

    @FXML
    private void goToSignUp() {
        SceneManager.switchScene("signup.fxml");
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String msg) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
