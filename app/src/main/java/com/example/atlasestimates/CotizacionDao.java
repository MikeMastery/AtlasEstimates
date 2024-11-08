package com.example.atlasestimates;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CotizacionDao {

    @Insert
    long insert(table_cotizacion cotizacion);

    // Eliminar una cotización por ID
    @Query("DELETE FROM cotizacion WHERE id_cotizacion = :idCotizacion")
    void deleteCotizacionById(int idCotizacion);

    @Query("SELECT nombre_cliente FROM clientes WHERE id_cliente = :clienteId")
    String getNombreCliente(int clienteId);

    // Obtener todas las cotizaciones
    @Query("SELECT * FROM cotizacion")
    List<table_cotizacion> getAllCotizaciones();
}
