package com.example.atlasestimates;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Items",
        foreignKeys = @ForeignKey(
                entity = table_categoria.class,
                parentColumns = "id_categoria",
                childColumns = "id_categoria",
                onDelete = ForeignKey.CASCADE
        )
)
public class table_items {

    @PrimaryKey(autoGenerate = true)
    private int id_items;
    private int id_categoria;  // Foreign Key a subCategoria
    private String nombre_Item;
    private String Supervision;
    private String precio;
    private String stock;

    // Getter y Setter para id_items
    public int getId_items() {
        return id_items;
    }

    public void setId_items(int id_items) {
        this.id_items = id_items;
    }

    // Getter y Setter para id_categoria
    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    // Getter y Setter para nombre_Item
    public String getNombre_Item() {
        return nombre_Item;
    }

    public void setNombre_Item(String nombre_Item) {
        this.nombre_Item = nombre_Item;
    }

    // Getter y Setter para descripcion_p
    // Getter para Supervision
    public String getSupervision() {
        return Supervision;
    }

    // Setter para Supervision (si no existe)
    public void setSupervision(String supervision) {
        this.Supervision = supervision;
    }
    // Getter y Setter para precio
    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    // Getter y Setter para stock
    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
