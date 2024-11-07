package com.example.atlasestimates;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CotizacionDao{
    @Insert
    long insert(table_cotizacion cotizacion);

    @Query("SELECT * FROM cotizacion")
    List<table_cotizacion> getAllCotizaciones();
}
