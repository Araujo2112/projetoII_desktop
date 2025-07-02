package com.example.frontendjavafx.controllers.gestor;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.LocalDate;

public class GerarRelatorioController {

    @FXML
    private DatePicker dataInicioPicker;

    @FXML
    private DatePicker dataFimPicker;

    @FXML
    private void gerarRelatorio() {
        LocalDate dataInicio = dataInicioPicker.getValue();
        LocalDate dataFim = dataFimPicker.getValue();

        if (dataInicio == null || dataFim == null) {
            mostrarErro("Por favor selecione ambas as datas.");
            return;
        }

        if (dataInicio.isAfter(dataFim)) {
            mostrarErro("A data de início não pode ser depois da data de fim.");
            return;
        }

        try {
            String url = String.format("http://localhost:8080/relatorios?dataInicio=%s&dataFim=%s",
                    dataInicio, dataFim);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if (response.statusCode() == 200) {
                            mostrarInfo("Relatório gerado com sucesso!");
                        } else {
                            mostrarErro("Erro ao gerar relatório: " + response.body());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            mostrarErro("Erro ao enviar pedido para o servidor.");
        }
    }

    private void mostrarErro(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void mostrarInfo(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
