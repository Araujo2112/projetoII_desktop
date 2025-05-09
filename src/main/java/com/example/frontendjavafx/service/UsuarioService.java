package com.example.frontendjavafx.service;

import com.example.frontendjavafx.dto.UsuarioRequestDTO;
import com.example.frontendjavafx.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class UsuarioService {

    private static final String BASE_URL = "http://localhost:8080/users";
    private final HttpClient client;
    private final Gson gson;

    public UsuarioService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public List<Usuario> getAllUsuarios() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Type listType = new TypeToken<List<Usuario>>(){}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public Usuario getUsuarioById(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Usuario.class);
    }

    public Usuario createUsuario(Usuario usuario) throws IOException, InterruptedException {
        UsuarioRequestDTO dto = new UsuarioRequestDTO(usuario); // <-- transforma para DTO
        String json = gson.toJson(dto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201) {
            throw new IOException("Erro ao criar utilizador: " + response.statusCode());
        }

        return gson.fromJson(response.body(), Usuario.class);
    }


    public Usuario updateUsuario(Integer id, Usuario usuario) throws IOException, InterruptedException {
        String json = gson.toJson(usuario);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Usuario.class);
    }

    public void deleteUsuario(Integer id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        client.send(request, HttpResponse.BodyHandlers.discarding());
    }
}
