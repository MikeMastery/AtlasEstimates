package com.example.atlasestimates;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM clientes WHERE id_cliente = :clienteId")
    LiveData<table_clientes> getClientesById(int clienteId);


    @Query("SELECT * FROM clientes WHERE id_cliente = :idCliente LIMIT 1")
    table_clientes getClienteById(int idCliente);



    @Update
    void updateCliente(table_clientes clientes);


    @Query("SELECT * FROM clientes WHERE id_cliente = :cotizacionId")
    List<table_clientes> getDetallesByCotizacionId(int cotizacionId);

    }




