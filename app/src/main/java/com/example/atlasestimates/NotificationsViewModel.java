package com.example.atlasestimates;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.atlasestimates.Notificacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<List<Notificacion>> listaNotificaciones;

    public NotificationsViewModel() {
        listaNotificaciones = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Notificacion>> getListaNotificaciones() {
        return listaNotificaciones;
    }

    public void agregarNotificacion(String fecha) {
        List<Notificacion> listaActual = listaNotificaciones.getValue();
        if (listaActual == null) {
            listaActual = new ArrayList<>();
        }

        // Crear la notificación con el mensaje y la fecha proporcionados
        String mensaje = "Se ha creado una nueva cotización";

        // Agregar la nueva notificación con el mensaje y la fecha
        listaActual.add(new Notificacion(mensaje, fecha));
        listaNotificaciones.setValue(listaActual);
    }
}