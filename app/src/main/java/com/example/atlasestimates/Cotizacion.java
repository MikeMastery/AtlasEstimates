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
    private String horasMaquina;
    private String precioHora;
    private String categoria;
    private String cantidaAgua;
    private String precioAgua;
    private String identificacion;
    private String Ubicacion;
    private String dni;
    private String ruc;
    private String razonsocial;


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

    public void setHorasMaquina(String obtenerHoraMaquina){
        this.horasMaquina= obtenerHoraMaquina;
    }
    public void setPrecioHora(String obtenerPrecioHora){
        this.precioHora = obtenerPrecioHora;
    }

    public String getHorasMaquina() {
        return horasMaquina;
    }

    public String getPrecioHora() {
        return precioHora;
    }

    public void setCategoria(String obtenerCategoria){
        this.categoria = obtenerCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setPrecioAgua(String obtenerPrecioAgua){
        this.precioAgua = obtenerPrecioAgua;
    }
    public String getPrecioAgua() {
        return precioAgua;
    }

    public void setCantidadAgua(String obtenerCantidadAgua){
        this.cantidaAgua = obtenerCantidadAgua;
    }
    public String getCantidadAgua() {
        return cantidaAgua;
    }

    public void setIdentificacion(String obtenerIdentificacion){
        this.identificacion = obtenerIdentificacion;

    }

    public String getIdentificacion(){
        return identificacion;
    }

    public void setUbicacion(String obtenerUbicacion){
        this.Ubicacion  = obtenerUbicacion;
    }

    public String getUbicacion(){
        return Ubicacion;
    }

    public void setDni(String obtnerDNI){
        this.dni = obtnerDNI;
    }

    public  String getDni(){
        return dni;
    }

    public void setRuc(String obtenerRuc){
        this.ruc = obtenerRuc;
    }

    public String getRuc(){
        return ruc;
    }

    public void setRazonSocial(String obtnerRazonSocial){
        this.razonsocial = obtnerRazonSocial;

    }

    public String getRazonsocial(){
        return razonsocial;
    }

}
