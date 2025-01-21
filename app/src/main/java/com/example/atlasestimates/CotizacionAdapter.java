package com.example.atlasestimates;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import java.text.DecimalFormat;
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
        holder.tvestado.setText(cotizacion.getEstado());

        // Configurar el estilo del botón según el estado
        holder.tvestado.setBackgroundResource(R.drawable.estado_button_bg);

        // Asignar color según el estado
        switch (cotizacion.getEstado().toLowerCase()) {
            case "aceptada":
                holder.tvestado.setBackgroundColor(Color.parseColor("#4CAF50")); // Verde
                break;
            case "rechazada":
                holder.tvestado.setBackgroundColor(Color.parseColor("#F44336")); // Rojo
                break;
            case "pendiente":
                holder.tvestado.setBackgroundColor(Color.parseColor("#2196F3")); // Azul
                break;
        }

        holder.tvestado.setTextColor(Color.WHITE);
        holder.tvestado.setPadding(24, 8, 24, 8); // A


        // Obtener el total como double
        double total = cotizacion.getTotal(); // Asegúrate de que getTotal() devuelve un double
        String totalServicio = cotizacion.getTotal_Servicio();

        // Formatear el total con comas
        String totalFormateado = formatearNumeroConComas(total);

        // Mostrar el total adecuado según las condiciones
        if (total > 0.0 && (totalServicio == null || totalServicio.isEmpty())) {
            holder.tvTotal.setText("Total: S/ " + totalFormateado);
        } else if (total == 0.0 && totalServicio != null && !totalServicio.isEmpty()) {
            holder.tvTotal.setText("Total: " + totalServicio);
        } else if (total > 0.0 && totalServicio != null && !totalServicio.isEmpty()) {
            holder.tvTotal.setText("Total: S/ " + totalFormateado + "\nTotal Servicio: " + totalServicio);
        } else {
            holder.tvTotal.setText("Sin total disponible");
        }

        // Obtener el nombre del cliente o la razón social en segundo plano
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                // Obtener el cliente de la base de datos
                table_clientes cliente = appDatabase.clienteDao().getClienteById(cotizacion.getId_cliente());

                // Si no se encuentra el cliente, retornar un mensaje genérico
                if (cliente == null) {
                    return "Cliente desconocido";
                }

                // Validar el número del campo DNI o RUC
                String dniRuc = cliente.getDni_ruc(); // Asegúrate de que el campo exista en tu entidad table_clientes
                if (dniRuc == null || dniRuc.isEmpty()) {
                    return "Campo DNI/RUC vacío";
                }

                if (dniRuc.length() == 8) {
                    // Si tiene 8 dígitos, mostrar como Cliente
                    return "Cliente: " + cliente.getNombre_cliente();
                } else if (dniRuc.length() == 11) {
                    // Si tiene 11 dígitos, mostrar como Razón Social
                    return "Razón Social: " + cliente.getRazon_social();
                } else {
                    // Si no cumple con ninguna longitud esperada
                    return "Formato de DNI/RUC no válido";
                }
            }

            @Override
            protected void onPostExecute(String resultado) {
                // Mostrar el resultado en el TextView correspondiente
                holder.tvNombreCliente.setText(resultado);
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
                        Toast toast = Toast.makeText(v.getContext(), "Registro eliminado correctamente.", Toast.LENGTH_SHORT);
                        toast.show();

                        // Reducir el tiempo de duración del Toast
                        new Thread(() -> {
                            try {
                                // Hacer que el Toast dure solo 2 segundos
                                Thread.sleep(1500);  // 1500 ms (1.5 segundos)
                                toast.cancel();  // Cancelar el Toast
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
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

        // Configurar el botón de "Editar"
        holder.btneditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, CrearEditarCotizacionActivity.class);
            intent.putExtra("cotizacionId", cotizacion.getId_cotizacion());
            context.startActivity(intent);
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
                                item.getEstado().toLowerCase().contains(filterPattern) ||
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
        TextView tvTitulo, tvFecha, tvTotal, tvNombreCliente, tvestado;
        Button btnEliminar, btnViewDetails, btneditar;

        public CotizacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvNombreCliente = itemView.findViewById(R.id.tvCliente); // TextView para nombre del cliente
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            btneditar = itemView.findViewById(R.id.btnEditar);
            tvestado = itemView.findViewById(R.id.tvestado);
        }
    }

    private String formatearNumeroConComas(double numero) {
        DecimalFormat formato = new DecimalFormat("#,###");
        return formato.format(numero);
    }


}
