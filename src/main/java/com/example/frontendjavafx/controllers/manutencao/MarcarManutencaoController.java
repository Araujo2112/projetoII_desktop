package com.example.frontendjavafx.controllers.manutencao;

import com.example.frontendjavafx.model.*;
import com.example.frontendjavafx.service.EspacoDesportivoService;
import com.example.frontendjavafx.service.ManutencaoService;
import com.example.frontendjavafx.service.TipoEstadoService;
import com.example.frontendjavafx.utils.SceneManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class MarcarManutencaoController implements Initializable {

    @FXML private TableView<Manutencao> tabelaManutencoes;
    @FXML private TableColumn<Manutencao, String> colunaEspaco;
    @FXML private TableColumn<Manutencao, String> colunaDescricao;
    @FXML private TableColumn<Manutencao, String> colunaDataInicio;
    @FXML private TableColumn<Manutencao, String> colunaDataFim;
    @FXML private TableColumn<Manutencao, String> colunaEstado;
    @FXML private TableColumn<Manutencao, Void> colunaAcoes;

    @FXML private Button btnNovaManutencao;

    @FXML private Hyperlink btnPaginaInicial;
    @FXML private Hyperlink btnNovaManutencaoTopo;
    @FXML private Hyperlink btnCalendarioTopo;

    private final ManutencaoService manutencaoService = new ManutencaoService();
    private final TipoEstadoService tipoEstadoService = new TipoEstadoService();
    private final EspacoDesportivoService espacoDesportivoService = new EspacoDesportivoService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        carregarManutencoes();
        adicionarColunaAcoes();

        btnNovaManutencao.setOnAction(e -> abrirPopupNovaManutencao());

        btnPaginaInicial.setOnAction(e -> SceneManager.switchScene("manutencao/dashboard_manutencao.fxml"));
        btnNovaManutencaoTopo.setOnAction(e -> SceneManager.switchScene("manutencao/marcar_manutencao.fxml"));
        btnCalendarioTopo.setOnAction(e -> SceneManager.switchScene("manutencao/calendario.fxml"));
    }

    private void configurarColunas() {
        colunaEspaco.setCellValueFactory(cellData -> {
            EspacoDesportivo espaco = cellData.getValue().getEspacoDesportivo();
            return new SimpleStringProperty(espaco != null ? espaco.getLote() : "Sem espaço");
        });

        colunaDescricao.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescricao())
        );

        colunaDataInicio.setCellValueFactory(cellData -> {
            LocalDate data = cellData.getValue().getDtIni();
            return new SimpleStringProperty(data != null ? data.toString() : "—");
        });

        colunaDataFim.setCellValueFactory(cellData -> {
            LocalDate data = cellData.getValue().getDtFim();
            return new SimpleStringProperty(data != null ? data.toString() : "—");
        });

        colunaEstado.setCellValueFactory(cellData -> {
            TipoEstado estado = cellData.getValue().getEstado();
            return new SimpleStringProperty(estado != null ? estado.getEstado() : "Sem estado");
        });
    }

    private void carregarManutencoes() {
        try {
            List<Manutencao> todas = manutencaoService.getAllManutencoes();
            List<Manutencao> emEspera = todas.stream()
                    .filter(m -> m.getEstado() != null && m.getEstado().getIdEstado() == 3)
                    .toList();
            ObservableList<Manutencao> lista = FXCollections.observableArrayList(emEspera);
            tabelaManutencoes.setItems(lista);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro", "Erro ao carregar manutenções.");
        }
    }

    private void adicionarColunaAcoes() {
        colunaAcoes.setCellFactory(tc -> new TableCell<>() {
            private final Button btnConcluir = new Button("Concluir");
            private final Button btnCancelar = new Button("Cancelar");

            {
                btnConcluir.getStyleClass().add("botao-aprovar");
                btnCancelar.getStyleClass().add("botao-rejeitar");

                btnConcluir.setOnAction(event -> {
                    Manutencao manutencao = getTableView().getItems().get(getIndex());
                    atualizarEstado(manutencao, 1, "Manutenção concluída com sucesso.");
                });

                btnCancelar.setOnAction(event -> {
                    Manutencao manutencao = getTableView().getItems().get(getIndex());
                    atualizarEstado(manutencao, 2, "Manutenção cancelada com sucesso.");
                });
            }

            @Override
            protected void updateItem(Void item, boolean vazio) {
                super.updateItem(item, vazio);
                if (vazio) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, btnConcluir, btnCancelar);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void abrirPopupNovaManutencao() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/frontendjavafx/manutencao/adicionar_manutencao.fxml"));
            Parent root = loader.load();

            Stage popup = new Stage();
            popup.setTitle("Nova Manutenção");
            popup.setScene(new Scene(root));
            popup.setResizable(false);
            popup.showAndWait();

            carregarManutencoes();
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarAlertaErro("Erro", "Não foi possível abrir o formulário de nova manutenção.");
        }
    }


    private void atualizarEstado(Manutencao manutencao, int idEstado, String mensagemSucesso) {
        try {
            TipoEstado novoEstado = tipoEstadoService.getTipoEstadoById(idEstado);
            manutencao.setEstado(novoEstado);
            manutencaoService.updateManutencao(manutencao.getIdManu(), manutencao);
            tabelaManutencoes.getItems().remove(manutencao);
            mostrarAlerta("Sucesso", mensagemSucesso);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro", "Erro ao atualizar manutenção.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarAlertaErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void logout() {
        SceneManager.switchScene("login.fxml");
    }

    @FXML
    private void marcarNovaManutencao() {
        SceneManager.switchScene("manutencao/nova_manutencao.fxml");
    }
}
