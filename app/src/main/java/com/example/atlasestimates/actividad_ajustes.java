package com.example.atlasestimates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

public class actividad_ajustes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_ajustes);

        // Configuración de los márgenes de la vista principal
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        // Configuración del botón de ajustes de privacidad
        Button privacySettingsButton = findViewById(R.id.privacy_settings);
        privacySettingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(actividad_ajustes.this, PrivacySettingsActivity.class);
            startActivity(intent);
        });
    }
}
