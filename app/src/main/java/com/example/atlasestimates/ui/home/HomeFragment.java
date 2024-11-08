package com.example.atlasestimates.ui.home;



import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlasestimates.AppDatabase;
import com.example.atlasestimates.CotizacionAdapter;
import com.example.atlasestimates.CotizacionDao;
import com.example.atlasestimates.CotizacionViewModel;
import com.example.atlasestimates.R;
import com.example.atlasestimates.actividad_ajustes;
import com.example.atlasestimates.databinding.FragmentHomeBinding;
import com.example.atlasestimates.nueva_cotizacion;
import com.example.atlasestimates.table_cotizacion;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private CotizacionAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerViewCotizaciones;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Cargar datos de la base de datos
        loadCotizaciones();

        // Configurar el Spinner
        Spinner spinner = binding.spinner;
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.opciones_spinner,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

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
        ImageButton imageButton = root.findViewById(R.id.file_plus);
        TextView textView = root.findViewById(R.id.file_plus_text);

        View.OnClickListener clickListener = v -> {
            Intent intent = new Intent(getActivity(), nueva_cotizacion.class);
            startActivity(intent);
        };

// Asigna el mismo listener al ImageButton y al TextView
        imageButton.setOnClickListener(clickListener);
        textView.setOnClickListener(clickListener);



        setHasOptionsMenu(true); // Indicar que este fragmento tiene un menú de opciones

        return root;
    }

    private void loadCotizaciones() {
        // Cargar datos en un hilo de fondo
        new AsyncTask<Void, Void, List<table_cotizacion>>() {
            @Override
            protected List<table_cotizacion> doInBackground(Void... voids) {
                // Obtener la instancia de la base de datos y cargar las cotizaciones
                AppDatabase db = AppDatabase.getInstance(getContext());
                return db.cotizacionDao().getAllCotizaciones();
            }

            @Override
            protected void onPostExecute(List<table_cotizacion> cotizaciones) {
                // Obtener la instancia del ViewModel
                CotizacionViewModel cotizacionViewModel = new ViewModelProvider(requireActivity()).get(CotizacionViewModel.class);

                // Configurar el adaptador con los datos y el CotizacionViewModel
                adapter = new CotizacionAdapter(cotizaciones, cotizacionViewModel);
                recyclerView.setAdapter(adapter);
            }
        }.execute();
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_nav_menu, menu); // Inflar el menú de la barra de acción
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), actividad_ajustes.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
