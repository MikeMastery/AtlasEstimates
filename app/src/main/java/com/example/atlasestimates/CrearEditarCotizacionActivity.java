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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CrearEditarCotizacionActivity extends AppCompatActivity {



    private CotizacionViewModel viewmodel;
    private Spinner spnTipoIdentificacion;
    private LinearLayout layoutDNI, layoutRUC;
    private static final int PICK_IMAGE = 1;
    private ImageView selectedImage;
    private ImageButton editImageButton;
    private Button buttonActualizar;
    private table_cotizacion cotizacion;
    private table_clientes clientes;// Añádelo como variable global.
    private EditText  Ed_fecha, Ed_titulo, Ed_ubicacion, Ed_descripcion, Ed_cliente, Ed_dni;


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
        Ed_titulo = findViewById(R.id.ed_titulo);
        buttonActualizar = findViewById(R.id.button_actualizarregistro);
        Ed_ubicacion = findViewById(R.id.ed_ubicacion);
        Ed_descripcion = findViewById(R.id.ed_descripcion);
        Ed_cliente = findViewById(R.id.ed_cliente);
        Ed_dni = findViewById(R.id.ed_dni);



        // Obtener el ID de la cotización desde el Intent
        int cotizacionID = getIntent().getIntExtra("cotizacionId", -1);


        viewmodel = new ViewModelProvider(this).get(CotizacionViewModel.class);


        obtenerDatosCotizacion(cotizacionID);
        obtenerDatosCliente(cotizacionID);



        editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cotizacion != null) {
                    String nuevoTitulo = Ed_titulo.getText().toString().trim();
                    String nuevaFecha = Ed_fecha.getText().toString().trim();
                    String nuevaUbicacion = Ed_ubicacion.getText().toString().trim();
                    String nuevaDescripcion = Ed_descripcion.getText().toString().trim();
                    if (!nuevoTitulo.isEmpty()) {
                        cotizacion.setTitulo(nuevoTitulo);
                        cotizacion.setFecha(nuevaFecha);
                        cotizacion.setUbicacion(nuevaUbicacion);
                        cotizacion.setDescripcion(nuevaDescripcion);
                        viewmodel.actualizarCotizacion(cotizacion);// Envia la actualización al ViewModel.
                if (clientes != null) {
                    String nuevoCliente = Ed_cliente.getText().toString().trim();
                    if(!nuevoCliente.isEmpty()){
                        clientes.setNombre_cliente(nuevoCliente);
                        viewmodel.actualizarCliente(clientes);
                    }
                        }
                        final Toast toast = Toast.makeText(CrearEditarCotizacionActivity.this, "Cotización actualizada", Toast.LENGTH_SHORT);
                        toast.show();

                        // Reducir la duración del Toast
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1500);  // 1500 ms (1.5 segundos)
                                    toast.cancel();  // Cancelar el Toast
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } else {
                        Toast.makeText(CrearEditarCotizacionActivity.this, "El título no puede estar vacío", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CrearEditarCotizacionActivity.this, "Error al cargar la cotización", Toast.LENGTH_SHORT).show();
                }
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
    private void obtenerDatosCotizacion(int cotizacionID) {
        viewmodel.getCotizacion(cotizacionID).observe(this, new Observer<table_cotizacion>() {
            @Override
            public void onChanged(table_cotizacion cotizacionResult) {
                if (cotizacionResult != null) {
                    cotizacion = cotizacionResult; // Asigna el resultado a la variable global.
                    Ed_titulo.setText(cotizacion.getTitulo());
                    Ed_fecha.setText(cotizacion.getFecha());
                    Ed_ubicacion.setText(cotizacion.getUbicacion());
                    Ed_descripcion.setText(cotizacion.getDescripcion());

                }
            }
        });
    }

    private void obtenerDatosCliente(int cotizacionId) {
        viewmodel.getCliente(cotizacionId).observe(this, new Observer<table_clientes>() {
            @Override
            public void onChanged(table_clientes cliente) {
                if (cliente != null) {
                    clientes = cliente;  // Guardar la instancia como variable global
                    Ed_cliente.setText(cliente.getNombre_cliente());
                }
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

