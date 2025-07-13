package com.example.frontendjavafx.controllers.rececionista;

import com.example.frontendjavafx.model.EspacoDesportivo;
import com.example.frontendjavafx.model.Pagamento;
import com.example.frontendjavafx.model.Reserva;
import com.example.frontendjavafx.model.TipoEstado;
import com.example.frontendjavafx.service.ReservaService;
import com.example.frontendjavafx.service.PagamentoService;
import com.example.frontendjavafx.service.EspacoDesportivoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EditarReservaController {

    @FXML
    private DatePicker txtData;  // Modificado para DatePicker

    @FXML
    private TextField txtHoraInicio;

    @FXML
    private TextField txtHoraFim;

    @FXML
    private ComboBox<EspacoDesportivo> comboEspacoDesportivo;

    private final ReservaService reservaService = new ReservaService();
    private final PagamentoService pagamentoService = new PagamentoService();
    private final EspacoDesportivoService espacoDesportivoService = new EspacoDesportivoService();

    private Reserva reservaAtual;

    public void setReserva(Reserva reserva) {
        this.reservaAtual = reserva;

        System.out.println("Data carregada: " + reserva.getDt());

        txtData.setValue(reserva.getDt());
        txtHoraInicio.setText(reserva.gethIni().toString());
        txtHoraFim.setText(reserva.gethFim().toString());

        try {
            List<EspacoDesportivo> espacos = espacoDesportivoService.getAllEspacos();

            comboEspacoDesportivo.getItems().clear();
            comboEspacoDesportivo.getItems().addAll(espacos);

            EspacoDesportivo espacoAtual = reserva.getEspacoDesportivo();
            comboEspacoDesportivo.setValue(espacoAtual);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void guardarAlteracoes() {
        try {
            reservaAtual.setDt(txtData.getValue());
            reservaAtual.sethIni(LocalTime.parse(txtHoraInicio.getText()));
            reservaAtual.sethFim(LocalTime.parse(txtHoraFim.getText()));

            EspacoDesportivo espacoSelecionado = comboEspacoDesportivo.getValue();
            reservaAtual.setEspacoDesportivo(espacoSelecionado);

            reservaService.updateReserva(reservaAtual.getIdReserva(), reservaAtual);

            Pagamento pagamento = pagamentoService.getPagamentoPorReserva(reservaAtual.getIdReserva());
            if (pagamento != null) {
                pagamento.setEstado(new TipoEstado(3, "Em Processo"));
                pagamentoService.updatePagamento(pagamento.getIdPagamento(), pagamento);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Reserva e pagamento atualizados com sucesso!");
            alert.showAndWait();

            Stage stage = (Stage) txtData.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarErro("Erro ao atualizar reserva: " + e.getMessage());
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
