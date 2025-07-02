package com.example.frontendjavafx.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Relatorio {
    private Integer id;
    private TipoRelatorio idTipo;
    private LocalDate dataGeracao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String caminhoPdf;
    private String descricao;
    private LocalDate dataCriacao;

    public Relatorio() {}

    public Relatorio(Integer id, TipoRelatorio idTipo, LocalDate dataGeracao, LocalDate dataInicio, LocalDate dataFim, String caminhoPdf, String descricao, LocalDate dataCriacao) {
        this.id = id;
        this.idTipo = idTipo;
        this.dataGeracao = dataGeracao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.caminhoPdf = caminhoPdf;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoRelatorio getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoRelatorio idTipo) {
        this.idTipo = idTipo;
    }

    public LocalDate getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDate dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getCaminhoPdf() {
        return caminhoPdf;
    }

    public void setCaminhoPdf(String caminhoPdf) {
        this.caminhoPdf = caminhoPdf;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getTipoRelatorio() {
        return idTipo.getNome();
    }

    public String getDataCriacaoFormatada() {
        return dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
