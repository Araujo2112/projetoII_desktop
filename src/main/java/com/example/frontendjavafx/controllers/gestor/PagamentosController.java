package com.example.frontendjavafx.controllers.gestor;

import com.example.frontendjavafx.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PagamentosController implements Initializable {

    @FXML
    private TableView<Object> tabelaPagamentos;

    @FXML
    private TableColumn<Object, Void> colunaAcoes;

    @FXML
    private Hyperlink btnPaginaInicial;
    @FXML
    private Hyperlink btnEspacosTopo;
    @FXML
    private Hyperlink btnPagamentosTopo;
    @FXML
    private Hyperlink btnRelatoriosTopo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adicionarColunaAcoes();

        // Topo
        btnPaginaInicial.setOnAction(e -> SceneManager.switchScene("gestor/dashboard_gestor.fxml"));
        btnEspacosTopo.setOnAction(e -> SceneManager.switchScene("gestor/espacos.fxml"));
        btnPagamentosTopo.setOnAction(e -> SceneManager.switchScene("gestor/pagamentos.fxml"));
        btnRelatoriosTopo.setOnAction(e -> SceneManager.switchScene("gestor/relatorios.fxml"));
    }

    @FXML
    private void logout() {
        SceneManager.switchScene("login.fxml");
    }

    private void adicionarColunaAcoes() {
        colunaAcoes.setCellFactory(tc -> new TableCell<Object, Void>() {
            private final Button btnAprovar = new Button("Aprovar");
            private final Button btnRejeitar = new Button("Rejeitar");

            {
                btnAprovar.getStyleClass().add("botao-aprovar");
                btnRejeitar.getStyleClass().add("botao-rejeitar");

                btnAprovar.setOnAction(event -> {
                    Object pagamento = getTableView().getItems().get(getIndex());
                    System.out.println("Aprovar: " + pagamento);
                });

                btnRejeitar.setOnAction(event -> {
                    Object pagamento = getTableView().getItems().get(getIndex());
                    System.out.println("Rejeitar: " + pagamento);
                });
            }

            @Override
            protected void updateItem(Void item, boolean vazio) {
                super.updateItem(item, vazio);
                if (vazio) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, btnAprovar, btnRejeitar);
                    setGraphic(hbox);
                }
            }
        });
    }
}
