package com.example.frontendjavafx.model;

public class TipoEstado {
    private Integer idEstado;
    private String estado;

    public TipoEstado() {}

    public TipoEstado(Integer idEstado, String estado) {
        this.idEstado = idEstado;
        this.estado = estado;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
