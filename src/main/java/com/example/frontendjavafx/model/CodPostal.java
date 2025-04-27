package com.example.frontendjavafx.model;

public class CodPostal {
    private String idCodPostal;
    private String localidade;

    public CodPostal() {
    }

    public CodPostal(String idCodPostal, String localidade) {
        this.idCodPostal = idCodPostal;
        this.localidade = localidade;
    }

    public String getIdCodPostal() {
        return idCodPostal;
    }

    public void setIdCodPostal(String idCodPostal) {
        this.idCodPostal = idCodPostal;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }
}

