package com.example.atlasestimates;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clientes")
public class table_clientes {
    @PrimaryKey(autoGenerate = true)
    private int id_cliente;

    private String nombre_cliente;
    private String dni_ruc;
    private String razon_social;

    // Getters y Setters
    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getDni_ruc() {
        return dni_ruc;
    }

    public void setDni_ruc(String dni_ruc) {
        this.dni_ruc = dni_ruc;
    }
    public void setRazon_social(String razon_social){
        this.razon_social = razon_social;
    }

    public String getRazon_social(){
        return razon_social;
    }
}
