package com.example.atlasestimates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class CotizacionAdapter extends RecyclerView.Adapter<CotizacionAdapter.CotizacionViewHolder> implements Filterable {

    private List<table_cotizacion> cotizaciones;
    private List<table_cotizacion> cotizacionesFull; // Lista completa para el filtro
    private CotizacionViewModel cotizacionViewModel;

    public CotizacionAdapter(List<table_cotizacion> cotizaciones, CotizacionViewModel cotizacionViewModel) {
        this.cotizaciones = cotizaciones;
        this.cotizacionViewModel = cotizacionViewModel;
        this.cotizacionesFull = new ArrayList<>(cotizaciones); // Inicializar la lista completa para el filtro
    }

    @NonNull
    @Override
    public CotizacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cotizacion, parent, false);
        return new CotizacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CotizacionViewHolder holder, int position) {
        table_cotizacion cotizacion = cotizaciones.get(position);
        holder.tvTitulo.setText(cotizacion.getTitulo());
        holder.tvFecha.setText(cotizacion.getFecha());
        holder.tvTotal.setText("Total: " + cotizacion.getTotal());

        // Al hacer clic en el botón de eliminar
        holder.btnEliminar.setOnClickListener(v -> {
            // Obtén el ID del cliente asociado a esta cotización
            int clienteId = cotizacion.getId_cliente();

            // Llama al ViewModel para eliminar el cliente y relaciones
            cotizacionViewModel.deleteClienteYRelaciones(clienteId);

            // Remueve el elemento de la lista y notifica el cambio
            cotizaciones.remove(position);
            notifyItemRemoved(position);

            // Mostrar un Toast indicando éxito
            Toast.makeText(v.getContext(), "Registro eliminado correctamente.", Toast.LENGTH_SHORT).show();
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
                filteredList.addAll(cotizacionesFull); // Muestra la lista completa si no hay filtro
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (table_cotizacion item : cotizacionesFull) {
                    // Filtra por el título o cualquier otro campo de `table_cotizacion`
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
            cotizaciones.addAll((List) results.values); // Actualiza la lista de cotizaciones filtrada
            notifyDataSetChanged();
        }
    };

    public static class CotizacionViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvFecha, tvTotal;
        Button btnEliminar;

        public CotizacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
