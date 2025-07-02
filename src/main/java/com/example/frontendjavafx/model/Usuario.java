package com.example.frontendjavafx.model;

public class Usuario {
    private Integer idUsuario;
    private String nome;
    private String tel;
    private Integer nif;
    private String rua;
    private Integer nPorta;
    private CodPostal codPostal;
    private TipoUsuario tipoUsuario;
    private String email;
    private String password;

    public Usuario() {}

    public Usuario(Integer idUsuario, String nome, String tel, Integer nif, String rua, Integer nPorta, CodPostal codPostal, TipoUsuario tipoUsuario, String email, String password) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.tel = tel;
        this.nif = nif;
        this.rua = rua;
        this.nPorta = nPorta;
        this.codPostal = codPostal;
        this.tipoUsuario = tipoUsuario;
        this.email = email;
        this.password = password;
    }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public Integer getNif() { return nif; }
    public void setNif(Integer nif) { this.nif = nif; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public Integer getNPorta() { return nPorta; }
    public void setNPorta(Integer nPorta) { this.nPorta = nPorta; }

    public CodPostal getCodPostal() { return codPostal; }
    public void setCodPostal(CodPostal codPostal) { this.codPostal = codPostal; }

    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(TipoUsuario tipoUsuario) { this.tipoUsuario = tipoUsuario; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
