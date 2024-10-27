package com.example.atlasestimates.ui.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.atlasestimates.Producto;
import com.example.atlasestimates.ProductoAdapter;
import com.example.atlasestimates.R;
import com.example.atlasestimates.databinding.FragmentDashboardBinding;
import com.example.atlasestimates.Activity_nuevoProducto;
import com.example.atlasestimates.nueva_cotizacion;
import com.example.atlasestimates.nuevo_servicio;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar RecyclerView
        recyclerView = root.findViewById(R.id.recyclerViewProductos);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Crear adapter directamente
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


                // Configurar las vistas con datos estáticos
                switch (position) {
                    case 0:
                        imageView.setImageResource(R.drawable.cercos);
                        nameView.setText("Cercos Prefabricados");
                        descriptionView.setText("Permite la visión desde afuera o desde adentro de una propiedad. Los muros prefabricados cuentan con variedad de placas de concreto con diferentes estéticos diseños, este producto aísla totalmente la propiedad, sin permitir la visibilidad.");
                        break;

                    case 1:
                        imageView.setImageResource(R.drawable.block);
                        nameView.setText("Block Concreto");
                        descriptionView.setText("Los Bloques de concreto son elementos modulares premoldeados diseñados para la albañilería confinada y armada");
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.murete);
                        nameView.setText("Murete de Concreto");
                        descriptionView.setText("Los Bloques de concreto son elementos modulares premoldeados diseñados para la albañilería confinada y armada");
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.poste);
                        nameView.setText("Poste de Concreto");
                        descriptionView.setText("Descripción del producto 4");
                        break;
                    // Puedes añadir más casos según necesites
                }
            }

            @Override
            public int getItemCount() {
                return 4; // Número de productos estáticos
            }
        };

        recyclerView.setAdapter(adapter);

        // Configurar botones
        ImageButton addProductButton = root.findViewById(R.id.add_product);
        addProductButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Activity_nuevoProducto.class);
            startActivity(intent);
        });

        ImageButton addServicio = root.findViewById(R.id.servicio);
        addServicio.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), nuevo_servicio.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}