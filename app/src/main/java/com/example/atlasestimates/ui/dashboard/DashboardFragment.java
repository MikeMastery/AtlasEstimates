package com.example.atlasestimates.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlasestimates.R;
import com.example.atlasestimates.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Item> serviciosList; // Lista que contendrá solo los servicios

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar RecyclerView
        recyclerView = root.findViewById(R.id.recyclerViewProductos);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Crear la lista de servicios
        serviciosList = new ArrayList<>();

        // Definir el botón de servicios
        binding.servicio.setOnClickListener(v -> {
            // Cargar servicios estáticos
            cargarServiciosEstaticos();
            adapter.notifyDataSetChanged();
        });

        // Configurar el adaptador
        adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.tarjetas, parent, false);
                return new RecyclerView.ViewHolder(view) {};
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                // Encontrar las vistas en el layout
                ImageView imageView = holder.itemView.findViewById(R.id.ivProductImage);
                TextView nameView = holder.itemView.findViewById(R.id.tvProductName);
                TextView descriptionView = holder.itemView.findViewById(R.id.tvProductDescription);

                // Obtener el item correspondiente
                Item servicio = serviciosList.get(position);

                // Configurar las vistas con datos
                imageView.setImageResource(servicio.getImageResource());
                nameView.setText(servicio.getName());
                descriptionView.setText(servicio.getDescription());
            }

            @Override
            public int getItemCount() {
                return serviciosList.size(); // El tamaño de la lista de servicios
            }
        };

        recyclerView.setAdapter(adapter);

        return root;
    }

    // Método para cargar los servicios estáticos
    private void cargarServiciosEstaticos() {
        // Limpiar la lista antes de cargar los servicios
        serviciosList.clear();

        // Añadir servicios estáticos
        serviciosList.add(new Item("Ingenieria", "Descripción del servicio de instalación", R.drawable.ingenieria));
        serviciosList.add(new Item("Arquitectura", "Descripción del servicio de consultoría técnica", R.drawable.arquitectura));
        serviciosList.add(new Item("Topografia", "Descripción del servicio de mantenimiento", R.drawable.topografia));
        serviciosList.add(new Item("Maquinaria Pesada", "Descripción del servicio de asesoría legal", R.drawable.maquinaria));
        serviciosList.add(new Item("Abastecimiento de agua", "Descripción del servicio de asesoría legal", R.drawable.abasagua));
        serviciosList.add(new Item("Estructuras Metalicas", "Descripción del servicio de asesoría legal", R.drawable.estructuras));

        // Puedes añadir más servicios según sea necesario
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Clase que representa los servicios
    public static class Item {
        private String name;
        private String description;
        private int imageResource;

        public Item(String name, String description, int imageResource) {
            this.name = name;
            this.description = description;
            this.imageResource = imageResource;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getImageResource() {
            return imageResource;
        }
    }
}
