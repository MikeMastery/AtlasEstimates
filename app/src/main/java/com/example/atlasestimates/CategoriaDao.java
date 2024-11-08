package com.example.atlasestimates;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoriaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(table_categoria categoria);

    // Eliminar una categoría por ID
    @Query("DELETE FROM Categorias WHERE id_categoria = :idCategoria")
    void deleteCategoriaById(int idCategoria);

    // Otros métodos, si es necesario
    @Query("SELECT * FROM Categorias")
    List<table_categoria> getAllCategorias();
}
