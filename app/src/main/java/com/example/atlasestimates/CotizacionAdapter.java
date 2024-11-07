package com.example.atlasestimates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CotizacionAdapter extends RecyclerView.Adapter<CotizacionAdapter.CotizacionViewHolder> {

    private List<table_cotizacion> cotizaciones;

    public CotizacionAdapter(List<table_cotizacion> cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    @NonNull
    @Override
    public CotizacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cotizacion, parent, false);
        return new CotizacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CotizacionViewHolder holder, int position) {
        table_cotizacion cotizacion = cotizaciones.get(position); // Cambia Cotizacion a table_cotizacion
        holder.tvTitulo.setText(cotizacion.getTitulo()); // Asegúrate de que table_cotizacion tenga este método
        holder.tvFecha.setText(cotizacion.getFecha()); // Asegúrate de que table_cotizacion tenga este método
        holder.tvTotal.setText("Total: " + cotizacion.getTotal()); // Asegúrate de que table_cotizacion tenga este método
    }


    @Override
    public int getItemCount() {
        return cotizaciones.size();
    }

    public static class CotizacionViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvFecha, tvTotal;

        public CotizacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
}
