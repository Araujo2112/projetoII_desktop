package com.example.frontendjavafx.controllers.manutencao;

import com.example.frontendjavafx.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

public class DashboardManutencaoController {

    @FXML private Button btnNovaManutencao;
    @FXML private Button btnCalendario;

    @FXML private Hyperlink btnPaginaInicial;
    @FXML private Hyperlink btnNovaManutencaoTopo;
    @FXML private Hyperlink btnCalendarioTopo;

    @FXML
    public void initialize() {
        System.out.println("Dashboard da Manutenção carregado!");

        btnNovaManutencao.setOnAction(e -> SceneManager.switchScene("manutencao/marcar_manutencao.fxml"));
        btnCalendario.setOnAction(e -> SceneManager.switchScene("manutencao/calendario.fxml"));

        btnPaginaInicial.setOnAction(e -> SceneManager.switchScene("manutencao/dashboard_manutencao.fxml"));
        btnNovaManutencaoTopo.setOnAction(e -> SceneManager.switchScene("manutencao/marcar_manutencao.fxml"));
        btnCalendarioTopo.setOnAction(e -> SceneManager.switchScene("manutencao/calendario.fxml"));
    }

    @FXML
    private void logout() {
        System.out.println("Logout clicado!");
        SceneManager.switchScene("login.fxml");
    }
}
