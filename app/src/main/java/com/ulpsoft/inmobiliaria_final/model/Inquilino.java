package com.ulpsoft.inmobiliaria_final.model;

import java.io.Serializable;

public class Inquilino implements Serializable {
    private int id;
    private String nombre;
    private String apellido;
    private String documento;
    private String telefono;
    private String email;


    public Inquilino(int id, String nombre, String apellido, String documento, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.telefono = telefono;
        this.email = email;
    }

    public Inquilino() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    @Override
    public String toString() {

        return "Id: " + id + " Nombre: " + nombre + " Apellido: " + apellido + " Documento: " + documento + " Telefono: " + telefono + " Email: " + email;

    }
}


