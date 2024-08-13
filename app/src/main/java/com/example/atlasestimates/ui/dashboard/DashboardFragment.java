package com.example.atlasestimates.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.atlasestimates.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Aquí puedes agregar cualquier configuración inicial
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout usando View Binding
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Aquí puedes inicializar tus componentes de UI, como RecyclerView, etc.
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


