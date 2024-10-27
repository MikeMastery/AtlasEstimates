package com.example.atlasestimates;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atlasestimates.databinding.FragmentHomeBinding;

public class nuevo_servicio extends AppCompatActivity {

    private FragmentHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_servicio);

        // Configuración del spinner con el array de opciones llamado 'servicios'
        Spinner spinner = binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_servicios, // Nombre del array
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Listener para manejar la selección de ítems en el spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Acción al seleccionar un ítem (aquí no se hace nada)
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acción cuando no se selecciona nada
            }
        });
    }
}
