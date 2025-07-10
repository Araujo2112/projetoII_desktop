package com.example.frontendjavafx.controllers.gestor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import com.example.frontendjavafx.model.EspacoDesportivo;
import com.example.frontendjavafx.service.EspacoDesportivoService;
import com.example.frontendjavafx.utils.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.time.LocalTime;

public class EspacosController implements Initializable {

    @FXML
    private TableView<EspacoDesportivo> tabelaEspacos;

    @FXML
    private TableColumn<EspacoDesportivo, String> colunaLote;

    @FXML
    private TableColumn<EspacoDesportivo, Integer> colunaCapacidade;

    @FXML
    private TableColumn<EspacoDesportivo, String> colunaTipoEspaco;

    @FXML
    private TableColumn<EspacoDesportivo, BigDecimal> colunaPrecoHora;

    @FXML
    private TableColumn<EspacoDesportivo, LocalTime> colunaHoraAbertura;

    @FXML
    private TableColumn<EspacoDesportivo, LocalTime> colunaHoraFecho;

    @FXML
    private TableColumn<EspacoDesportivo, Void> colunaAcoes;

    private final EspacoDesportivoService espacoDesportivoService = new EspacoDesportivoService();
    private final ObservableList<EspacoDesportivo> espacosList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        carregarEspacos();
        adicionarColunaAcoes();
    }

    private void configurarColunas() {
        colunaLote.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLote()));
        colunaCapacidade.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCapacidade()).asObject());
        colunaTipoEspaco.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getTipoEspaco() != null ? cellData.getValue().getTipoEspaco().getTipo() : ""
        ));
        colunaPrecoHora.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrecoHora()));
        colunaHoraAbertura.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getHoraAbertura()));
        colunaHoraFecho.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getHoraFecho()));
    }

    private void carregarEspacos() {
        try {
            List<EspacoDesportivo> espacos = espacoDesportivoService.getAllEspacos();
            espacosList.setAll(espacos);
            tabelaEspacos.setItems(espacosList);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void adicionarColunaAcoes() {
        colunaAcoes.setCellFactory(coluna -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");

            {
                btnEditar.getStyleClass().addAll("botao-editar");
                btnEliminar.getStyleClass().addAll("botao-eliminar");

                btnEditar.setOnAction(event -> {
                    EspacoDesportivo espaco = getTableView().getItems().get(getIndex());

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/frontendjavafx/gestor/EditarEspaco.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());

                        EditarEspacoController controller = fxmlLoader.getController();
                        controller.setEspaco(espaco);

                        Stage stage = new Stage();
                        stage.setTitle("Editar Espaço");
                        stage.setScene(scene);
                        stage.showAndWait();

                        carregarEspacos();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


                btnEliminar.setOnAction(event -> {
                    EspacoDesportivo espaco = getTableView().getItems().get(getIndex());
                    eliminarEspaco(espaco);
                });
            }

            @Override
            protected void updateItem(Void item, boolean vazio) {
                super.updateItem(item, vazio);
                if (vazio) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, btnEditar, btnEliminar);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void eliminarEspaco(EspacoDesportivo espaco) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Eliminação");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tens a certeza que queres eliminar o espaço \"" + espaco.getLote() + "\"?");

        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                try {
                    espacoDesportivoService.deleteEspaco(espaco.getIdEspaco());
                    carregarEspacos();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    mostrarErro("Erro ao eliminar espaço: " + e.getMessage());
                }
            }
        });
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void criarEspaco() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/frontendjavafx/gestor/AdicionarEspaco.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Adicionar Espaço Desportivo");
            stage.setScene(scene);
            stage.showAndWait();

            carregarEspacos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirDashboard() { SceneManager.switchScene("gestor/dashboard_gestor.fxml"); }

    @FXML
    private void abrirRelatorios() { SceneManager.switchScene("gestor/relatorios.fxml"); }

    @FXML
    private void abrirEspacos() { SceneManager.switchScene("gestor/espacos.fxml"); }

    @FXML
    private void abrirPagamentos() { SceneManager.switchScene("gestor/pagamentos.fxml"); }

    @FXML
    private void logout() { SceneManager.switchScene("login.fxml"); }
}
