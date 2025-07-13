package com.example.frontendjavafx.controllers.manutencao;

import com.example.frontendjavafx.model.Manutencao;
import com.example.frontendjavafx.service.ManutencaoService;
import com.example.frontendjavafx.utils.SceneManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.List;

public class CalendarioController {

    @FXML private TableView<Manutencao> tabelaManutencoes;
    @FXML private TableColumn<Manutencao, String> colunaEspaco;
    @FXML private TableColumn<Manutencao, String> colunaDescricao;
    @FXML private TableColumn<Manutencao, String> colunaDataInicio;
    @FXML private TableColumn<Manutencao, String> colunaDataFim;
    @FXML private TableColumn<Manutencao, String> colunaCusto;

    @FXML private Hyperlink btnPaginaInicial;
    @FXML private Hyperlink btnNovaManutencaoTopo;
    @FXML private Hyperlink btnCalendarioTopo;

    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;

    private final ManutencaoService manutencaoService = new ManutencaoService();

    public void initialize() {
        configurarColunas();
        carregarManutencoes();

        btnPaginaInicial.setOnAction(e -> SceneManager.switchScene("manutencao/dashboard_manutencao.fxml"));
        btnNovaManutencaoTopo.setOnAction(e -> SceneManager.switchScene("manutencao/marcar_manutencao.fxml"));
        btnCalendarioTopo.setOnAction(e -> SceneManager.switchScene("manutencao/calendario.fxml"));
    }

    private void configurarColunas() {
        colunaEspaco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspacoDesportivo().getLote()));
        colunaDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
        colunaDataInicio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDtIni().toString()));
        colunaDataFim.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDtFim().toString()));
        colunaCusto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCusto().toString()));
    }

    private void carregarManutencoes() {
        try {
            List<Manutencao> todas = manutencaoService.getAllManutencoes();
            ObservableList<Manutencao> listaManutencoes = FXCollections.observableArrayList(todas);
            tabelaManutencoes.setItems(listaManutencoes);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro", "Erro ao carregar as manutenções.");
        }
    }

    @FXML
    private void procurarManutencaoPorDatas() {
        LocalDate dataInicio = dataInicioPicker.getValue();
        LocalDate dataFim = dataFimPicker.getValue();

        if (dataInicio != null && dataFim != null) {
            try {
                List<Manutencao> manutencoesFiltradas = manutencaoService.buscarManutencaoEntreDatas(dataInicio, dataFim);
                ObservableList<Manutencao> listaFiltrada = FXCollections.observableArrayList(manutencoesFiltradas);
                tabelaManutencoes.setItems(listaFiltrada);
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlertaErro("Erro", "Erro ao procurar manutenções entre as datas informadas.");
            }
        } else {
            mostrarAlertaErro("Erro", "Por favor, selecione ambas as datas.");
        }
    }

    private void mostrarAlertaErro(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void logout() {
        SceneManager.switchScene("login.fxml");
    }
}
