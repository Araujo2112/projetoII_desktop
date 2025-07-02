package com.example.frontendjavafx.service;

import com.example.frontendjavafx.model.TipoRelatorio;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.*;
import java.util.List;

public class TipoRelatorioService {
    private static final String BASE_URL = "http://localhost:8080/tipo-relatorio";
    private final HttpClient client;
    private final Gson gson;

    public TipoRelatorioService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public List<TipoRelatorio> getAll() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<List<TipoRelatorio>>(){}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public TipoRelatorio getById(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoRelatorio.class);
    }

    public TipoRelatorio create(TipoRelatorio tipo) throws IOException, InterruptedException {
        String json = gson.toJson(tipo);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoRelatorio.class);
    }

    public TipoRelatorio update(Integer id, TipoRelatorio tipo) throws IOException, InterruptedException {
        String json = gson.toJson(tipo);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoRelatorio.class);
    }

    public void delete(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        client.send(request, HttpResponse.BodyHandlers.discarding());
    }
}
