package com.example.atlasestimates;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categorias")
public class table_categoria {

    @PrimaryKey(autoGenerate = true)
    private int id_categoria;
    private String nombre_categoria; // Nombre de la categoría
    private String descripcion_categoria = "Descripción predeterminada"; // Descripción estática

    // Getters y Setters
    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }

    public String getDescripcion_categoria() {
        return descripcion_categoria;
    }

    public void setDescripcion_categoria(String descripcion_categoria) {
        this.descripcion_categoria = descripcion_categoria;
    }
}

