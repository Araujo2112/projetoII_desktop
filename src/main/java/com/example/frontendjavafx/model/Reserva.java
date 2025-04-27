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

    public Reserva() {}

    public Reserva(Integer idReserva, LocalDate dt, LocalTime hIni, LocalTime hFim, TipoEstado estado, EspacoDesportivo espacoDesportivo, Usuario usuario) {
        this.idReserva = idReserva;
        this.dt = dt;
        this.hIni = hIni;
        this.hFim = hFim;
        this.estado = estado;
        this.espacoDesportivo = espacoDesportivo;
        this.usuario = usuario;
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
}
