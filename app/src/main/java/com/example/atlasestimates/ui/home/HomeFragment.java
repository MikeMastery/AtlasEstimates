package com.example.atlasestimates.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.atlasestimates.R;
import com.example.atlasestimates.actividad_ajustes;
import com.example.atlasestimates.databinding.FragmentHomeBinding;
import com.example.atlasestimates.nueva_cotizacion;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar el Spinner
        Spinner spinner = binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.opciones_spinner,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // No hacer nada al seleccionar un ítem
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acción cuando no se selecciona nada, si es necesario
            }
        });

        // Nuevo código agregado para el ImageButton
        ImageButton imageButton = root.findViewById(R.id.file_plus);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), nueva_cotizacion.class);
            startActivity(intent);
        });

        setHasOptionsMenu(true); // Indicar que este fragmento tiene un menú de opciones

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_nav_menu, menu); // Inflar el menú de la barra de acción
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            // Maneja el clic en el ítem de ajustes
            Intent intent = new Intent(getActivity(), actividad_ajustes.class); // Cambia AjustesActivity a la actividad que corresponda
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Indicar que este fragmento tiene un menú de opciones
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
