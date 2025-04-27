package com.example.frontendjavafx.controllers.gestor;

import com.example.frontendjavafx.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Hyperlink;

public class RelatoriosController {

    @FXML private TableView<?> tabelaRelatorios;

    @FXML private Hyperlink btnPaginaInicial;
    @FXML private Hyperlink btnEspacosTopo;
    @FXML private Hyperlink btnPagamentosTopo;
    @FXML private Hyperlink btnRelatoriosTopo;

    @FXML
    public void initialize() {
        System.out.println("Relatórios carregados!");

        btnPaginaInicial.setOnAction(e -> SceneManager.switchScene("gestor/dashboard_gestor.fxml"));
        btnEspacosTopo.setOnAction(e -> SceneManager.switchScene("gestor/espacos.fxml"));
        btnPagamentosTopo.setOnAction(e -> SceneManager.switchScene("gestor/pagamentos.fxml"));
        btnRelatoriosTopo.setOnAction(e -> SceneManager.switchScene("gestor/relatorios.fxml"));
    }

    @FXML
    private void logout() {
        System.out.println("Logout clicado!");
        SceneManager.switchScene("login.fxml");
    }
}
