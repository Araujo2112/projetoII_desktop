package com.example.frontendjavafx.model;

import java.time.LocalDate;

public class Manutencao {
    private Integer idManu;
    private LocalDate dtIni;
    private LocalDate dtFim;
    private String descricao;
    private TipoEstado estado;
    private EspacoDesportivo espacoDesportivo;
    private Usuario usuario;
    private Double custo;

    public Manutencao() {}

    public Manutencao(Integer idManu, LocalDate dtIni, LocalDate dtFim, String descricao, EspacoDesportivo espacoDesportivo, TipoEstado estado, Usuario usuario, Double custo) {
        this.idManu = idManu;
        this.dtIni = dtIni;
        this.dtFim = dtFim;
        this.descricao = descricao;
        this.espacoDesportivo = espacoDesportivo;
        this.estado = estado;
        this.usuario = usuario;
        this.custo = custo;
    }

    public Integer getIdManu() {
        return idManu;
    }

    public void setIdManu(Integer idManu) {
        this.idManu = idManu;
    }

    public LocalDate getDtIni() {
        return dtIni;
    }

    public void setDtIni(LocalDate dtIni) {
        this.dtIni = dtIni;
    }

    public LocalDate getDtFim() {
        return dtFim;
    }

    public void setDtFim(LocalDate dtFim) {
        this.dtFim = dtFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }
}
