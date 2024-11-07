package com.ulpsoft.inmobiliaria_final.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class Inmueble implements Serializable {
    private int id;
    private int uso;
    private String direccion;
    private int tipoId;
    private int ambientes;
    private Double latitud;
    private Double longitud;
    private Double superficie;
    private Double precio;

    @SerializedName("idPropietario")
    private int idPropietario;

    private Boolean estado;
    private String imgUrl;

    @SerializedName("tipo")
    private Tipo tipo;

    @SerializedName("propietarioInmueble")
    private Propietario propietarioInmueble;

    public Inmueble(int id, int uso, String direccion, Tipo tipo,
                    int ambientes, Double latitud, Double longitud, Double superficie, Double precio,
                    Propietario propietarioInmueble, Boolean estado, String imgUrl) {
        this.id = id;
        this.uso = uso;
        this.direccion = direccion;
        this.tipo = tipo;
        this.ambientes = ambientes;
        this.latitud = latitud;
        this.longitud = longitud;
        this.superficie = superficie;
        this.precio = precio;
        this.propietarioInmueble = propietarioInmueble;
        this.estado = estado;
        this.imgUrl = imgUrl;
    }

    public Inmueble() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUso() {
        return uso;
    }

    public void setUso(int usoInmueble) {
        this.uso = usoInmueble;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }


    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(Double superficie) {
        this.superficie = superficie;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Propietario getPropietarioInmueble() {
        return propietarioInmueble;
    }

    public void setPropietarioInmueble(Propietario propietarioInmueble) {
        this.propietarioInmueble = propietarioInmueble;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String usoDescripcion() {
        if (uso == 1) {
            return "Residencial";
        } else {
            return "Comercial";

        }
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "id=" + id +
                ", uso=" + uso +
                ", direccion='" + direccion + '\'' +
                ", tipo=" + tipo +
                ", ambientes=" + ambientes +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", superficie=" + superficie +
                ", precio=" + precio +
                ", propietarioInmueble=" + propietarioInmueble +
                ", estado=" + estado +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }


}
