package com.example.atlasestimates;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClienteDao {
    @Insert
    long insert(table_clientes cliente);

    @Query("SELECT * FROM clientes")
    List<table_clientes> getAllClientes();
}
