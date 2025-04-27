package com.example.frontendjavafx.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Notificacao {
    private Integer idNotificacao;
    private Usuario usuario;
    private String mensagem;
    private LocalDate dataNotificacao;
    private LocalTime horaNotificacao;

    public Notificacao() {}

    public Notificacao(Integer idNotificacao, Usuario usuario, String mensagem, LocalDate dataNotificacao, LocalTime horaNotificacao) {
        this.idNotificacao = idNotificacao;
        this.usuario = usuario;
        this.mensagem = mensagem;
        this.dataNotificacao = dataNotificacao;
        this.horaNotificacao = horaNotificacao;
    }

    public Integer getIdNotificacao() {
        return idNotificacao;
    }

    public void setIdNotificacao(Integer idNotificacao) {
        this.idNotificacao = idNotificacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDate getDataNotificacao() {
        return dataNotificacao;
    }

    public void setDataNotificacao(LocalDate dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }

    public LocalTime getHoraNotificacao() {
        return horaNotificacao;
    }

    public void setHoraNotificacao(LocalTime horaNotificacao) {
        this.horaNotificacao = horaNotificacao;
    }
}

