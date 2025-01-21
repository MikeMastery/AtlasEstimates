package com.example.atlasestimates;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

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

    @Query("SELECT * FROM cotizacion WHERE id_cotizacion = :cotizacionId")
    LiveData<table_cotizacion> getCotizacionById(int cotizacionId);


    // Obtener todas las cotizaciones
    @Query("SELECT * FROM cotizacion")
    List<table_cotizacion> getAllCotizaciones();

    @Query("SELECT SUM(total) FROM cotizacion")
    LiveData<Double> getSumaTotalIngresos();


        @Query("SELECT SUM(CAST(c.total AS DOUBLE)) FROM cotizacion c " +
                "JOIN Items i ON c.id_cotizacion = i.id_items " +
                "WHERE i.nombre_Item = :nombreItem")
        Double getTotalPorItem(String nombreItem);


    @Query("UPDATE cotizacion SET estado = :nuevoEstado WHERE id_cotizacion = :cotizacionId")
    void actualizarEstado(int cotizacionId, String nuevoEstado);


    @Query("SELECT * FROM cotizacion ORDER BY fecha DESC")
    List<table_cotizacion> getAllCotizacionesOrderByDate();

    @Update
        void updateCotizacion(table_cotizacion cotizacion);




}
