package com.example.atlasestimates;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {table_cotizacion.class, table_detalleCotizacion.class, table_clientes.class, table_items.class, table_categoria.class}, version = 21)
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
    // Migración de la versión 1 a la versión 2
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Agregar columna estado
            database.execSQL("ALTER TABLE table_cotizacion ADD COLUMN estado TEXT DEFAULT 'Pendiente'");
        }
    };
}
