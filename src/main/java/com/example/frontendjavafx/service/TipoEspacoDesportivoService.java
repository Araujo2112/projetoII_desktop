package com.example.frontendjavafx.service;

import com.example.frontendjavafx.model.TipoEspacoDesportivo;
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

public class TipoEspacoDesportivoService {

    private static final String BASE_URL = "http://localhost:8080/tipos-espacos";
    private final HttpClient client;
    private final Gson gson;

    public TipoEspacoDesportivoService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .create();
    }

    public List<TipoEspacoDesportivo> getAllTiposEspacos() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Type listType = new TypeToken<List<TipoEspacoDesportivo>>(){}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public TipoEspacoDesportivo getTipoEspacoById(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoEspacoDesportivo.class);
    }

    public TipoEspacoDesportivo createTipoEspaco(TipoEspacoDesportivo tipoEspacoDesportivo) throws IOException, InterruptedException {
        String json = gson.toJson(tipoEspacoDesportivo);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoEspacoDesportivo.class);
    }

    public TipoEspacoDesportivo updateTipoEspaco(Integer id, TipoEspacoDesportivo tipoEspacoDesportivo) throws IOException, InterruptedException {
        String json = gson.toJson(tipoEspacoDesportivo);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoEspacoDesportivo.class);
    }

    public void deleteTipoEspaco(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        client.send(request, HttpResponse.BodyHandlers.discarding());
    }
}
