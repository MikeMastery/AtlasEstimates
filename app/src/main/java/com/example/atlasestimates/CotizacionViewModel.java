package com.example.atlasestimates;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CotizacionViewModel extends AndroidViewModel {
    private final AppDatabase appDatabase;

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<List<table_cotizacion>> cotizaciones = new MutableLiveData<>();

    public CotizacionViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application);

    }

    public void actualizarCotizacion(table_cotizacion cotizacion) {
        executor.execute(() -> {
            appDatabase.cotizacionDao().updateCotizacion(cotizacion);
        });
    }

    // Método para actualizar los datos del cliente
    public void actualizarCliente(table_clientes clientes) {
        executor.execute(() -> {
            appDatabase.clienteDao().updateCliente(clientes); // Asegúrate de que tengas este DAO
        });
    }

    // Método para actualizar los datos del cliente
    public void actualizarCategoria(table_categoria categoria) {
        executor.execute(() -> {
            appDatabase.categoriaDao().updateCategoria(categoria); // Asegúrate de que tengas este DAO
        });
    }









    public LiveData<List<table_cotizacion>> getCotizaciones() {
        return cotizaciones;
    }

    public void loadCotizaciones(Context context) {
        // Usar AsyncTask ya que estás en Java
        new AsyncTask<Void, Void, List<table_cotizacion>>() {
            @Override
            protected List<table_cotizacion> doInBackground(Void... voids) {
                AppDatabase db = AppDatabase.getInstance(context);
                return db.cotizacionDao().getAllCotizaciones();
            }

            @Override
            protected void onPostExecute(List<table_cotizacion> result) {
                cotizaciones.setValue(result);
            }
        }.execute();
    }




    public void deleteClienteYRelaciones(int clienteId) {
        executor.execute(() -> {
            // 1. Eliminar los detalles de cotización relacionados
            appDatabase.detalleCotizacionDao().deleteDetallesByCotizacionId(clienteId);

            // 2. Eliminar la cotización asociada
            appDatabase.cotizacionDao().deleteCotizacionById(clienteId);

            // 3. Finalmente, eliminar el cliente
            appDatabase.clienteDao().deleteClienteById(clienteId);

            appDatabase.itemsDao().deleteItemById(clienteId);  // Ejemplo si es necesario
            appDatabase.categoriaDao().deleteCategoriaById(clienteId);

            // Recargar la lista de cotizaciones y actualizar el LiveData
            List<table_cotizacion> updatedCotizaciones = appDatabase.cotizacionDao().getAllCotizaciones();
            cotizaciones.postValue(updatedCotizaciones); // Actualiza el LiveData

        });
    }

    // Obtener una cotización por su ID
    public LiveData<table_cotizacion> getCotizacion(int cotizacionId) {
        return appDatabase.cotizacionDao().getCotizacionById(cotizacionId);
    }

    // Obtener los detalles de una cotización por su ID
    public LiveData<List<table_detalleCotizacion>> getDetalles(int cotizacionId) {
        return appDatabase.detalleCotizacionDao().getDetalleByCotizacionId(cotizacionId);
    }

    // Obtener los ítems por su ID de detalle
    public LiveData<List<table_items>> getItems(int detalleId) {
        return appDatabase.itemsDao().getItemsByDetalleId(detalleId);
    }

    // Obtener un cliente relacionado a la cotización
    public LiveData<table_clientes> getCliente(int cotizacionId) {
        return appDatabase.clienteDao().getClientesById(cotizacionId);
    }

    // Obtener la categoría de los ítems
    public LiveData<table_categoria> getCategoria(int categoriaId) {
        return appDatabase.categoriaDao().getCategoriaById(categoriaId);
    }
}
