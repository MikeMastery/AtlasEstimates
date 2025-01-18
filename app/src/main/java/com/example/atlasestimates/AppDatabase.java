package com.example.atlasestimates;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {table_cotizacion.class, table_detalleCotizacion.class, table_clientes.class, table_items.class, table_categoria.class}, version = 20)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract CotizacionDao cotizacionDao();
    public abstract DetalleCotizacionDao detalleCotizacionDao();
    public abstract ClienteDao clienteDao();
    public abstract CategoriaDao categoriaDao();
    public abstract ItemsDao itemsDao();


    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "atlas_estimates_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
