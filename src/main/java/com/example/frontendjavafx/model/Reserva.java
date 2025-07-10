package com.example.frontendjavafx.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private Integer idReserva;
    private LocalDate dt;
    private LocalTime hIni;
    private LocalTime hFim;
    private TipoEstado estado;
    private EspacoDesportivo espacoDesportivo;
    private Usuario usuario;
    private Integer idTipoPag;
    private Boolean lembrete12hEnviado;
    private Boolean lembrete24hEnviado;

    public Reserva() {}

    public Reserva(Integer idReserva, LocalDate dt, LocalTime hIni, LocalTime hFim, TipoEstado estado, EspacoDesportivo espacoDesportivo, Usuario usuario,
                   Integer idTipoPag, Boolean lembrete12hEnviado, Boolean lembrete24hEnviado) {
        this.idReserva = idReserva;
        this.dt = dt;
        this.hIni = hIni;
        this.hFim = hFim;
        this.estado = estado;
        this.espacoDesportivo = espacoDesportivo;
        this.usuario = usuario;
        this.idTipoPag = idTipoPag;
        this.lembrete12hEnviado = lembrete12hEnviado;
        this.lembrete24hEnviado = lembrete24hEnviado;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDate getDt() {
        return dt;
    }

    public void setDt(LocalDate dt) {
        this.dt = dt;
    }

    public LocalTime gethIni() {
        return hIni;
    }

    public void sethIni(LocalTime hIni) {
        this.hIni = hIni;
    }

    public LocalTime gethFim() {
        return hFim;
    }

    public void sethFim(LocalTime hFim) {
        this.hFim = hFim;
    }

    public TipoEstado getEstado() {
        return estado;
    }

    public void setEstado(TipoEstado estado) {
        this.estado = estado;
    }

    public EspacoDesportivo getEspacoDesportivo() {
        return espacoDesportivo;
    }

    public void setEspacoDesportivo(EspacoDesportivo espacoDesportivo) {
        this.espacoDesportivo = espacoDesportivo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getIdTipoPag() {
        return idTipoPag;
    }

    public void setIdTipoPag(Integer idTipoPag) {
        this.idTipoPag = idTipoPag;
    }

    public Boolean getLembrete12hEnviado() {
        return lembrete12hEnviado;
    }

    public void setLembrete12hEnviado(Boolean lembrete12hEnviado) {
        this.lembrete12hEnviado = lembrete12hEnviado;
    }

    public Boolean getLembrete24hEnviado() {
        return lembrete24hEnviado;
    }

    public void setLembrete24hEnviado(Boolean lembrete24hEnviado) {
        this.lembrete24hEnviado = lembrete24hEnviado;
    }
}
