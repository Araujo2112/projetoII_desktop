package com.example.frontendjavafx.dto;

import com.example.frontendjavafx.model.CodPostal;
import com.example.frontendjavafx.model.Usuario;

public class UsuarioRequestDTO {
    public String nome;
    public String tel;
    public Integer nif;
    public String rua;
    public Integer nPorta;
    public String email;
    public String password;
    public CodPostal codPostal;
    public String tipoUsuario;

    public UsuarioRequestDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.tel = usuario.getTel();
        this.nif = usuario.getNif();
        this.rua = usuario.getRua();
        this.nPorta = usuario.getNPorta();
        this.email = usuario.getEmail();
        this.password = usuario.getPassword();
        this.codPostal = usuario.getCodPostal();
        this.tipoUsuario = usuario.getTipoUsuario().getTipo(); // <- isto aqui resolve o problema
    }
}


