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
    private String medida;
    private String desarrolloProyecto;
    private String textviewDesarrollo;
    private String supervison;
    private String TotalIngeArqui;
    private String totalTopogrgafia;
    private String comentarioTopografia;
    private String totalconstruccionObra;
    private String totalEstructura;
    private String TotaldAgua;
    private String CantidadAgua;
    private String MontoMovilizacion;
    private String TipoMaquina;
    private String HorasAlquiler;
    private String CostoHora;
    private String CantidadMaquinaGlobal;
    private String CostoTotalGlobalMaquina;
    private String PlazoEntrega;



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

    public void setPrecioEquiposMenores(String obtenerPrecioAgua){
        this.precioAgua = obtenerPrecioAgua;
    }
    public String getPrecioEquiposMenores() {
        return precioAgua;
    }

    public void setEquipoMenor(String obtenerCantidadAgua){
        this.cantidaAgua = obtenerCantidadAgua;
    }
    public String getEquipoMenor() {
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

    // Métodos getter y setter para 'medida'
    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getDesarrolloProyecto(){
        return  desarrolloProyecto;
    }

    public void setDesarrolloProyecto(String obtenerDesarrollo){
        this.desarrolloProyecto = obtenerDesarrollo;
    }

    public String gettextodesarrollo(){
        return  textviewDesarrollo;
    }

    public void setTextodesarrollo(String obtenertextoDesarrollo){
        this.textviewDesarrollo = obtenertextoDesarrollo;
    }

    public String getsupersionSINO (){
        return  supervison;


    }

    public void  setSupervisonSINO (String obtnerSupervision){
        this.supervison = obtnerSupervision;
    }

    public String gettotalInAr(){
        return TotalIngeArqui;
    }

    public void setTotalIngeArqui(String obtenerTotalIngeArqui){
        this.TotalIngeArqui = obtenerTotalIngeArqui;
    }

    public void settotaltopografia(String obtenertotaltopografia){
        this.totalTopogrgafia = obtenertotaltopografia;
    }

    public String getTotalTopogrgafia(){
        return totalTopogrgafia;
    }

    public void setComentarioTopografia(String obtenercomentario){
        this.comentarioTopografia = obtenercomentario;
    }

    public String getcoementarioTopografia(){
        return comentarioTopografia;
    }

    public void setCampoConstruccionObra(String obtenercampoObra){
        this.totalconstruccionObra = obtenercampoObra;
    }

    public String getCampoConstruccionObra(){
        return totalconstruccionObra;
    }

    public void setCampoEstructura(String obtenerestructura){
        this.totalEstructura = obtenerestructura;
    }

    public String getCampoEstructura(){
        return totalEstructura;
    }
    public void setCampoTotalAgua(String obtenerTotalAgua){
        this.TotaldAgua = obtenerTotalAgua;
    }

    public String getCampoTotalAgua(){
        return TotaldAgua;
    }

    public void setCantidadAgua(String obteneresCantidadAgua){
        this.CantidadAgua = obteneresCantidadAgua;
    }

    public String getCantidaAgua(){
        return CantidadAgua;
    }


    public void setMontoMovilizacion(String obteneresMontoMovilizacion){
        this.MontoMovilizacion = obteneresMontoMovilizacion;
    }

    public String getMovilizacion(){
        return MontoMovilizacion;
    }
    public void setMaquina(String obteneresMaquinaria){
        this.TipoMaquina = obteneresMaquinaria;
    }

    public String getMaquina(){
        return TipoMaquina;
    }


    public void setHorasAlquiler(String obteneresHoras){
        this.HorasAlquiler = obteneresHoras;
    }

    public String getHorasAlquiler(){
        return HorasAlquiler;
    }

    public void setCostoHora(String obtenererCosto){
        this.CostoHora = obtenererCosto;
    }

    public String getCostoHora(){
        return CostoHora;
    }

    public void setCantidadMaquinaGlobal(String obtenererCantidadMaquina){
        this.CantidadMaquinaGlobal = obtenererCantidadMaquina;
    }

    public String getCantidadMaquinaGlobal(){
        return  CantidadMaquinaGlobal;
    }

    public void setCostoMaquinaGlobal(String obtenererCosto){
        this.CostoTotalGlobalMaquina = obtenererCosto;
    }

    public String getCostoMaquinaGlobal(){
        return  CostoTotalGlobalMaquina;
    }

    public void setPlazoEntrega(String obtenerplazo){
        this.PlazoEntrega = obtenerplazo;
    }

    public String getPlazoEntrega(){
        return  PlazoEntrega;
    }





}
