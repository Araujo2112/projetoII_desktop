package com.example.frontendjavafx.service;

import com.example.frontendjavafx.model.Notificacao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class NotificacaoService {

    private static final String BASE_URL = "http://localhost:8080/notificacoes";
    private final HttpClient client;
    private final Gson gson;

    public NotificacaoService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public List<Notificacao> getAllNotificacoes() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Type listType = new TypeToken<List<Notificacao>>(){}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public Notificacao getNotificacaoById(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Notificacao.class);
    }

    public Notificacao createNotificacao(Notificacao notificacao) throws IOException, InterruptedException {
        String json = gson.toJson(notificacao);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Notificacao.class);
    }

    public Notificacao updateNotificacao(Integer id, Notificacao notificacao) throws IOException, InterruptedException {
        String json = gson.toJson(notificacao);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Notificacao.class);
    }

    public void deleteNotificacao(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        client.send(request, HttpResponse.BodyHandlers.discarding());
    }
}
