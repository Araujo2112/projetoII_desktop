package com.example.frontendjavafx.controllers.manutencao;

import com.example.frontendjavafx.model.*;
import com.example.frontendjavafx.service.EspacoDesportivoService;
import com.example.frontendjavafx.service.ManutencaoService;
import com.example.frontendjavafx.utils.LocalDateAdapter;
import com.example.frontendjavafx.utils.LocalTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AdicionarManutencaoController {

    @FXML private ComboBox<EspacoDesportivo> comboEspaco;
    @FXML private TextField txtDescricao;
    @FXML private DatePicker dateInicio;
    @FXML private DatePicker dateFim;
    @FXML private TextField txtCusto;

    private final ManutencaoService manutencaoService = new ManutencaoService();
    private final EspacoDesportivoService espacoDesportivoService = new EspacoDesportivoService();

    @FXML
    public void initialize() {
        try {
            List<EspacoDesportivo> espacos = espacoDesportivoService.getAllEspacos();
            comboEspaco.getItems().setAll(espacos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void guardarManutencao() {
        try {
            EspacoDesportivo espaco = comboEspaco.getValue();
            String descricao = txtDescricao.getText();
            LocalDate dtIni = dateInicio.getValue();
            LocalDate dtFim = dateFim.getValue();
            double custo = Double.parseDouble(txtCusto.getText());

            if (espaco == null || descricao.isBlank() || dtIni == null || txtCusto.getText().isBlank()) {
                mostrarErro("Por favor preencha todos os campos obrigatórios.");
                return;
            }

            Manutencao nova = new Manutencao();
            nova.setEspacoDesportivo(espaco);
            nova.setDescricao(descricao);
            nova.setDtIni(dtIni);
            nova.setDtFim(dtFim);
            nova.setCusto(custo);
            TipoEstado estado = new TipoEstado();
            estado.setIdEstado(3);
            nova.setEstado(estado);

            Usuario user = new Usuario();
            user.setIdUsuario(2);
            nova.setUsuario(user);


            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                    .setPrettyPrinting()
                    .create();

            System.out.println("JSON enviado:");
            System.out.println(gson.toJson(nova));

            try {
                manutencaoService.createManutencao(nova);

                mostrarInfo("Manutenção marcada com sucesso!");
                Stage stage = (Stage) txtDescricao.getScene().getWindow();
                stage.close();

            } catch (Exception ex) {
                mostrarErro("Erro ao guardar manutenção: " + ex.getMessage());
                ex.printStackTrace();
            }

        } catch (NumberFormatException e) {
            mostrarErro("Custo inválido! Use números como 120.00.");
        } catch (Exception e) {
            mostrarErro("Erro ao guardar manutenção: " + e.getMessage());
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

    private void mostrarInfo(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

}
