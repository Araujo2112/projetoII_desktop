package com.example.frontendjavafx.controllers;

import com.example.frontendjavafx.model.CodPostal;
import com.example.frontendjavafx.model.TipoUsuario;
import com.example.frontendjavafx.model.Usuario;
import com.example.frontendjavafx.service.CodPostalService;
import com.example.frontendjavafx.service.TipoUsuarioService;
import com.example.frontendjavafx.service.UsuarioService;
import com.example.frontendjavafx.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML private TextField nameField;
    @FXML private TextField telField;
    @FXML private TextField nifField;
    @FXML private TextField ruaField;
    @FXML private TextField nPortaField;
    @FXML private TextField codPostalField;
    @FXML private TextField localidadeField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private final UsuarioService usuarioService = new UsuarioService();
    private final CodPostalService codPostalService = new CodPostalService();
    private final TipoUsuarioService tipoUsuarioService = new TipoUsuarioService();

    @FXML
    private void goToLogin() {
        SceneManager.switchScene("login.fxml");
    }

    @FXML
    private void criarConta() {
        try {
            if (nameField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField.getText().isEmpty() ||
                    telField.getText().isEmpty() || nifField.getText().isEmpty() || ruaField.getText().isEmpty() ||
                    nPortaField.getText().isEmpty() || codPostalField.getText().isEmpty() || localidadeField.getText().isEmpty()) {
                mostrarErro("Preenche todos os campos!");
                return;
            }

            // 1. Verificar/criar Código Postal
            String idCodPostal = codPostalField.getText();
            CodPostal codPostal = codPostalService.getCodPostalById(idCodPostal);
            if (codPostal == null) {
                codPostal = new CodPostal(idCodPostal, localidadeField.getText());
                codPostalService.createCodPostal(codPostal);
            }

            // 2. Buscar Tipo de Utilizador 'admin'
            TipoUsuario tipoAdmin = tipoUsuarioService.getTipoUsuarioById(1);
            if (tipoAdmin == null) {
                mostrarErro("Tipo de utilizador 'admin' não existe.");
                return;
            }

            // 3. Criar utilizador
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(nameField.getText());
            novoUsuario.setTel(telField.getText());
            novoUsuario.setNif(Integer.parseInt(nifField.getText()));
            novoUsuario.setRua(ruaField.getText());
            novoUsuario.setNPorta(Integer.parseInt(nPortaField.getText()));
            novoUsuario.setEmail(emailField.getText());
            novoUsuario.setPassword(passwordField.getText());
            novoUsuario.setCodPostal(codPostal);
            novoUsuario.setTipoUsuario(tipoAdmin);

            // 4. Enviar para backend
            usuarioService.createUsuario(novoUsuario);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Conta criada");
            alert.setHeaderText(null);
            alert.setContentText("Conta criada com sucesso!");
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
