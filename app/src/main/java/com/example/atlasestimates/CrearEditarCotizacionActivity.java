package com.example.atlasestimates;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CrearEditarCotizacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_cotizacion);

        ViewFlipper viewFlipper = findViewById(R.id.viewFlipper);

        ImageButton btnSiguiente = findViewById(R.id.sisuiente_coti);

        btnSiguiente.setOnClickListener(v -> {
            // Aquí defines la acción al presionar el botón
            Intent intent = new Intent(this, layout_2_cotiza.class);
            startActivity(intent);
        });


        btnSiguiente.setOnClickListener(v -> viewFlipper.showNext());



        // Obtener el Intent y el ID de la cotización (si existe)
        Intent intent = getIntent();
        int cotizacionId = intent.getIntExtra("cotizacionId", -1); // -1 indica creación

        if (cotizacionId != -1) {
            // Es edición: cargar los datos de la cotización existente
            cargarDatosParaEdicion(cotizacionId);
        } else {
            // Es creación: inicializar los campos vacíos
            inicializarDatosParaCreacion();
        }
    }

    private void cargarDatosParaEdicion(int cotizacionId) {
        AppDatabase db = AppDatabase.getInstance(this);

        TextView tituloCotizacion = findViewById(R.id.tvTituloCotizacion);  // Este es el TextView en el layout de creación
        tituloCotizacion.setText("Editando Cotización");

        new AsyncTask<Void, Void, table_cotizacion>() {
            @Override
            protected table_cotizacion doInBackground(Void... voids) {
                return db.cotizacionDao().getCotizacionById(cotizacionId).getValue();
            }

            @Override
            protected void onPostExecute(table_cotizacion cotizacion) {
                if (cotizacion != null) {
                    // Rellena los campos del primer layout
                    EditText titulo = findViewById(R.id.namecoti);
                    EditText fecha = findViewById(R.id.fecha);
                    titulo.setText(cotizacion.getTitulo());
                    fecha.setText(cotizacion.getFecha());
                }
            }
        }.execute();
    }

    private void inicializarDatosParaCreacion() {
        // Inicializa los campos con valores predeterminados o vacíos
        // Por ejemplo, podrías establecer un campo de texto en vacío:
        // EditText titulo = findViewById(R.id.tituloEditText);
        // titulo.setText("");
    }
}
