package com.example.atlasestimates;


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
    private int id_cliente;  // Foreign Key a clientes
    private String titulo;
    private String fecha;
    private double total;
    private String descripcion;
    private String imagen;
    private String ubicacion;

    // Getters y Setters
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

}
