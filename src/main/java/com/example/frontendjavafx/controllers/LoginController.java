package com.example.frontendjavafx.controllers;

import com.example.frontendjavafx.utils.DashboardRouter;
import com.example.frontendjavafx.utils.SceneManager;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private void goToSignUp() {
        SceneManager.switchScene("signup.fxml");
    }

    @FXML
    private void login() {
        // Simulação de login como gestor
        DashboardRouter.openDashboard("gestor");

        // Para testar outros, basta trocar a string:
        // DashboardRouter.openDashboard("rececionista");
        // DashboardRouter.openDashboard("manutencao");
    }
}
