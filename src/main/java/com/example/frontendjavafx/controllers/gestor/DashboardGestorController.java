package com.example.frontendjavafx.controllers.gestor;

import com.example.frontendjavafx.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

public class DashboardGestorController {

    @FXML private Button btnEspacos;
    @FXML private Button btnPagamentos;
    @FXML private Button btnRelatorios;

    @FXML private Hyperlink btnPaginaInicial;
    @FXML private Hyperlink btnRelatoriosTopo;
    @FXML private Hyperlink btnEspacosTopo;
    @FXML private Hyperlink btnPagamentosTopo;

    @FXML
    public void initialize() {
        System.out.println("Dashboard do Gestor carregado!");

        // Botões dos cards
        btnEspacos.setOnAction(e -> SceneManager.switchScene("gestor/espacos.fxml"));
        btnPagamentos.setOnAction(e -> SceneManager.switchScene("gestor/pagamentos.fxml"));
        btnRelatorios.setOnAction(e -> SceneManager.switchScene("gestor/relatorios.fxml"));

        // Botões da barra de topo
        btnPaginaInicial.setOnAction(e -> SceneManager.switchScene("gestor/dashboard_gestor.fxml"));
        btnRelatoriosTopo.setOnAction(e -> SceneManager.switchScene("gestor/relatorios.fxml"));
        btnEspacosTopo.setOnAction(e -> SceneManager.switchScene("gestor/espacos.fxml"));
        btnPagamentosTopo.setOnAction(e -> SceneManager.switchScene("gestor/pagamentos.fxml"));
    }

    @FXML
    private void logout() {
        System.out.println("Logout clicado!");
        SceneManager.switchScene("login.fxml");
    }
}
