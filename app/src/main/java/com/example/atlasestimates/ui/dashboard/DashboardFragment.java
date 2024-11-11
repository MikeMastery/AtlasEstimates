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
    private List<Item> itemsList; // Lista que contendrá servicios o productos

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar RecyclerView
        recyclerView = root.findViewById(R.id.recyclerViewProductos);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Inicializar la lista
        itemsList = new ArrayList<>();

        // Definir el botón de servicios
        binding.servicio.setOnClickListener(v -> {
            cargarServiciosEstaticos();
            adapter.notifyDataSetChanged();
        });

        // Definir el botón de productos
        binding.addProduct.setOnClickListener(v -> {
            cargarProductosEstaticos();
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
                ImageView imageView = holder.itemView.findViewById(R.id.ivProductImage);
                TextView nameView = holder.itemView.findViewById(R.id.tvProductName);
                TextView descriptionView = holder.itemView.findViewById(R.id.tvProductDescription);

                Item item = itemsList.get(position);

                imageView.setImageResource(item.getImageResource());
                nameView.setText(item.getName());
                descriptionView.setText(item.getDescription());
            }

            @Override
            public int getItemCount() {
                return itemsList.size();
            }
        };

        recyclerView.setAdapter(adapter);

        // Cargar productos por defecto
        cargarProductosEstaticos();

        return root;
    }

    // Método para cargar los servicios estáticos
    private void cargarServiciosEstaticos() {
        itemsList.clear();

        // Añadir servicios estáticos
        itemsList.add(new Item("Ingenieria", "Descripción del servicio de instalación", R.drawable.ingenieria));
        itemsList.add(new Item("Arquitectura", "Descripción del servicio de consultoría técnica", R.drawable.arquitectura));
        itemsList.add(new Item("Topografia", "Descripción del servicio de mantenimiento", R.drawable.topografia));
        itemsList.add(new Item("Maquinaria Pesada", "Descripción del servicio de asesoría legal", R.drawable.maquinaria));
        itemsList.add(new Item("Abastecimiento de agua", "Descripción del servicio de asesoría legal", R.drawable.abasagua));
        itemsList.add(new Item("Estructuras Metalicas", "Descripción del servicio de asesoría legal", R.drawable.estructuras));
    }

    // Nuevo método para cargar los productos estáticos
    private void cargarProductosEstaticos() {
        itemsList.clear();

        // Añadir productos estáticos
        itemsList.add(new Item("Cercos Prefabricados", "Permite la visión desde afuera o desde adentro de una propiedad. Los muros prefabricados cuentan con variedad de placas de concreto con diferentes estéticos diseños, este producto aísla totalmente la propiedad, sin permitir la visibilidad.", R.drawable.cercos));
        itemsList.add(new Item("Block Concreto", "Los Bloques de concreto son elementos modulares premoldeados diseñados para la albañilería confinada y armada", R.drawable.block));
        itemsList.add(new Item("Murete de Concreto", "Los Bloques de concreto son elementos modulares premoldeados diseñados para la albañilería confinada y armada", R.drawable.murete));
        itemsList.add(new Item("Poste de Concreto", "Son productos prefabricados de concreto, formados por la mezcla de cemento, agregado grueso, fino, agua y aditivos dentro de esta mezcla se ha colocado una estructura de acero que le da la resistencia a la flexión del poste", R.drawable.poste));
        // Puedes añadir más productos según necesites


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Clase que representa los items (productos o servicios)
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