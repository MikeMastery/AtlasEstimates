package com.example.atlasestimates.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlasestimates.AppDatabase;
import com.example.atlasestimates.CotizacionAdapter;
import com.example.atlasestimates.CotizacionViewModel;
import com.example.atlasestimates.R;
import com.example.atlasestimates.actividad_ajustes;
import com.example.atlasestimates.databinding.FragmentHomeBinding;
import com.example.atlasestimates.mostrardetalles;
import com.example.atlasestimates.nueva_cotizacion;
import com.example.atlasestimates.table_cotizacion;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private CotizacionAdapter adapter;
    private boolean isSearchBarVisible = false;
    private CotizacionViewModel cotizacionViewModel;
    private Button btnViewDetails ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cotizacionViewModel = new ViewModelProvider(requireActivity()).get(CotizacionViewModel.class);


        // Inicializar RecyclerView
        recyclerView = binding.recyclerViewCotizaciones;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crear adapter con lista vacía inicial
        adapter = new CotizacionAdapter(new ArrayList<>(), cotizacionViewModel, requireContext());
        recyclerView.setAdapter(adapter);

        // Observar cambios en las cotizaciones
        cotizacionViewModel.getCotizaciones().observe(getViewLifecycleOwner(), new Observer<List<table_cotizacion>>() {
            @Override
            public void onChanged(List<table_cotizacion> cotizaciones) {
                adapter.updateCotizaciones(cotizaciones);

            }
        });




        // Cargar datos de la base de datos loadCotizaciones();

        // Configurar el botón de nueva cotización
        setupNewQuoteButton(root);

        // Configurar la búsqueda
        setupSearch(root);

        setHasOptionsMenu(true);

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        // Recargar datos cuando el fragmento se vuelve visible
        cotizacionViewModel.loadCotizaciones(requireContext());
    }



    private void setupNewQuoteButton(View root) {
        ImageButton imageButton = root.findViewById(R.id.file_plus);
        TextView textView = root.findViewById(R.id.file_plus_text);

        View.OnClickListener clickListener = v -> {
            Intent intent = new Intent(getActivity(), nueva_cotizacion.class);
            startActivity(intent);
        };

        imageButton.setOnClickListener(clickListener);
        textView.setOnClickListener(clickListener);
    }

    private void setupSearch(View root) {
        ImageButton searchButton = root.findViewById(R.id.filter);
        TextView searchText = root.findViewById(R.id.search_text);
        EditText searchBar = root.findViewById(R.id.search_bar);

        View.OnClickListener showSearchBar = v -> toggleSearchBar();
        searchButton.setOnClickListener(showSearchBar);
        searchText.setOnClickListener(showSearchBar);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }



    private void toggleSearchBar() {
        isSearchBarVisible = !isSearchBarVisible;

        // Obtener la altura del EditText
        float displacement = getResources().getDimensionPixelSize(R.dimen.search_bar_height);

        // Animación para el EditText de búsqueda
        ObjectAnimator searchBarAnimation = ObjectAnimator.ofFloat(
                binding.searchBar,
                "alpha",
                isSearchBarVisible ? 0f : 1f,
                isSearchBarVisible ? 1f : 0f
        );

        // Animación para el FrameLayout del medio
        ObjectAnimator middleFrameAnimation = ObjectAnimator.ofFloat(
                binding.middleFrame,
                "translationY",
                isSearchBarVisible ? 0f : displacement,
                isSearchBarVisible ? displacement : 0f
        );

        // Animación para el RecyclerView
        ObjectAnimator recyclerViewAnimation = ObjectAnimator.ofFloat(
                binding.recyclerViewCotizaciones,
                "translationY",
                isSearchBarVisible ? 0f : displacement,
                isSearchBarVisible ? displacement : 0f
        );

        // Configurar el conjunto de animaciones
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(searchBarAnimation, middleFrameAnimation, recyclerViewAnimation);
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        // Manejar la visibilidad del EditText
        binding.searchBar.setVisibility(View.VISIBLE);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isSearchBarVisible) {
                    binding.searchBar.setVisibility(View.GONE);
                }
            }
        });

        animatorSet.start();
    }





    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_nav_menu, menu);
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