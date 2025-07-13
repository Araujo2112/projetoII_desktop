package com.example.frontendjavafx.controllers.rececionista;

import com.example.frontendjavafx.dto.ReservaPagamentoDTO;
import com.example.frontendjavafx.model.Manutencao;
import com.example.frontendjavafx.model.Pagamento;
import com.example.frontendjavafx.model.Reserva;
import com.example.frontendjavafx.model.TipoEstado;
import com.example.frontendjavafx.service.PagamentoService;
import com.example.frontendjavafx.service.ReservaService;
import com.example.frontendjavafx.service.TipoEstadoService;
import com.example.frontendjavafx.utils.SceneManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GerirReservaController {

    @FXML private Hyperlink btnPaginaInicial;
    @FXML private Hyperlink btnGerirReservasTopo;
    @FXML private Hyperlink btnGerirRelatoriosTopo;

    @FXML private TableView<ReservaPagamentoDTO> tabelaReservas;
    @FXML private TableColumn<ReservaPagamentoDTO, String> colunaEspaco;
    @FXML private TableColumn<ReservaPagamentoDTO, String> colunaData;
    @FXML private TableColumn<ReservaPagamentoDTO, String> colunaHoraInicio;
    @FXML private TableColumn<ReservaPagamentoDTO, String> colunaHoraFim;
    @FXML private TableColumn<ReservaPagamentoDTO, String> colunaUsuario;
    @FXML private TableColumn<ReservaPagamentoDTO, String> colunaEstadoPagamento;
    @FXML private TableColumn<ReservaPagamentoDTO, String> colunaAcoes;

    private final ReservaService reservaService = new ReservaService();
    private final PagamentoService pagamentoService = new PagamentoService();
    private final TipoEstadoService tipoEstadoService = new TipoEstadoService();

    public void initialize() {
        configurarColunas();
        carregarReservas();

        btnPaginaInicial.setOnAction(e -> SceneManager.switchScene("rececionista/dashboard_rececionista.fxml"));
        btnGerirReservasTopo.setOnAction(e -> SceneManager.switchScene("rececionista/gerir_reservas.fxml"));
        btnGerirRelatoriosTopo.setOnAction(e -> SceneManager.switchScene("rececionista/relatorios_desportivos.fxml"));
    }

    private void configurarColunas() {
        colunaEspaco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReserva().getEspacoDesportivo().getLote()));
        colunaData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReserva().getDt().toString()));
        colunaHoraInicio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReserva().gethIni().toString()));
        colunaHoraFim.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReserva().gethFim().toString()));
        colunaUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReserva().getUsuario().getNome()));
        colunaEstadoPagamento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPagamento().getEstado().getEstado()));

        colunaAcoes.setCellFactory(tc -> new TableCell<ReservaPagamentoDTO, String>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnConfirmar = new Button("Confirmar");
            private final Button btnCancelar = new Button("Cancelar");

            {
                btnEditar.getStyleClass().add("botao-editar");
                btnConfirmar.getStyleClass().add("botao-confirmar");
                btnCancelar.getStyleClass().add("botao-eliminar");

                btnEditar.setOnAction(event -> {
                    ReservaPagamentoDTO reservaPagamentoDTO = getTableView().getItems().get(getIndex());
                    alterarReserva(reservaPagamentoDTO.getReserva());
                });

                btnConfirmar.setOnAction(event -> {
                    Reserva reserva = getTableView().getItems().get(getIndex()).getReserva();
                    atualizarEstadoReserva(reserva, 1, "Reserva confirmada com sucesso.");
                });

                btnCancelar.setOnAction(event -> {
                    Reserva reserva = getTableView().getItems().get(getIndex()).getReserva();
                    atualizarEstadoReserva(reserva, 2, "Reserva cancelada com sucesso.");
                });

            }

            @Override
            protected void updateItem(String item, boolean vazio) {
                super.updateItem(item, vazio);
                if (vazio) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, btnEditar, btnConfirmar, btnCancelar);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void carregarReservas() {
        try {
            List<Reserva> todasReservas = reservaService.getAllReservas();

            List<Reserva> reservasEmEspera = todasReservas.stream()
                    .filter(r -> r.getEstado() != null && r.getEstado().getIdEstado() == 3)
                    .toList();

            ObservableList<ReservaPagamentoDTO> listaReservaPagamento = FXCollections.observableArrayList();
            for (Reserva reserva : reservasEmEspera) {
                Pagamento pagamento = obterPagamentoPorReserva(reserva);
                ReservaPagamentoDTO reservaPagamentoDTO = new ReservaPagamentoDTO(reserva, pagamento);
                listaReservaPagamento.add(reservaPagamentoDTO);
            }
            tabelaReservas.setItems(listaReservaPagamento);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro", "Erro ao carregar as reservas.");
        }
    }

    private Pagamento obterPagamentoPorReserva(Reserva reserva) {
        try {
            return pagamentoService.getPagamentoPorReserva(reserva.getIdReserva());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new Pagamento();
        }
    }

    @FXML
    private void alterarReserva(Reserva reservaSelecionada) {
        if (reservaSelecionada != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/frontendjavafx/rececionista/editar_reserva.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                EditarReservaController controller = fxmlLoader.getController();

                controller.setReserva(reservaSelecionada);

                Stage stage = new Stage();
                stage.setTitle("Editar Reserva");
                stage.setScene(scene);
                stage.showAndWait();

                carregarReservas();

            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlertaErro("Erro", "Erro ao abrir a tela de edição da reserva.");
            }
        } else {
            mostrarAlertaErro("Erro", "Selecione uma reserva para alterar.");
        }
    }

    private void atualizarEstadoReserva(Reserva reserva, int idEstado, String mensagemSucesso) {
        try {
            TipoEstado novoEstado = tipoEstadoService.getTipoEstadoById(idEstado);
            reserva.setEstado(novoEstado);

            Pagamento pagamento = obterPagamentoPorReserva(reserva);
            if (pagamento != null) {
                if (idEstado == 1) {
                    pagamento.setEstado(tipoEstadoService.getTipoEstadoById(1));
                } else if (idEstado == 2) {
                    pagamento.setEstado(tipoEstadoService.getTipoEstadoById(2));
                }
                pagamentoService.updatePagamento(pagamento.getIdPagamento(), pagamento);
            }

            reservaService.updateReserva(reserva.getIdReserva(), reserva);

            carregarReservas();

            mostrarAlerta("Sucesso", mensagemSucesso);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro", "Erro ao atualizar reserva.");
        }
    }


    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
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
