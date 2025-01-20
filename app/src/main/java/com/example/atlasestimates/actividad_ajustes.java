package com.example.atlasestimates;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        // En tu Activity
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            TextView versionTextView = findViewById(R.id.version_textview);
            versionTextView.setText("Versión " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Configuración del botón de ajustes de privacidad
        Button privacySettingsButton = findViewById(R.id.privacy_settings);
        privacySettingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(actividad_ajustes.this, PrivacySettingsActivity.class);
            startActivity(intent);
        });
    }
}
