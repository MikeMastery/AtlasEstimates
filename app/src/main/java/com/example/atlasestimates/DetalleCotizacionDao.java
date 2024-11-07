package com.example.atlasestimates;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DetalleCotizacionDao {
    @Insert
    void insert(table_detalleCotizacion detalleCotizacion);

    @Query("SELECT * FROM detalle_Cotizacion WHERE id_cotizacion = :idCotizacion")
    List<table_detalleCotizacion> getDetallesByCotizacionId(int idCotizacion);
}
