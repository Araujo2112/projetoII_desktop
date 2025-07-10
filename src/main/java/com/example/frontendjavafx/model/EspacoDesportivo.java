package com.example.frontendjavafx.model;

import java.math.BigDecimal;
import java.time.LocalTime;

public class EspacoDesportivo {
    private Integer idEspaco;
    private String lote;
    private Integer capacidade;
    private TipoEspacoDesportivo tipoEspaco;
    private java.math.BigDecimal precoHora;
    private java.time.LocalTime horaAbertura;
    private java.time.LocalTime horaFecho;
    private Boolean disponibilidade;

    public EspacoDesportivo() {}

    public EspacoDesportivo(Integer idEspaco, Integer capacidade, String lote, BigDecimal precoHora, Boolean disponibilidade, LocalTime horaAbertura, LocalTime horaFecho) {
        this.idEspaco = idEspaco;
        this.capacidade = capacidade;
        this.lote = lote;
        this.precoHora = precoHora;
        this.disponibilidade = disponibilidade;
        this.horaAbertura = horaAbertura;
        this.horaFecho = horaFecho;
    }

    public Integer getIdEspaco() {
        return idEspaco;
    }

    public void setIdEspaco(Integer idEspaco) {
        this.idEspaco = idEspaco;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public BigDecimal getPrecoHora() {
        return precoHora;
    }

    public void setPrecoHora(BigDecimal precoHora) {
        this.precoHora = precoHora;
    }

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public LocalTime getHoraAbertura() {
        return horaAbertura;
    }

    public void setHoraAbertura(LocalTime horaAbertura) {
        this.horaAbertura = horaAbertura;
    }

    public LocalTime getHoraFecho() {
        return horaFecho;
    }

    public void setHoraFecho(LocalTime horaFecho) {
        this.horaFecho = horaFecho;
    }

    public TipoEspacoDesportivo getTipoEspaco() {
        return tipoEspaco;
    }

    public void setTipoEspaco(TipoEspacoDesportivo tipoEspaco) {
        this.tipoEspaco = tipoEspaco;
    }

    @Override
    public String toString() {
        return lote;
    }
}
