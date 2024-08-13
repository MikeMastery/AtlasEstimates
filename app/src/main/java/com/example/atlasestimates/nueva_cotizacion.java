package com.example.atlasestimates;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.atlasestimates.ui.home.HomeFragment;

import java.util.Calendar;

public class nueva_cotizacion extends AppCompatActivity {

    private EditText etFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nueva_cotizacion);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializa el ImageButton
        ImageButton imageButton = findViewById(R.id.sisuiente_coti);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, layout_2_cotiza.class);
            startActivity(intent);
        });



        // Inicializa el EditText
        etFecha = findViewById(R.id.fecha);
    }

    // Método para abrir el calendario
    public void AbrirCalendario(View view) {
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(nueva_cotizacion.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
                etFecha.setText(fecha);
            }
        }, anio, mes, dia);
        dpd.show();
    }
}
