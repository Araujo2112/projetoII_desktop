package com.example.frontendjavafx.model;

public class TipoPagamento {
    private Integer idTipoPag;
    private String nome;

    public TipoPagamento() {}

    public TipoPagamento(Integer idTipoPag, String nome) {
        this.idTipoPag = idTipoPag;
        this.nome = nome;
    }

    public Integer getIdTipoPag() {
        return idTipoPag;
    }

    public void setIdTipoPag(Integer idTipoPag) {
        this.idTipoPag = idTipoPag;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
