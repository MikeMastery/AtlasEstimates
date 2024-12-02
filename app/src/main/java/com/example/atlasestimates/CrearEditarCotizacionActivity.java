package com.example.atlasestimates;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CrearEditarCotizacionActivity extends AppCompatActivity {

    private Spinner spnTipoIdentificacion;
    private LinearLayout layoutDNI, layoutRUC;
    private static final int PICK_IMAGE = 1;
    private ImageView selectedImage;
    private ImageButton editImageButton;
    private EditText Ed_fecha;
    private ImageButton addImageButton;
    private TextView tv_dni, tv_ruc, tv_razonSocial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_cotizacion);

        spnTipoIdentificacion = findViewById(R.id.spn_tipo_identificacion);
        layoutDNI = findViewById(R.id.layout_dni_field);
        layoutRUC = findViewById(R.id.layout_ruc_fields);
        selectedImage = findViewById(R.id.selected_image);
        editImageButton = findViewById(R.id.edit_image_button);
        Ed_fecha = findViewById(R.id.editText_fecha);

        editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        // Configurar el Spinner con las opciones
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_identificacion, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoIdentificacion.setAdapter(adapter);

        // Manejar selección del Spinner
        spnTipoIdentificacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = parent.getItemAtPosition(position).toString();

                // Mostrar/Ocultar campos según selección
                if (seleccion.equals("DNI")) {
                    layoutDNI.setVisibility(View.VISIBLE);
                    layoutRUC.setVisibility(View.GONE);
                } else if (seleccion.equals("RUC")) {
                    layoutDNI.setVisibility(View.GONE);
                    layoutRUC.setVisibility(View.VISIBLE);
                } else {
                    layoutDNI.setVisibility(View.GONE);
                    layoutRUC.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });
    }

    // Método para abrir el calendario (fuera de onCreate)
    public void AbrirCalendario(View view) {
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(CrearEditarCotizacionActivity.this, (view1, year, month, dayOfMonth) -> {
            String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
            Ed_fecha.setText(fecha);
        }, anio, mes, dia);
        dpd.show();
    }
}

