package com.example.frontendjavafx.model;

public class TipoUsuario {
    private Integer idTipoUsuario;
    private String tipo;

    public TipoUsuario() {}

    public TipoUsuario(Integer idTipoUsuario, String tipo) {
        this.idTipoUsuario = idTipoUsuario;
        this.tipo = tipo;
    }

    public Integer getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
