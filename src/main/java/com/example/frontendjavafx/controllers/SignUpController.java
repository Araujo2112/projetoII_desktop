package com.example.frontendjavafx.controllers;

import com.example.frontendjavafx.model.TipoUsuario;
import com.example.frontendjavafx.model.Usuario;
import com.example.frontendjavafx.service.UsuarioService;
import com.example.frontendjavafx.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    private void goToLogin() {
        SceneManager.switchScene("login.fxml");
    }

    @FXML
    private void criarConta() {
        try {
            String nome = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (nome.isEmpty() || email.isEmpty() || password.isEmpty()) {
                mostrarErro("Todos os campos são obrigatórios!");
                return;
            }

            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(nome);
            novoUsuario.setEmail(email);
            novoUsuario.setPassword(password);

            // Definir sempre como Admin
            TipoUsuario tipoAdmin = new TipoUsuario();
            tipoAdmin.setIdTipoUsuario(1);
            tipoAdmin.setTipo("admin");
            novoUsuario.setTipoUsuario(tipoAdmin);

            usuarioService.createUsuario(novoUsuario);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Conta criada com sucesso! Agora já podes fazer login.");
            alert.showAndWait();

            SceneManager.switchScene("login.fxml");

        } catch (Exception e) {
            e.printStackTrace();
            mostrarErro("Erro ao criar conta: " + e.getMessage());
        }
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
