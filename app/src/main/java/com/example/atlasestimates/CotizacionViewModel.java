package com.example.atlasestimates;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CotizacionViewModel extends AndroidViewModel {
    private final AppDatabase appDatabase;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public CotizacionViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application);
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
        });
    }
}
