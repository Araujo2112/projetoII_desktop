package com.example.frontendjavafx.model;

public class TipoEspacoDesportivo {
    private Integer idTipoEspaco;
    private String tipo;

    public TipoEspacoDesportivo() {}

    public TipoEspacoDesportivo(Integer idTipoEspaco, String tipo) {
        this.idTipoEspaco = idTipoEspaco;
        this.tipo = tipo;
    }

    public Integer getIdTipoEspaco() {
        return idTipoEspaco;
    }

    public void setIdTipoEspaco(Integer idTipoEspaco) {
        this.idTipoEspaco = idTipoEspaco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return tipo; // Para mostrar bem no ComboBox
    }
}
