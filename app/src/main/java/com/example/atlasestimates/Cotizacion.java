package com.example.atlasestimates;


import java.io.Serializable;

public class Cotizacion implements Serializable {
    private String nombreCotizacion;
    private String nombreCliente;
    private String fecha;
    private String imagenUri; // Cambiado de Bitmap a String (URI)
    private String total;
    private String igv;
    private String subTotal;
    private String producto;
    private String descripcion;
    private String metrosLineales;
    private String obtenerPrecio;


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
    public String getPrecio() {
        return obtenerPrecio;
    }

    public void setPrecio(String obtenerPrecio) {
        this.obtenerPrecio = obtenerPrecio;
    }
    public String getTotal(){
        return total;

    }
    public void setTotal(String obtenerTotal){
        this.total = obtenerTotal;
    }
    public String getIgv(){
        return igv;

    }
    public void setIgv(String obtenerIGV){
        this.igv = obtenerIGV;
    }
    public String getSubTotal(){
        return subTotal;

    }
    public void setSubtotal(String obtenerSubTotal){
        this.subTotal = obtenerSubTotal;
    }


}
