package com.example.atlasestimates;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class CotizacionAdapter extends RecyclerView.Adapter<CotizacionAdapter.CotizacionViewHolder> implements Filterable {

    private List<table_cotizacion> cotizaciones;
    private List<table_cotizacion> cotizacionesFull; // Lista completa para el filtro
    private CotizacionViewModel cotizacionViewModel;
    private Context context;

    public CotizacionAdapter(List<table_cotizacion> cotizaciones, CotizacionViewModel cotizacionViewModel) {
        this.cotizaciones = cotizaciones;
        this.cotizacionViewModel = cotizacionViewModel;
        this.cotizacionesFull = new ArrayList<>(cotizaciones); // Inicializar la lista completa para el filtro
    }

    @NonNull
    @Override
    public CotizacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cotizacion, parent, false);
        context = parent.getContext(); // Guardar el contexto para usarlo en el Intent
        return new CotizacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CotizacionViewHolder holder, int position) {
        table_cotizacion cotizacion = cotizaciones.get(position);
        holder.tvTitulo.setText(cotizacion.getTitulo());
        holder.tvFecha.setText(cotizacion.getFecha());
        holder.tvTotal.setText("Total: " + cotizacion.getTotal());

        // Configurar el botón de "Eliminar"
        holder.btnEliminar.setOnClickListener(v -> {
            int clienteId = cotizacion.getId_cliente();
            cotizacionViewModel.deleteClienteYRelaciones(clienteId);
            cotizaciones.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(v.getContext(), "Registro eliminado correctamente.", Toast.LENGTH_SHORT).show();
        });

        // Configurar el botón de "Ver detalles"
        holder.btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, mostrardetalles.class);
            // Pasar datos adicionales a la actividad de detalles, si es necesario
            intent.putExtra("titulo", cotizacion.getTitulo());
            intent.putExtra("fecha", cotizacion.getFecha());
            intent.putExtra("total", cotizacion.getTotal());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cotizaciones.size();
    }

    @Override
    public Filter getFilter() {
        return cotizacionFilter;
    }

    private final Filter cotizacionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<table_cotizacion> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(cotizacionesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (table_cotizacion item : cotizacionesFull) {
                    if (item.getTitulo().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cotizaciones.clear();
            cotizaciones.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class CotizacionViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvFecha, tvTotal;
        Button btnEliminar, btnViewDetails;

        public CotizacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails); // Enlazar btnViewDetails
        }
    }
}
