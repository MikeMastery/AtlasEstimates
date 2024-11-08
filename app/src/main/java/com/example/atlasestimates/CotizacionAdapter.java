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

import java.util.List;

public class CotizacionAdapter extends RecyclerView.Adapter<CotizacionAdapter.CotizacionViewHolder> {

    private List<table_cotizacion> cotizaciones;
    private CotizacionViewModel cotizacionViewModel;

    public CotizacionAdapter(List<table_cotizacion> cotizaciones, CotizacionViewModel cotizacionViewModel) {
        this.cotizaciones = cotizaciones;
        this.cotizacionViewModel = cotizacionViewModel;
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

    public static class CotizacionViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvFecha, tvTotal, mostrarcliente;
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
