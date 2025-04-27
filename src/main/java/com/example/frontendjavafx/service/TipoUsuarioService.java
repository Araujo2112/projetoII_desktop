package com.example.frontendjavafx.service;

import com.example.frontendjavafx.model.TipoUsuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TipoUsuarioService {

    private static final String BASE_URL = "http://localhost:8080/tipos-usuarios";
    private final HttpClient client;
    private final Gson gson;

    public TipoUsuarioService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public List<TipoUsuario> getAllTipoUsuarios() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Type listType = new TypeToken<List<TipoUsuario>>(){}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public TipoUsuario getTipoUsuarioById(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoUsuario.class);
    }

    public TipoUsuario createTipoUsuario(TipoUsuario tipoUsuario) throws IOException, InterruptedException {
        String json = gson.toJson(tipoUsuario);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoUsuario.class);
    }

    public TipoUsuario updateTipoUsuario(Integer id, TipoUsuario tipoUsuario) throws IOException, InterruptedException {
        String json = gson.toJson(tipoUsuario);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoUsuario.class);
    }

    public void deleteTipoUsuario(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        client.send(request, HttpResponse.BodyHandlers.discarding());
    }
}
