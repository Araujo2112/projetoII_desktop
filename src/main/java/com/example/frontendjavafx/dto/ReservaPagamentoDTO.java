package com.example.frontendjavafx.dto;

import com.example.frontendjavafx.model.Pagamento;
import com.example.frontendjavafx.model.Reserva;

public class ReservaPagamentoDTO {
    private Reserva reserva;
    private Pagamento pagamento;

    public ReservaPagamentoDTO(Reserva reserva, Pagamento pagamento) {
        this.reserva = reserva;
        this.pagamento = pagamento;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }


}
