package com.example.frontendjavafx.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

public class AuthService {

    private static final String LOGIN_URL = "http://localhost:8080/api/login";
    private final HttpClient client;

    public AuthService() {
        this.client = HttpClient.newHttpClient();
    }

    public String login(String email, String senha) throws IOException, InterruptedException {
        String url = LOGIN_URL + "?email=" + email + "&senha=" + senha;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Erro ao autenticar: " + response.statusCode());
        }
    }
}
