package com.example.atlasestimates;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlasestimates.R;
import com.example.atlasestimates.Notificacion;

import java.util.ArrayList;
import java.util.List;

public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.ViewHolder> {

    private List<Notificacion> notificaciones;

    public NotificacionesAdapter() {
        notificaciones = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notificacion notificacion = notificaciones.get(position);
        holder.mensajeTextView.setText(notificacion.getMensaje());
        holder.fechaTextView.setText(notificacion.getFecha());
    }

    @Override
    public int getItemCount() {
        return notificaciones.size();
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
        notifyDataSetChanged();  // Asegúrate de notificar al adaptador que la lista ha cambiado
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mensajeTextView, fechaTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mensajeTextView = itemView.findViewById(R.id.textoMensaje);
            fechaTextView = itemView.findViewById(R.id.textoFecha);
        }
    }
}
