package com.example.atlasestimates;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "constructoradb";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_COTIZACIONES = "Cotizaciones";
    private static final String TABLE_PRODUCTOS = "Productos";

    // Cotizaciones Table Columns
    private static final String COTIZACION_ID = "ID";
    private static final String COTIZACION_NOMBRE = "Nombre";
    private static final String COTIZACION_CLIENTE = "Cliente";
    private static final String COTIZACION_DESCRIPCION = "Descripcion";
    private static final String COTIZACION_FECHA = "Fecha";
    private static final String COTIZACION_IMAGEN = "Imagen";
    private static final String COTIZACION_PRODUCTOS = "Productos";
    private static final String COTIZACION_CANTIDAD = "Cantidad";

    // Productos Table Columns
    private static final String PRODUCTO_ID = "ID";
    private static final String PRODUCTO_NOMBRE = "Nombre";
    private static final String PRODUCTO_DESCRIPCION = "Descripcion";
    private static final String PRODUCTO_IMAGEN = "Imagen";
    private static final String PRODUCTO_UNIDADES = "UnidadesDisponibles";

    // SQL for creating Cotizaciones table
    private static final String CREATE_TABLE_COTIZACIONES = "CREATE TABLE " + TABLE_COTIZACIONES + "("
            + COTIZACION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COTIZACION_NOMBRE + " TEXT,"
            + COTIZACION_CLIENTE + " TEXT,"
            + COTIZACION_DESCRIPCION + " TEXT,"
            + COTIZACION_FECHA + " TEXT,"
            + COTIZACION_IMAGEN + " TEXT,"
            + COTIZACION_PRODUCTOS + " TEXT,"
            + COTIZACION_CANTIDAD + " TEXT" + ")";

    // SQL for creating Productos table
    private static final String CREATE_TABLE_PRODUCTOS = "CREATE TABLE " + TABLE_PRODUCTOS + "("
            + PRODUCTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PRODUCTO_NOMBRE + " TEXT,"
            + PRODUCTO_DESCRIPCION + " TEXT,"
            + PRODUCTO_IMAGEN + " TEXT,"
            + PRODUCTO_UNIDADES + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating tables
        db.execSQL(CREATE_TABLE_COTIZACIONES);
        db.execSQL(CREATE_TABLE_PRODUCTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COTIZACIONES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        // Create tables again
        onCreate(db);
    }

    // Mueve el método insertCotizacion fuera del método onUpgrade
    public long insertCotizacion(String nombre, String cliente, String descripcion, String fecha, String productos, String imagen, String cantidad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COTIZACION_NOMBRE, nombre);
        values.put(COTIZACION_CLIENTE, cliente);
        values.put(COTIZACION_DESCRIPCION, descripcion);
        values.put(COTIZACION_FECHA, fecha);
        values.put(COTIZACION_PRODUCTOS, productos);
        values.put(COTIZACION_IMAGEN, imagen);
        values.put(COTIZACION_CANTIDAD, cantidad);

        // Inserta la fila en la base de datos y retorna el ID de la fila insertada
        long id = db.insert(TABLE_COTIZACIONES, null, values);
        return id;
    }
}


