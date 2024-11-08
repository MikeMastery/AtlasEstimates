package com.example.atlasestimates;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemsDao {

    // Insertar un item en la base de datos
    @Insert
    long insert(table_items item);

    // Eliminar un item por ID
    @Query("DELETE FROM Items WHERE id_items = :idItem")
    void deleteItemById(int idItem);


    // Obtener todos los items
    @Query("SELECT * FROM Items")
    List<table_items> getAllItems();

    // Obtener un item por su ID
    @Query("SELECT * FROM Items WHERE id_items = :id")
    table_items getItemById(int id);

    // Obtener todos los items por id de categoría
    @Query("SELECT * FROM Items WHERE id_categoria = :categoriaId")
    List<table_items> getItemsByCategoria(int categoriaId);
}
