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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

public class CotizacionAdapter extends RecyclerView.Adapter<CotizacionAdapter.CotizacionViewHolder> implements Filterable {

    private List<table_cotizacion> cotizaciones;
    private List<table_cotizacion> cotizacionesFull;
    private CotizacionViewModel cotizacionViewModel;
    private Context context;
    private AppDatabase appDatabase; // Agregar referencia a la base de datos

    public CotizacionAdapter(List<table_cotizacion> cotizaciones, CotizacionViewModel cotizacionViewModel, Context context) {
        this.cotizaciones = new ArrayList<>(cotizaciones); // Lista filtrada
        this.cotizacionesFull = new ArrayList<>(cotizaciones); // Lista original
        this.cotizacionViewModel = cotizacionViewModel;
        this.context = context;
        this.appDatabase = AppDatabase.getInstance(context); // Inicializar la base de datos
    }
    public void updateCotizaciones(List<table_cotizacion> newCotizaciones) {
        this.cotizaciones = new ArrayList<>(newCotizaciones); // Actualiza la lista filtrada
        this.cotizacionesFull = new ArrayList<>(newCotizaciones); // Actualiza la lista completa
        notifyDataSetChanged(); // Refresca la vista
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

        // Obtener el nombre del cliente en segundo plano
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                table_clientes cliente = appDatabase.clienteDao().getClienteById(cotizacion.getId_cliente());
                return cliente != null ? cliente.getNombre_cliente() : "Cliente desconocido";
            }

            @Override
            protected void onPostExecute(String nombreCliente) {
                holder.tvNombreCliente.setText("Cliente: " + nombreCliente);
            }
        }.execute();


        // Configurar el botón de "Eliminar"
        holder.btnEliminar.setOnClickListener(v -> {
            // Crear el AlertDialog de confirmación
            new AlertDialog.Builder(context)
                    .setMessage("¿Seguro que quieres eliminar el registro?")
                    .setCancelable(false)  // Para que no se pueda cerrar sin responder
                    .setPositiveButton("Sí", (dialog, id) -> {
                        // Si el usuario confirma, eliminar el registro
                        int clienteId = cotizacion.getId_cliente();
                        cotizacionViewModel.deleteClienteYRelaciones(clienteId);
                        cotizaciones.remove(position);  // Elimina la cotización de la lista
                        notifyItemRemoved(position);  // Notifica que el item fue eliminado
                        Toast.makeText(v.getContext(), "Registro eliminado correctamente.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", (dialog, id) -> {
                        // Si el usuario cancela, no hacer nada
                        dialog.dismiss();
                    })
                    .create()
                    .show();  // Muestra el diálogo
        });


            // Configurar el botón de "Ver detalles"
            holder.btnViewDetails.setOnClickListener(v -> {
                // Al hacer clic en el botón, pasamos el id de la cotización
                Intent intent = new Intent(context, mostrardetalles.class);
                intent.putExtra("cotizacionId", cotizacion.getId_cotizacion()); // Pasar solo el ID de la cotización
                context.startActivity(intent); // Iniciar la actividad de detalles
            });
        }
    @Override
    public int getItemCount() {
        return cotizaciones.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<table_cotizacion> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(cotizacionesFull); // Si no hay filtro, usa la lista completa
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (table_cotizacion item : cotizacionesFull) {
                        if (item.getTitulo().toLowerCase().contains(filterPattern) ||
                                item.getFecha().toLowerCase().contains(filterPattern) ||
                                String.valueOf(item.getTotal()).contains(filterPattern)) {
                            filteredList.add(item); // Agregar elementos que coincidan con el filtro
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList; // Pasar la lista filtrada
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                cotizaciones.clear();
                cotizaciones.addAll((List<table_cotizacion>) results.values); // Actualizar la lista filtrada
                notifyDataSetChanged(); // Refrescar el RecyclerView
            }
        };
    }


    public static class CotizacionViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvFecha, tvTotal, tvNombreCliente;
        Button btnEliminar, btnViewDetails;

        public CotizacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvNombreCliente = itemView.findViewById(R.id.tvCliente); // TextView para nombre del cliente
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }
    }
}
