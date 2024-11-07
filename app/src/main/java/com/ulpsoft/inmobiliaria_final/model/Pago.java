package com.ulpsoft.inmobiliaria_final.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.google.type.DateTime;

import java.io.Serializable;
import java.util.jar.Attributes;

public class Pago {
    private int id;
    private int idContrato;
    private String fecha;
    private double monto;
    private String estado;
    private String usuPago;
    private String detalle;
    private int nro;

    @SerializedName("concepto")
    private Concepto concepto;

    // Constructor
    public Pago(int id, int idContrato, String fecha, double monto, String estado, String usuPago, String detalle, int nro, Concepto concepto) {
        this.id = id;
        this.idContrato = idContrato;
        this.fecha = fecha;
        this.monto = monto;
        this.estado = estado;
        this.usuPago = usuPago;

        this.detalle = detalle;
        this.nro = nro;
        this.concepto = concepto;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuPago() {
        return usuPago;
    }

    public void setUsuPago(String usuPago) {
        this.usuPago = usuPago;
    }


    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", idContrato=" + idContrato +
                ", fecha='" + fecha + '\'' +
                ", monto=" + monto +
                ", estado='" + estado + '\'' +
                ", usuPago='" + usuPago + '\'' +
                ", detalle='" + detalle + '\'' +
                ", nro=" + nro +
                ", concepto=" + concepto +
                '}';
    }
}
