package com.example.frontendjavafx.controllers.gestor;

import com.example.frontendjavafx.model.Relatorio;
import com.example.frontendjavafx.service.RelatorioService;
import com.example.frontendjavafx.utils.SceneManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.awt.Desktop;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

public class RelatoriosController {

    @FXML private TableView<Relatorio> tabelaRelatorios;
    @FXML private TableColumn<Relatorio, Integer> colId;
    @FXML private TableColumn<Relatorio, String> colDataCriacao;
    @FXML private TableColumn<Relatorio, String> colTipo;
    @FXML private TableColumn<Relatorio, Void> colAcoes;

    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private Hyperlink btnPaginaInicial;
    @FXML private Hyperlink btnEspacosTopo;
    @FXML private Hyperlink btnPagamentosTopo;
    @FXML private Hyperlink btnRelatoriosTopo;

    private final ObservableList<Relatorio> listaRelatorios = FXCollections.observableArrayList();
    private final RelatorioService relatorioService = new RelatorioService();

    @FXML
    public void initialize() {
        btnPaginaInicial.setOnAction(e -> SceneManager.switchScene("gestor/dashboard_gestor.fxml"));
        btnEspacosTopo.setOnAction(e -> SceneManager.switchScene("gestor/espacos.fxml"));
        btnPagamentosTopo.setOnAction(e -> SceneManager.switchScene("gestor/pagamentos.fxml"));
        btnRelatoriosTopo.setOnAction(e -> SceneManager.switchScene("gestor/relatorios.fxml"));

        configurarTabela();
        carregarRelatorios();
    }

    private void configurarTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDataCriacao.setCellValueFactory(new PropertyValueFactory<>("dataCriacaoFormatada"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoRelatorio"));

        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnVer = new Button("Ver");
            private final Button btnApagar = new Button("Apagar");
            private final HBox hbox = new HBox(10, btnVer, btnApagar);

            {
                btnVer.setOnAction(event -> abrirPDF(getTableView().getItems().get(getIndex()).getId()));
                btnApagar.setOnAction(event -> eliminarRelatorio(getTableView().getItems().get(getIndex()).getId()));
                hbox.setStyle("-fx-alignment: center");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btnVer.getStyleClass().setAll("btn-ver");
                    btnApagar.getStyleClass().setAll("btn-apagar");
                    setGraphic(hbox);
                }
            }
        });

        tabelaRelatorios.setItems(listaRelatorios);
    }

    private void carregarRelatorios() {
        new Thread(() -> {
            try {
                List<Relatorio> relatorios = relatorioService.getAll();
                Platform.runLater(() -> {
                    listaRelatorios.clear();
                    listaRelatorios.addAll(relatorios);
                });
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os relatórios.");
            }
        }).start();
    }

    @FXML
    private void gerarRelatorio() {
        LocalDate inicio = dataInicioPicker.getValue();
        LocalDate fim = dataFimPicker.getValue();

        if (inicio == null || fim == null || inicio.isAfter(fim)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Datas inválidas", "Seleciona datas válidas para gerar o relatório.");
            return;
        }

        new Thread(() -> {
            try {
                relatorioService.gerarFaturacao(inicio, fim);
                Platform.runLater(() -> {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Relatório gerado com sucesso!");
                    carregarRelatorios();
                });
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao gerar relatório.");
            }
        }).start();
    }

    private void eliminarRelatorio(Integer id) {
        new Thread(() -> {
            try {
                relatorioService.delete(id);
                Platform.runLater(() -> {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Relatório eliminado com sucesso.");
                    carregarRelatorios();
                });
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao eliminar relatório.");
            }
        }).start();
    }

    private void abrirPDF(Integer id) {
        try {
            String url = "http://localhost:8080/api/relatorios/download/" + id;
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível abrir o PDF.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(tipo);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(msg);
            alert.showAndWait();
        });
    }

    @FXML
    private void logout() {
        SceneManager.switchScene("login.fxml");
    }
}
