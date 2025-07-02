package com.example.frontendjavafx.service;

import com.example.frontendjavafx.model.TipoUsuario;
import com.example.frontendjavafx.utils.LocalDateAdapter;
import com.example.frontendjavafx.utils.LocalTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TipoUsuarioService {

    private static final String BASE_URL = "http://localhost:8080/tipos-usuarios"; // ATENÇÃO: "tipos-usuarios"
    private final HttpClient client;
    private final Gson gson;

    public TipoUsuarioService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
    }

    public List<TipoUsuario> getAllTipoUsuarios() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Erro ao obter todos os tipos de utilizador: " + response.statusCode());
        }

        Type listType = new TypeToken<List<TipoUsuario>>(){}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public TipoUsuario getTipoUsuarioById(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Tipo de utilizador não encontrado: " + response.statusCode());
        }

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

        if (response.statusCode() != 201) {
            throw new IOException("Erro ao criar tipo de utilizador: " + response.statusCode());
        }

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

        if (response.statusCode() != 200) {
            throw new IOException("Erro ao atualizar tipo de utilizador: " + response.statusCode());
        }

        return gson.fromJson(response.body(), TipoUsuario.class);
    }

    public void deleteTipoUsuario(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

        if (response.statusCode() != 204) {
            throw new IOException("Erro ao eliminar tipo de utilizador: " + response.statusCode());
        }
    }

}
