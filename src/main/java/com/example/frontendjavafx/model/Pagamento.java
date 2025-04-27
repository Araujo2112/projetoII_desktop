package com.example.frontendjavafx.model;

import java.time.LocalDate;

public class Pagamento {
    private Integer idPagamento;
    private LocalDate dtPagamento;
    private TipoPagamento tipoPagamento;
    private Usuario usuario;
    private TipoEstado estado;
    private Reserva reserva;

    public Pagamento() {}

    public Pagamento(Integer idPagamento, LocalDate dtPagamento, TipoPagamento tipoPagamento, Usuario usuario, TipoEstado estado, Reserva reserva) {
        this.idPagamento = idPagamento;
        this.dtPagamento = dtPagamento;
        this.tipoPagamento = tipoPagamento;
        this.usuario = usuario;
        this.estado = estado;
        this.reserva = reserva;
    }

    public Integer getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Integer idPagamento) {
        this.idPagamento = idPagamento;
    }

    public LocalDate getDtPagamento() {
        return dtPagamento;
    }

    public void setDtPagamento(LocalDate dtPagamento) {
        this.dtPagamento = dtPagamento;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoEstado getEstado() {
        return estado;
    }

    public void setEstado(TipoEstado estado) {
        this.estado = estado;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}
