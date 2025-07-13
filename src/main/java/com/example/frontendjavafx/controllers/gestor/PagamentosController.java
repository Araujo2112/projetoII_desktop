package com.example.frontendjavafx.controllers.gestor;

import com.example.frontendjavafx.model.*;
import com.example.frontendjavafx.service.NotificacaoService;
import com.example.frontendjavafx.service.PagamentoService;
import com.example.frontendjavafx.service.ReservaService;
import com.example.frontendjavafx.utils.SceneManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class PagamentosController implements Initializable {

    @FXML private TableView<Pagamento> tabelaPagamentos;
    @FXML private TableColumn<Pagamento, String> colunaCliente;
    @FXML private TableColumn<Pagamento, String> colunaEspaco;
    @FXML private TableColumn<Pagamento, String> colunaData;
    @FXML private TableColumn<Pagamento, Double> colunaValor;
    @FXML private TableColumn<Pagamento, String> colunaEstado;
    @FXML private TableColumn<Pagamento, Void> colunaAcoes;

    @FXML private Hyperlink btnPaginaInicial;
    @FXML private Hyperlink btnEspacosTopo;
    @FXML private Hyperlink btnPagamentosTopo;
    @FXML private Hyperlink btnRelatoriosTopo;

    private final PagamentoService pagamentoService = new PagamentoService();
    private final NotificacaoService notificacaoService = new NotificacaoService();
    private final ReservaService reservaService = new ReservaService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColunas();
        carregarPagamentos();
        adicionarColunaAcoes();

        btnPaginaInicial.setOnAction(e -> SceneManager.switchScene("gestor/dashboard_gestor.fxml"));
        btnEspacosTopo.setOnAction(e -> SceneManager.switchScene("gestor/espacos.fxml"));
        btnPagamentosTopo.setOnAction(e -> SceneManager.switchScene("gestor/pagamentos.fxml"));
        btnRelatoriosTopo.setOnAction(e -> SceneManager.switchScene("gestor/relatorios_desportivos.fxml"));
    }

    private void configurarColunas() {
        colunaCliente.setCellValueFactory(cellData -> {
            Usuario u = cellData.getValue().getUsuario();
            return new javafx.beans.property.SimpleStringProperty(u != null ? u.getNome() : "");
        });

        colunaEspaco.setCellValueFactory(cellData -> {
            Reserva reserva = cellData.getValue().getReserva();
            EspacoDesportivo espaco = reserva != null ? reserva.getEspacoDesportivo() : null;
            String lote = (espaco != null && espaco.getLote() != null) ? espaco.getLote() : "Sem espaço";
            return new SimpleStringProperty(lote);
        });

        colunaData.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getDtPagamento() != null
                                ? cellData.getValue().getDtPagamento().toString()
                                : "Sem data"
                )
        );

        colunaValor.setCellValueFactory(cellData -> {
            Reserva reserva = cellData.getValue().getReserva();
            EspacoDesportivo espaco = reserva != null ? reserva.getEspacoDesportivo() : null;

            if (reserva != null && espaco != null && reserva.gethIni() != null && reserva.gethFim() != null && espaco.getPrecoHora() != null) {
                double precoHora = espaco.getPrecoHora() != null ? espaco.getPrecoHora().doubleValue() : 0.0;
                long minutos = Duration.between(reserva.gethIni(), reserva.gethFim()).toMinutes();
                double horas = minutos / 60.0;
                double total = precoHora * horas;
                return new SimpleDoubleProperty(total).asObject();
            }

            return new SimpleDoubleProperty(0.0).asObject();
        });

        colunaEstado.setCellValueFactory(cellData -> {
            TipoEstado estado = cellData.getValue().getEstado();
            return new SimpleStringProperty(estado != null ? estado.getEstado() : "Sem estado");
        });

    }

    private void carregarPagamentos() {
        try {
            List<Pagamento> todosPagamentos = pagamentoService.getAllPagamentos();
            List<Pagamento> pendentes = todosPagamentos.stream()
                    .filter(p -> p.getEstado() != null && "Em Processo".equalsIgnoreCase(p.getEstado().getEstado()))
                    .toList();

            ObservableList<Pagamento> lista = FXCollections.observableArrayList(pendentes);
            tabelaPagamentos.setItems(lista);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro", "Erro ao carregar pagamentos.");
        }

    }

    private void adicionarColunaAcoes() {
        colunaAcoes.setCellFactory(tc -> new TableCell<>() {
            private final Button btnAprovar = new Button("Aprovar");
            private final Button btnRejeitar = new Button("Rejeitar");

            {
                btnAprovar.getStyleClass().add("botao-aprovar");
                btnRejeitar.getStyleClass().add("botao-rejeitar");

                btnAprovar.setOnAction(event -> {
                    Pagamento pagamento = getTableView().getItems().get(getIndex());
                    aprovarPagamento(pagamento);
                });

                btnRejeitar.setOnAction(event -> {
                    Pagamento pagamento = getTableView().getItems().get(getIndex());
                    rejeitarPagamento(pagamento);
                });
            }

            @Override
            protected void updateItem(Void item, boolean vazio) {
                super.updateItem(item, vazio);
                if (vazio) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, btnAprovar, btnRejeitar);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void aprovarPagamento(Pagamento pagamento) {
        try {
            Pagamento pagamentoCompleto = pagamentoService.getPagamentoById(pagamento.getIdPagamento());
            pagamentoCompleto.setEstado(new TipoEstado(1, "Feito"));
            pagamentoService.updatePagamento(pagamento.getIdPagamento(), pagamentoCompleto);

            Reserva reserva = reservaService.getReservaById(pagamento.getReserva().getIdReserva());
            reserva.setEstado(new TipoEstado(1, "Feito"));
            reservaService.updateReserva(reserva.getIdReserva(), reserva);

            criarNotificacao(pagamento, "O seu pagamento foi aprovado com sucesso!");
            tabelaPagamentos.getItems().remove(pagamento);
            mostrarAlerta("Pagamento aprovado", "O pagamento foi aprovado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro", "Erro ao aprovar pagamento.");
        }
    }

    private void rejeitarPagamento(Pagamento pagamento) {
        try {
            pagamento.setEstado(new TipoEstado(2, "Cancelado"));
            pagamentoService.updatePagamento(pagamento.getIdPagamento(), pagamento);

            Reserva reserva = pagamento.getReserva();
            if (reserva != null) {
                reserva.setEstado(new TipoEstado(2, "Cancelado"));
                reservaService.updateReserva(reserva.getIdReserva(), reserva);
            }

            criarNotificacao(pagamento, "O seu pagamento foi rejeitado. Contacte o suporte.");
            tabelaPagamentos.getItems().remove(pagamento);
            mostrarAlerta("Pagamento rejeitado", "O pagamento foi rejeitado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro", "Erro ao rejeitar pagamento.");
        }
    }

    private void criarNotificacao(Pagamento pagamento, String mensagem) {
        try {
            Notificacao notificacao = new Notificacao();
            notificacao.setUsuario(pagamento.getUsuario());
            notificacao.setMensagem(mensagem);
            notificacao.setDataNotificacao(LocalDate.now());
            notificacao.setHoraNotificacao(LocalTime.now());
            notificacaoService.createNotificacao(notificacao);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertaErro("Erro", "Erro ao criar notificação.");
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
}
