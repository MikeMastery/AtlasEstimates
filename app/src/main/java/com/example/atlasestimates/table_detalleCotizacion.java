package com.example.atlasestimates;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "detalle_Cotizacion")
public class table_detalleCotizacion {
    @PrimaryKey(autoGenerate = true)
    private int id_detalle;
    private int id_cotizacion; // Foreign Key a cotizacion
    private int id_items;      // Foreign Key a items
    private double cantidad;
    private double subtotal;
    private double igv;

    // Getters y Setters
    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getId_cotizacion() {
        return id_cotizacion;
    }

    public void setId_cotizacion(int id_cotizacion) {
        this.id_cotizacion = id_cotizacion;
    }

    public int getId_items() {
        return id_items;
    }

    public void setId_items(int id_items) {
        this.id_items = id_items;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }
}
