package com.example.atlasestimates;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DetalleCotizacionDao {
    @Insert
    void insert(table_detalleCotizacion detalleCotizacion);
    // Eliminar detalles por ID de cotización
    @Query("DELETE FROM detalle_Cotizacion WHERE id_cotizacion = :idCotizacion")
    void deleteDetallesByCotizacionId(int idCotizacion);

    @Query("SELECT * FROM detalle_Cotizacion WHERE id_cotizacion = :cotizacionId")
    LiveData<List<table_detalleCotizacion>> getDetalleByCotizacionId(int cotizacionId);


    @Query("SELECT * FROM detalle_Cotizacion WHERE id_cotizacion = :idCotizacion")
    List<table_detalleCotizacion> getDetallesByCotizacionId(int idCotizacion);
}
