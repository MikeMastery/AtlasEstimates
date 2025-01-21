package com.example.atlasestimates.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlasestimates.AppDatabase;
import com.example.atlasestimates.CotizacionDao;
import com.example.atlasestimates.R;
import com.example.atlasestimates.databinding.FragmentNotificationsBinding;
import com.example.atlasestimates.table_cotizacion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;
    private RecyclerView recyclerView;
    private NotificacionAdapter adapter;
    private CotizacionDao cotizacionDao;  // Tu DAO existente de cotizaciones


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar RecyclerView
        recyclerView = root.findViewById(R.id.recyclerViewNotificaciones);
        adapter = new NotificacionAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar DAO
        cotizacionDao = AppDatabase.getInstance(requireContext()).cotizacionDao();

        // Cargar cotizaciones
        cargarNotificaciones();

        return root;
    }

        private void cargarNotificaciones () {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                // Asumiendo que tienes un método para obtener todas las cotizaciones ordenadas por fecha
                List<table_cotizacion> cotizaciones = cotizacionDao.getAllCotizacionesOrderByDate();

                // Actualizar UI en el hilo principal
                requireActivity().runOnUiThread(() -> {
                    adapter.setCotizaciones(cotizaciones);
                    adapter.notifyDataSetChanged();
                });
            });
        }

        @Override
        public void onResume () {
            super.onResume();
            cargarNotificaciones(); // Recargar cuando el fragmento se hace visible
        }
    }
