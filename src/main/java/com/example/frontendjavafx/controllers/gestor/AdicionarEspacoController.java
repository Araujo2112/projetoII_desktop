package com.example.frontendjavafx.controllers.gestor;

import com.example.frontendjavafx.model.EspacoDesportivo;
import com.example.frontendjavafx.model.TipoEspacoDesportivo;
import com.example.frontendjavafx.service.EspacoDesportivoService;
import com.example.frontendjavafx.service.TipoEspacoDesportivoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public class AdicionarEspacoController {

    @FXML
    private TextField txtLote;

    @FXML
    private TextField txtCapacidade;

    @FXML
    private TextField txtPrecoHora;

    @FXML
    private TextField txtHoraAbertura;

    @FXML
    private TextField txtHoraFecho;

    @FXML
    private ComboBox<TipoEspacoDesportivo> comboTipoEspaco;

    private final EspacoDesportivoService espacoDesportivoService = new EspacoDesportivoService();
    private final TipoEspacoDesportivoService tipoEspacoDesportivoService = new TipoEspacoDesportivoService();

    @FXML
    public void initialize() {
        try {
            List<TipoEspacoDesportivo> tipos = tipoEspacoDesportivoService.getAllTiposEspacos();
            comboTipoEspaco.getItems().setAll(tipos);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void guardarEspaco() {
        try {
            String lote = txtLote.getText();
            int capacidade = Integer.parseInt(txtCapacidade.getText());
            BigDecimal precoHora = new BigDecimal(txtPrecoHora.getText());
            LocalTime horaAbertura = LocalTime.parse(txtHoraAbertura.getText());
            LocalTime horaFecho = LocalTime.parse(txtHoraFecho.getText());

            TipoEspacoDesportivo tipoSelecionado = comboTipoEspaco.getValue();
            if (tipoSelecionado == null) {
                mostrarErro("Por favor selecione o Tipo de Espaço!");
                return;
            }

            EspacoDesportivo novoEspaco = new EspacoDesportivo();
            novoEspaco.setLote(lote);
            novoEspaco.setCapacidade(capacidade);
            novoEspaco.setPrecoHora(precoHora);
            novoEspaco.setHoraAbertura(horaAbertura);
            novoEspaco.setHoraFecho(horaFecho);
            novoEspaco.setDisponibilidade(true); // Sempre disponível por padrão
            novoEspaco.setTipoEspaco(tipoSelecionado);

            espacoDesportivoService.createEspaco(novoEspaco);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Espaço criado com sucesso!");
            alert.showAndWait();

            Stage stage = (Stage) txtLote.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            mostrarErro("Erro: Capacidade e Preço devem ser números válidos!");
        } catch (Exception e) {
            mostrarErro("Erro ao criar espaço: " + e.getMessage());
            e.printStackTrace();
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
