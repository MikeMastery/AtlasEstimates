package com.example.atlasestimates.ui.dashboard;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlasestimates.AppDatabase;
import com.example.atlasestimates.CotizacionViewModel;
import com.example.atlasestimates.R;
import com.example.atlasestimates.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemsList;
    private CotizacionViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inicializa el binding
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(CotizacionViewModel.class);

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

    private void cargarServiciosEstaticos() {
        itemsList.clear();

        itemsList.add(new Item("Ingeniería",
                "Instalación, mantenimiento y optimización de sistemas eléctricos, hidráulicos y estructuras mecánicas en proyectos industriales, comerciales y residenciales...",
                R.drawable.ingenieria));

        itemsList.add(new Item("Arquitectura",
                "Consultoría técnica en diseño y planificación arquitectónica para edificios residenciales, comerciales, urbanos y paisajismo...",
                R.drawable.arquitectura));

        itemsList.add(new Item("Topografía",
                "Servicios avanzados de medición, análisis y levantamiento de terrenos para proyectos de construcción...",
                R.drawable.topografia));

        itemsList.add(new Item("Maquinaria Pesada",
                "Asesoría legal y operativa en la adquisición, mantenimiento y uso eficiente de maquinaria pesada...",
                R.drawable.maquinaria));

        itemsList.add(new Item("Abastecimiento de agua",
                "Consultoría en el diseño, gestión y optimización de sistemas de abastecimiento de agua potable...",
                R.drawable.abasagua));

        itemsList.add(new Item("Estructuras Metálicas",
                "Asesoría en el diseño, fabricación, montaje y mantenimiento de estructuras metálicas...",
                R.drawable.estructuras));
    }

    private void cargarProductosEstaticos() {
        itemsList.clear();

        itemsList.add(new Item("Cercos prefabricados",
                "Muros de concreto prefabricados que ofrecen soluciones estéticas y funcionales para la delimitación de propiedades...",
                R.drawable.cercos));

        itemsList.add(new Item("Block de concreto",
                "Bloques modulares de concreto, utilizados en la construcción de muros de albañilería confinada y armada...",
                R.drawable.block));

        itemsList.add(new Item("Cerco cabeza caballo",
                "Elementos modulares de concreto, diseñados para crear muros estructurales resistentes...",
                R.drawable.murete));

        itemsList.add(new Item("Poste de concreto",
                "Postes de concreto prefabricado con refuerzos de acero, diseñados para resistir altas cargas...",
                R.drawable.poste));
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
                Intent intent = new Intent(v.getContext(), activity_product_detail.class);
                intent.putExtra("selectedItem", item);
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