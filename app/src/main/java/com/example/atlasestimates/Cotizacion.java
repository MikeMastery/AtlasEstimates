package com.example.atlasestimates;


import java.io.Serializable;

public class Cotizacion implements Serializable {
    private String nombreCotizacion;
    private String nombreCliente;
    private String fecha;
    private String imagenUri; // Cambiado de Bitmap a String (URI)
    private String otrosDatos;
    private String producto;
    private String descripcion;
    private String metrosLineales;


    // Getters y Setters
    public String getNombreCotizacion() {
        return nombreCotizacion;
    }

    public void setNombreCotizacion(String nombreCotizacion) {
        this.nombreCotizacion = nombreCotizacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagenUri() {
        return imagenUri;
    }

    public void setImagenUri(String imagenUri) {
        this.imagenUri = imagenUri;
    }

    public String getProducto() {
        return producto;
    }


    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMetrosLineales() {
        return metrosLineales;
    }

    public void setMetrosLineales(String metrosLineales) {
        this.metrosLineales = metrosLineales;
    }




    public String getOtrosDatos() {
        return otrosDatos;
    }

    public void setOtrosDatos(String otrosDatos) {
        this.otrosDatos = otrosDatos;
    }
}
