package com.example.atlasestimates.ui.dashboard;

import android.content.Intent;
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
    private ItemAdapter adapter;
    private List<Item> itemsList;

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
        adapter = new ItemAdapter(itemsList);
        recyclerView.setAdapter(adapter);

        // Cargar productos por defecto
        cargarProductosEstaticos();

        return root;
    }

    // Método para cargar los servicios estáticos
    private void cargarServiciosEstaticos() {
        itemsList.clear();

        // Añadir servicios estáticos
        itemsList.add(new Item("Ingenieria", "Instalación de sistemas y estructuras en proyectos técnicos.", R.drawable.ingenieria));
        itemsList.add(new Item("Arquitectura", "Consultoría técnica en diseño y planificación arquitectónica.", R.drawable.arquitectura));
        itemsList.add(new Item("Topografia", "Servicios de medición y análisis del terreno", R.drawable.topografia));
        itemsList.add(new Item("Maquinaria Pesada", "Asesoría legal en el uso de maquinaria pesada.", R.drawable.maquinaria));
        itemsList.add(new Item("Abastecimiento de agua", "Consultoría en gestión de sistemas de agua potable.", R.drawable.abasagua));
        itemsList.add(new Item("Estructuras Metalicas", "Asesoría legal sobre diseño y construcción de estructuras metálicas.", R.drawable.estructuras));
    }

    // Nuevo método para cargar los productos estáticos
    private void cargarProductosEstaticos() {
        itemsList.clear();

        // Añadir productos estáticos
        itemsList.add(new Item("Cercos Prefabricados", "Muros de concreto con diseños estéticos para mayor privacidad.", R.drawable.cercos));
        itemsList.add(new Item("Block Concreto", "Bloques modulares para albañilería confinada y armada.", R.drawable.block));
        itemsList.add(new Item("Murete de Concreto", "Elementos modulares de concreto para albañilería estructural.", R.drawable.murete));
        itemsList.add(new Item("Poste de Concreto", "Postes prefabricados con acero para alta resistencia a la flexión.", R.drawable.poste));
        // Puedes añadir más productos según necesites
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Adaptador para RecyclerView
    public static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

        private List<Item> itemsList;

        public ItemAdapter(List<Item> itemsList) {
            this.itemsList = itemsList;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tarjetas, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Item item = itemsList.get(position);
            holder.imageView.setImageResource(item.getImageResource());
            holder.nameView.setText(item.getName());
            holder.descriptionView.setText(item.getDescription());

            // Manejar el clic en la tarjeta
            holder.itemView.setOnClickListener(v -> {
                // Enviar el ítem a la actividad de detalles
                Intent intent = new Intent(v.getContext(), activity_product_detail.class);
                intent.putExtra("selectedItem", item); // Pasar el item seleccionado
                v.getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return itemsList.size();
        }

        // ViewHolder para el RecyclerView
        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView nameView;
            TextView descriptionView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.ivProductImage);
                nameView = itemView.findViewById(R.id.tvProductName);
                descriptionView = itemView.findViewById(R.id.tvProductDescription);
            }
        }
    }

    // Clase que representa los items (productos o servicios)
    public static class Item implements java.io.Serializable {
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
