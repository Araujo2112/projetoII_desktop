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

public class EditarEspacoController {

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

    private EspacoDesportivo espacoAtual;

    public void setEspaco(EspacoDesportivo espaco) {
        this.espacoAtual = espaco;

        txtLote.setText(espaco.getLote());
        txtCapacidade.setText(String.valueOf(espaco.getCapacidade()));
        txtPrecoHora.setText(espaco.getPrecoHora().toString());
        txtHoraAbertura.setText(espaco.getHoraAbertura().toString());
        txtHoraFecho.setText(espaco.getHoraFecho().toString());

        if (espaco.getTipoEspaco() != null) {
            comboTipoEspaco.setValue(espaco.getTipoEspaco());
        }
    }

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
    private void guardarAlteracoes() {
        try {
            espacoAtual.setLote(txtLote.getText());
            espacoAtual.setCapacidade(Integer.parseInt(txtCapacidade.getText()));
            espacoAtual.setPrecoHora(new BigDecimal(txtPrecoHora.getText()));
            espacoAtual.setHoraAbertura(LocalTime.parse(txtHoraAbertura.getText()));
            espacoAtual.setHoraFecho(LocalTime.parse(txtHoraFecho.getText()));
            espacoAtual.setTipoEspaco(comboTipoEspaco.getValue());

            espacoDesportivoService.updateEspaco(espacoAtual.getIdEspaco(), espacoAtual);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Espaço atualizado com sucesso!");
            alert.showAndWait();

            Stage stage = (Stage) txtLote.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarErro("Erro ao atualizar espaço: " + e.getMessage());
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
