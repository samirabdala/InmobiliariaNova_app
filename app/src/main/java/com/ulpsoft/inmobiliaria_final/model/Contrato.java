package com.ulpsoft.inmobiliaria_final.model;

import com.google.gson.annotations.SerializedName;
import com.google.type.DateTime;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Contrato implements Serializable {
    private int id;

    @SerializedName("inqui")
    private Inquilino inqui;

    @SerializedName("inmu")
    private Inmueble inmu;

    @SerializedName("propId")
    private int propId;

    @SerializedName("fechaInicio")
    private String fechaInicio;

    @SerializedName("fechaFin")
    private String fechaFin;

    private int estado;
    private String descripcion;
    private String observaciones;

    @SerializedName("pagos")
    private int pagos;

    @SerializedName("precioInmueble")
    private double precioInmueble;

    @SerializedName("direccionInmueble")
    private String direccionInmueble;

    public Contrato() {
    }

    public Contrato(int id, Inquilino inqui, Inmueble inmu, int propId, String fechaInicio, String fechaFin, int estado, String descripcion, String observaciones, int pagos) {
        this.id = id;
        this.inqui = inqui;
        this.inmu = inmu;
        this.propId = propId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
        this.pagos = pagos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Inquilino getInqui() {
        return inqui;
    }

    public void setInqui(Inquilino inqui) {
        this.inqui = inqui;
    }

    public Inmueble getInmu() {
        return inmu;
    }

    public void setInmu(Inmueble inmu) {
        this.inmu = inmu;
    }

    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getPagos() {
        return pagos;
    }

    public void setPagos(int pagos) {
        this.pagos = pagos;
    }

    // Getters y setters

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", inqui=" + (inqui != null ? inqui.toString() : "null") +
                ", inmu=" + (inmu != null ? inmu.toString() : "null") +
                ", propId=" + propId +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado=" + estado +
                ", descripcion='" + descripcion + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", pagos=" + pagos +
                +
                '}';
    }
}
