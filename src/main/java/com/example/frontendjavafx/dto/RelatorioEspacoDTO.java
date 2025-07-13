package com.example.frontendjavafx.dto;

public class RelatorioEspacoDTO {

    private String lote;
    private int quantidadeReservas;
    private double horasUtilizadas;
    private double precoTotal;
    private double custoManutencao;
    private double percentualUtilizacao;
    private double lucro;

    public RelatorioEspacoDTO(String lote, int quantidadeReservas, double horasUtilizadas, double precoTotal, double custoManutencao, double percentualUtilizacao, double lucro) {
        this.lote = lote;
        this.quantidadeReservas = quantidadeReservas;
        this.horasUtilizadas = horasUtilizadas;
        this.precoTotal = precoTotal;
        this.custoManutencao = custoManutencao;
        this.percentualUtilizacao = percentualUtilizacao;
        this.lucro = lucro;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public int getQuantidadeReservas() {
        return quantidadeReservas;
    }

    public void setQuantidadeReservas(int quantidadeReservas) {
        this.quantidadeReservas = quantidadeReservas;
    }

    public double getHorasUtilizadas() {
        return horasUtilizadas;
    }

    public void setHorasUtilizadas(double horasUtilizadas) {
        this.horasUtilizadas = horasUtilizadas;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public double getCustoManutencao() {
        return custoManutencao;
    }

    public void setCustoManutencao(double custoManutencao) {
        this.custoManutencao = custoManutencao;
    }

    public double getPercentualUtilizacao() {
        return percentualUtilizacao;
    }

    public void setPercentualUtilizacao(double percentualUtilizacao) {
        this.percentualUtilizacao = percentualUtilizacao;
    }

    public double getLucro() {
        return lucro;
    }

    public void setLucro(double lucro) {
        this.lucro = lucro;
    }
}
