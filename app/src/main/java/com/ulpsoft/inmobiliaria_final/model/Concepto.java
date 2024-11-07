package com.ulpsoft.inmobiliaria_final.model;

import androidx.annotation.NonNull;

public class Concepto {

    private int id;
    private String nombre;

    public Concepto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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


    @Override
    public String toString() {

        return "Id: " + id + " Nombre: " + nombre;
    }
}
