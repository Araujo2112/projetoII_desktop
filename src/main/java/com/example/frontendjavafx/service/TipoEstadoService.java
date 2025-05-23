package com.example.frontendjavafx.service;

import com.example.frontendjavafx.model.TipoEstado;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TipoEstadoService {

    private static final String BASE_URL = "http://localhost:8080/tipos-estados";
    private final HttpClient client;
    private final Gson gson;

    public TipoEstadoService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public List<TipoEstado> getAllTiposEstado() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Type listType = new TypeToken<List<TipoEstado>>(){}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public TipoEstado getTipoEstadoById(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoEstado.class);
    }

    public TipoEstado createTipoEstado(TipoEstado tipoEstado) throws IOException, InterruptedException {
        String json = gson.toJson(tipoEstado);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoEstado.class);
    }

    public TipoEstado updateTipoEstado(Integer id, TipoEstado tipoEstado) throws IOException, InterruptedException {
        String json = gson.toJson(tipoEstado);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoEstado.class);
    }

    public void deleteTipoEstado(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        client.send(request, HttpResponse.BodyHandlers.discarding());
    }
}
