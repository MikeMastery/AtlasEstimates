package com.example.atlasestimates;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);

        // Inicializar vistas
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        btnNext = findViewById(R.id.btnNext);

        // Crear lista de slides
        List<IntroSlide> slides = new ArrayList<>();
        slides.add(new IntroSlide(
                "Bienvenido a AtlasEtimates, tu App de Cotizaciones",
                "Gestiona tus cotizaciones de manera fácil y profesional",
                R.drawable.logoatlas
        ));
        slides.add(new IntroSlide(
                "Crea Cotizaciones",
                "Genera cotizaciones personalizadas en segundos",
                R.drawable.logoatlas
        ));
        slides.add(new IntroSlide(
                "Comparte",
                "Envía tus cotizaciones por email o WhatsApp",
                R.drawable.logoatlas
        ));

        // Configurar adapter
        IntroSlideAdapter adapter = new IntroSlideAdapter(slides);
        viewPager.setAdapter(adapter);

        // Configurar TabLayout
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // No necesitamos hacer nada aquí, solo conectar el tab con el viewpager
        }).attach();

        // Configurar botón siguiente
        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() + 1 < adapter.getItemCount()) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                startMainActivity();
            }
        });

        // Cambiar texto del botón en último slide
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == adapter.getItemCount() - 1) {
                    btnNext.setText("Comenzar");
                } else {
                    btnNext.setText("Siguiente");
                }
            }
        });
    }

    private void startMainActivity() {
        // Guardar que ya se mostró la intro
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean("INTRO_SHOWN", true)
                .apply();

        // Ir al MainActivity
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}