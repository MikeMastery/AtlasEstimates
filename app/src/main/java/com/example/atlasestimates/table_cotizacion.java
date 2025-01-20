package com.example.atlasestimates;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "cotizacion",
        foreignKeys = @ForeignKey(
                entity = table_clientes.class,
                parentColumns = "id_cliente",
                childColumns = "id_cliente",
                onDelete = ForeignKey.CASCADE
        )
)


public class table_cotizacion {
    @PrimaryKey(autoGenerate = true)
    private int id_cotizacion;
    private int id_cliente ;  // Foreign Key a clientes
    private String titulo;
    private String fecha;
    private double total;
    private String descripcion;
    private String imagen;
    private String ubicacion;
    private String pdfPath;
    private String Total_Servicio;
    private String comentario_plazo;

    private String estado ; // Valor por defecto

    // Getters y setters




    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Getters y Setters

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }
    public int getId_cotizacion() {
        return id_cotizacion;
    }

    public void setId_cotizacion(int id_cotizacion) {
        this.id_cotizacion = id_cotizacion;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTotal_Servicio() {
        return Total_Servicio;
    }

    public void setTotal_Servicio(String total_Servicio) {
        Total_Servicio = total_Servicio;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


    public String getComentario_plazo() {
        return comentario_plazo;
    }

    public void setComentario_plazo(String plazo) {
        this.comentario_plazo = plazo;
    }

}
