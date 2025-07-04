package com.example.frontendjavafx.controllers.gestor;

import com.example.frontendjavafx.model.Relatorio;
import com.example.frontendjavafx.utils.SceneManager;
import com.example.frontendjavafx.utils.LocalDateAdapter;
import com.example.frontendjavafx.utils.LocalTimeAdapter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.awt.Desktop;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @FXML
    public void initialize() {
        btnPaginaInicial.setOnAction(e -> SceneManager.switchScene("gestor/dashboard_gestor.fxml"));
        btnEspacosTopo.setOnAction(e -> SceneManager.switchScene("gestor/espacos.fxml"));
        btnPagamentosTopo.setOnAction(e -> SceneManager.switchScene("gestor/pagamentos.fxml"));
        btnRelatoriosTopo.setOnAction(e -> SceneManager.switchScene("gestor/relatorios.fxml"));

        configurarTabela();
        carregarRelatorios();
    }

    private void abrirPDF(Integer id) {
        try {
            String url = "http://localhost:8080/relatorios/download/" + id;
            java.awt.Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível abrir o PDF.");
        }
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
                btnVer.getStyleClass().add("btn-ver");
                btnApagar.getStyleClass().add("btn-apagar");

                btnVer.setOnAction(event -> {
                    Relatorio rel = getTableView().getItems().get(getIndex());
                    abrirPDF(rel.getId());
                });

                btnApagar.setOnAction(event -> {
                    Relatorio rel = getTableView().getItems().get(getIndex());
                    eliminarRelatorio(rel.getId());
                });

                hbox.setStyle("-fx-alignment: center");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hbox);
            }
        });

        tabelaRelatorios.setItems(listaRelatorios);
    }

    private void carregarRelatorios() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/relatorios"))
                    .GET()
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(json -> {
                        try {
                            Gson gson = new GsonBuilder()
                                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                                    .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                                    .create();

                            Type listType = new TypeToken<List<Relatorio>>() {}.getType();
                            List<Relatorio> relatorios = gson.fromJson(json, listType);

                            Platform.runLater(() -> {
                                listaRelatorios.clear();
                                listaRelatorios.addAll(relatorios);
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gerarRelatorio() {
        LocalDate inicio = dataInicioPicker.getValue();
        LocalDate fim = dataFimPicker.getValue();

        if (inicio == null || fim == null || inicio.isAfter(fim)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Datas inválidas", "Seleciona datas válidas para gerar o relatório.");
            return;
        }

        String url = String.format("http://localhost:8080/relatorios/faturacao?de=%s&ate=%s", inicio, fim);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if (response.statusCode() == 200) {
                            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Relatório gerado com sucesso!");
                            carregarRelatorios();
                        } else {
                            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao gerar relatório: " + response.body());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Ocorreu um erro inesperado.");
        }
    }

    private void eliminarRelatorio(Integer id) {
        String url = "http://localhost:8080/relatorios/" + id;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE()
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if (response.statusCode() == 204) {
                            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Relatório eliminado com sucesso.");
                            carregarRelatorios();
                        } else {
                            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao eliminar relatório.");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao comunicar com o servidor.");
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

    @FXML
    private void eliminarRelatorio() {
        System.out.println("Botão 'Eliminar' clicado fora da tabela");
    }
}
