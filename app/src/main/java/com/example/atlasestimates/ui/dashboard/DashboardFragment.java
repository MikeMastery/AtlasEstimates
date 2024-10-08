package com.example.atlasestimates.ui.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
    private ProductoAdapter productoAdapter;
    private List<Producto> productos = new ArrayList<>();

    // Declarar el ActivityResultLauncher
    private ActivityResultLauncher<Intent> nuevoProductoLauncher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar RecyclerView y Adapter
        recyclerView = root.findViewById(R.id.recyclerViewProductos);
        productoAdapter = new ProductoAdapter(productos);
        recyclerView.setAdapter(productoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar el lanzador de actividad para obtener el resultado
        nuevoProductoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // Extraer los datos del producto
                            String nombre = data.getStringExtra("nombre");
                            String descripcion = data.getStringExtra("descripcion");
                            double precio = data.getDoubleExtra("precio", 0);
                            int stock = data.getIntExtra("stock", 0);
                            String imagenUrl = data.getStringExtra("imagenUrl");

                            // Crear el nuevo producto y agregarlo a la lista
                            Producto nuevoProducto = new Producto(nombre, descripcion, precio, stock, imagenUrl);
                            productos.add(nuevoProducto);

                            // Actualizar el RecyclerView
                            productoAdapter.notifyDataSetChanged();
                        }
                    }
                }
        );

        // Configuración del ImageButton para abrir el formulario de nuevo producto
        ImageButton addProductButton = root.findViewById(R.id.add_product);
        addProductButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Activity_nuevoProducto.class);
            nuevoProductoLauncher.launch(intent); // Iniciar la actividad
        });

        ImageButton addServicio = root.findViewById(R.id.servicio);
        addServicio.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), nuevo_servicio.class);
            nuevoProductoLauncher.launch(intent); // Iniciar la actividad
        });




        return root;
    }

}
