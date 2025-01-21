package com.example.atlasestimates.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlasestimates.R;
import com.example.atlasestimates.table_cotizacion;

import java.util.ArrayList;
import java.util.List;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.NotificacionViewHolder> {
    private List<table_cotizacion> cotizaciones = new ArrayList<>();

    public void setCotizaciones(List<table_cotizacion> cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    @NonNull
    @Override
    public NotificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notificacion, parent, false);
        return new NotificacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacionViewHolder holder, int position) {
        table_cotizacion cotizacion = cotizaciones.get(position);
        holder.tituloTextView.setText("Cotización creada");  // Título por defecto
        holder.fechaTextView.setText(cotizacion.getFecha());
    }

    @Override
    public int getItemCount() {
        return cotizaciones.size();
    }

    static class NotificacionViewHolder extends RecyclerView.ViewHolder {
        TextView tituloTextView;
        TextView fechaTextView;

        NotificacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.textViewTituloNotificacion);
            fechaTextView = itemView.findViewById(R.id.textViewFechaNotificacion);
        }
    }
}