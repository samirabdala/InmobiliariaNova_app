package com.ulpsoft.inmobiliaria_final.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String email;
    private String nombreCompleto;
    private String token;
    private String expiracion; // Cambiar el tipo a String si no necesitas la clase DateTime
    private String avatar;
    public Usuario() {
    }

    public Usuario(String email, String nombreCompleto, String token, String expiracion, String avatar) {
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.token = token;
        this.expiracion = expiracion;
        this.avatar = avatar;
    }
    public Usuario(String email, String nombreCompleto, String avatar) {
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.avatar = avatar;
    }
    public String getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(String expiracion) {
        this.expiracion = expiracion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
