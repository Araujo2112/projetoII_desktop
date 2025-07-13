package com.example.frontendjavafx.service;

import com.example.frontendjavafx.dto.RelatorioEspacoDTO;
import com.example.frontendjavafx.model.Relatorio;
import com.example.frontendjavafx.utils.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.*;
import java.time.LocalDate;
import java.util.List;

public class RelatorioService {
    private static final String BASE_URL = "http://localhost:8080/api/relatorios";
    private final HttpClient client;
    private final Gson gson;

    public RelatorioService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    public List<Relatorio> getAll() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao buscar relatórios: " + response.statusCode());
        }

        Type listType = new TypeToken<List<Relatorio>>(){}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public List<Relatorio> getRelatoriosByTipo(int tipoId) throws IOException, InterruptedException {
        String url = BASE_URL + "/tipo/" + tipoId;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao buscar relatórios filtrados: " + response.statusCode());
        }

        Type listType = new TypeToken<List<Relatorio>>(){}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public List<RelatorioEspacoDTO> getRelatorioUtilizacao(LocalDate de, LocalDate ate) throws IOException, InterruptedException {
        String url = BASE_URL + "/utilizacao?de=" + de + "&ate=" + ate;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao obter relatório de utilização: " + response.body());
        }

        Type listType = new TypeToken<List<RelatorioEspacoDTO>>(){}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public void gerarFaturacao(LocalDate de, LocalDate ate) throws IOException, InterruptedException {
        String url = BASE_URL + "/faturacao?de=" + de + "&ate=" + ate;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao gerar relatório: " + response.body());
        }
    }

    public void gerarRelatorioUtilizacao(LocalDate de, LocalDate ate) throws IOException, InterruptedException {
        String url = BASE_URL + "/utilizacao?de=" + de + "&ate=" + ate;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro ao gerar relatório de utilização: " + response.body());
        }

        Type listType = new TypeToken<List<RelatorioEspacoDTO>>(){}.getType();
        gson.fromJson(response.body(), listType);
    }

    public void delete(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

        if (response.statusCode() != 204) {
            throw new RuntimeException("Erro ao eliminar relatório.");
        }
    }
}
