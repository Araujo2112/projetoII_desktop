package com.example.frontendjavafx.controllers.rececionista;

import com.example.frontendjavafx.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

public class DashboardRececionistaController {

    @FXML private Button btnVerReservas;
    @FXML private Button btnGerirRelatorios;

    @FXML private Hyperlink btnPaginaInicial;
    @FXML private Hyperlink btnVerReservasTopo;
    @FXML private Hyperlink btnGerirRelatoriosTopo;

    @FXML
    public void initialize() {
        btnVerReservas.setOnAction(e -> SceneManager.switchScene("rececionista/gerir_reservas.fxml"));
        btnGerirRelatorios.setOnAction(e -> SceneManager.switchScene("rececionista/relatorios_desportivos.fxml"));

        btnPaginaInicial.setOnAction(e -> SceneManager.switchScene("rececionista/dashboard_rececionista.fxml"));
        btnVerReservasTopo.setOnAction(e -> SceneManager.switchScene("rececionista/gerir_reservas.fxml"));
        btnGerirRelatoriosTopo.setOnAction(e -> SceneManager.switchScene("rececionista/relatorios_desportivos.fxml"));
    }

    @FXML
    private void logout() {
        SceneManager.switchScene("login.fxml");
    }
}
