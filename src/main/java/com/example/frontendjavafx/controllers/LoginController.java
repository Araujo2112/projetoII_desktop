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
        //DashboardRouter.openDashboard("gestor");
        // DashboardRouter.openDashboard("rececionista");
        DashboardRouter.openDashboard("manutencao");
    }
}
