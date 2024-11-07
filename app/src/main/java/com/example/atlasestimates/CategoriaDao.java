package com.example.atlasestimates;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface CategoriaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(table_categoria categoria);

    // Otros métodos, si es necesario
}
