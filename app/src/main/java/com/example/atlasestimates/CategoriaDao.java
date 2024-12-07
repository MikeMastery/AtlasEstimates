package com.example.atlasestimates;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoriaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(table_categoria categoria);

    // Eliminar una categoría por ID
    @Query("DELETE FROM Categorias WHERE id_categoria = :idCategoria")
    void deleteCategoriaById(int idCategoria);

    @Query("SELECT * FROM Categorias WHERE id_categoria = :categoriaId")
    LiveData<table_categoria> getCategoriaById(int categoriaId);

    // Otros métodos, si es necesario
    @Query("SELECT * FROM Categorias")
    List<table_categoria> getAllCategorias();

    @Update
    void updateCategoria(table_categoria categoria);
}
