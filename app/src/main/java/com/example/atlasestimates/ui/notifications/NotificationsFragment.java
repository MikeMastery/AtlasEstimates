package com.example.atlasestimates.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlasestimates.Notificacion;
import com.example.atlasestimates.NotificacionesAdapter;
import com.example.atlasestimates.NotificationsViewModel;
import com.example.atlasestimates.R;
import com.example.atlasestimates.databinding.FragmentNotificationsBinding;

import java.util.List;

public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificacionesAdapter adapter;
    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtener ViewModel compartido entre la actividad y el fragmento
        notificationsViewModel = new ViewModelProvider(requireActivity()).get(NotificationsViewModel.class);

        // Configurar el RecyclerView
        recyclerView = binding.recyclerView;
        adapter = new NotificacionesAdapter();
        recyclerView.setAdapter(adapter);

        // Observar la lista de notificaciones
        notificationsViewModel.getListaNotificaciones().observe(getViewLifecycleOwner(),

                new Observer<List<Notificacion>>() {
            @Override
            public void onChanged(List<Notificacion> notificaciones) {
                if (notificaciones != null && !notificaciones.isEmpty()) {
                    adapter.setNotificaciones(notificaciones); // Actualizar el RecyclerView con las notificaciones
                }
            }
        });

        return root;
    }
}
