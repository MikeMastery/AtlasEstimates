package com.example.atlasestimates;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClienteDao {
    @Insert
    long insert(table_clientes cliente);
    // Eliminar un cliente por ID
    @Query("DELETE FROM clientes WHERE id_cliente = :idCliente")
    void deleteClienteById(int idCliente);

    @Query("SELECT * FROM clientes")
    List<table_clientes> getAllClientes();
}
