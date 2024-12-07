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

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CrearEditarCotizacionActivity extends AppCompatActivity {

    private Uri selectedImageUri;
    private String imagePath = null;
    private CotizacionViewModel viewmodel;
    private Spinner spnTipoIdentificacion;
    private LinearLayout layoutDNI, layoutRUC;
    private static final int PICK_IMAGE = 1;
    private ImageView selectedImage;
    private ImageButton editImageButton;
    private Button buttonActualizar;
    private table_cotizacion cotizacion;
    private table_categoria categoria;
    private table_clientes clientes;// Añádelo como variable global.
    private EditText Ed_fecha, Ed_titulo, Ed_ubicacion, Ed_descripcion, Ed_cliente, Ed_dni, Comentario_Costos;


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
        Comentario_Costos = findViewById(R.id.ed_comentario_costos);



        // Obtener el ID de la cotización desde el Intent
        int cotizacionID = getIntent().getIntExtra("cotizacionId", -1);


        viewmodel = new ViewModelProvider(this).get(CotizacionViewModel.class);


        obtenerDatosCotizacion(cotizacionID);
        obtenerDatosCliente(cotizacionID);
        cargarImagenDesdeBD(cotizacionID);
        obtenerCategoria(cotizacionID);

        editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la galería para seleccionar imagen
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cotizacion != null && clientes != null && categoria != null) {
                    // Actualizar datos de la cotización
                    String nuevoTitulo = Ed_titulo.getText().toString().trim();
                    String nuevaFecha = Ed_fecha.getText().toString().trim();
                    String nuevaUbicacion = Ed_ubicacion.getText().toString().trim();
                    String nuevaDescripcion = Ed_descripcion.getText().toString().trim();

                    // Obtener la selección actual del spinner
                    String tipoIdentificacion = spnTipoIdentificacion.getSelectedItem().toString();

                    if (!nuevoTitulo.isEmpty()) {
                        // Actualizar datos de la cotización
                        cotizacion.setTitulo(nuevoTitulo);
                        cotizacion.setFecha(nuevaFecha);
                        cotizacion.setUbicacion(nuevaUbicacion);
                        cotizacion.setDescripcion(nuevaDescripcion);

                        // Actualizar la imagen si se ha seleccionado una nueva
                        // Actualizar la imagen si se ha seleccionado una nueva
                        if (imagePath != null) {
                            cotizacion.setImagen(imagePath);

                        }
                        viewmodel.actualizarCotizacion(cotizacion);

                        // Actualizar datos del cliente
                        String nuevoCliente = Ed_cliente.getText().toString().trim();
                        if (!nuevoCliente.isEmpty()) {
                            clientes.setNombre_cliente(nuevoCliente);

                            // Actualizar según el tipo de identificación
                            if (tipoIdentificacion.equals("DNI")) {
                                String nuevoDNI = Ed_dni.getText().toString().trim();
                                if (nuevoDNI.length() == 8) {
                                    clientes.setDni_ruc(nuevoDNI);
                                } else {
                                    Toast.makeText(CrearEditarCotizacionActivity.this,
                                            "El DNI debe tener 8 dígitos",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else if (tipoIdentificacion.equals("RUC")) {
                                // Campos para RUC (asumiendo que tienes EditText para RUC y Razón Social)
                                EditText edRuc = findViewById(R.id.ed_ruc);
                                EditText edRazonSocial = findViewById(R.id.ed_razonsocial);

                                String nuevoRUC = edRuc.getText().toString().trim();
                                String nuevaRazonSocial = edRazonSocial.getText().toString().trim();

                                if (nuevoRUC.length() == 11) {
                                    clientes.setDni_ruc(nuevoRUC);
                                    // Aquí deberías tener un método para setear la razón social si es necesario
                                    clientes.setRazon_social(nuevaRazonSocial);
                                } else {
                                    Toast.makeText(CrearEditarCotizacionActivity.this,
                                            "El RUC debe tener 11 dígitos",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            // Actualizar cliente
                            viewmodel.actualizarCliente(clientes);

                            String nuevaCategoria = Comentario_Costos.getText().toString().trim();
                            if (!nuevaCategoria.isEmpty()) {
                                // Actualizar datos de la cotización
                                categoria.setDescripcion_categoria(nuevaCategoria);

                            }
                                viewmodel.actualizarCategoria(categoria);
                        }


                        // Mostrar mensaje de éxito
                        Toast.makeText(CrearEditarCotizacionActivity.this,
                                "Datos actualizados correctamente",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CrearEditarCotizacionActivity.this,
                                "El título no puede estar vacío",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CrearEditarCotizacionActivity.this,
                            "Error al cargar los datos",
                            Toast.LENGTH_SHORT).show();
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

                    // Obtener el número de identificación
                    String identificacion = cliente.getDni_ruc();

                    if (identificacion != null) {
                        // Ocultar primero todos los layouts
                        layoutDNI.setVisibility(View.GONE);
                        layoutRUC.setVisibility(View.GONE);

                        // Determinar el tipo según la longitud del número
                        if (identificacion.length() == 8) {
                            // Es DNI
                            layoutDNI.setVisibility(View.VISIBLE);
                            Ed_dni.setText(identificacion);

                            // Establecer el spinner en DNI automáticamente
                            for (int i = 0; i < spnTipoIdentificacion.getAdapter().getCount(); i++) {
                                if (spnTipoIdentificacion.getAdapter().getItem(i).toString().equals("DNI")) {
                                    spnTipoIdentificacion.setSelection(i);
                                    break;
                                }
                            }
                        } else if (identificacion.length() == 11) {
                            // Es RUC
                            layoutRUC.setVisibility(View.VISIBLE);

                            // Establecer el spinner en RUC automáticamente
                            for (int i = 0; i < spnTipoIdentificacion.getAdapter().getCount(); i++) {
                                if (spnTipoIdentificacion.getAdapter().getItem(i).toString().equals("RUC")) {
                                    spnTipoIdentificacion.setSelection(i);
                                    break;
                                }
                            }

                            // Aquí debes añadir los campos para RUC y Razón Social
                            // Ejemplo (asumiendo que tienes estos EditText en tu layout):
                            EditText edRuc = findViewById(R.id.ed_ruc);
                            EditText edRazonSocial = findViewById(R.id.ed_razonsocial);

                            edRuc.setText(identificacion);
                            edRazonSocial.setText(cliente.getRazon_social());
                        } else {
                            // Si el número no tiene 8 o 11 dígitos
                            layoutDNI.setVisibility(View.GONE);
                            layoutRUC.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
    }

    private void obtenerCategoria(int cotizacionId) {
        viewmodel.getCategoria(cotizacionId).observe(this, new Observer<table_categoria>() {
            @Override
            public void onChanged(table_categoria categoriaResult) {
                if (categoriaResult != null) { // Verifica categoriaResult, no categoria
                    categoria = categoriaResult; // Asigna el valor a la variable global
                    Comentario_Costos.setText(categoria.getDescripcion_categoria()); // Usa el valor correctamente
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            try {
                // Obtener la URI de la imagen seleccionada
                selectedImageUri = data.getData();

                // Cargar y redimensionar la imagen
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);

                // Redimensionar imagen (similar a tu método existente)
                int originalWidth = bitmap.getWidth();
                int originalHeight = bitmap.getHeight();
                int resizedWidth;
                int resizedHeight;

                if (originalHeight > originalWidth) {
                    int desiredHeight = 535;
                    resizedHeight = desiredHeight;
                    resizedWidth = (int) ((float) originalWidth * ((float) desiredHeight / originalHeight));
                } else {
                    int desiredWidth = 550;
                    resizedWidth = desiredWidth;
                    resizedHeight = (int) ((float) originalHeight * ((float) desiredWidth / originalWidth));
                }

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, true);

                // Mostrar imagen en ImageView
                selectedImage.setImageBitmap(resizedBitmap);

                // Guardar la imagen y obtener la ruta del archivo
                imagePath = saveImage(resizedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método para guardar la imagen (debes implementarlo)
    private String saveImage(Bitmap bitmap) {
        // Implementa la lógica para guardar la imagen
        // Retorna la ruta donde se guardó la imagen
        File imageFile = new File(getExternalFilesDir(null), "cotizacion_" + System.currentTimeMillis() + ".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

    // Método para cargar la imagen desde la base de datos
    private void cargarImagenDesdeBD(int cotizacionID) {
        viewmodel.getCotizacion(cotizacionID).observe(this, new Observer<table_cotizacion>() {
            @Override
            public void onChanged(table_cotizacion cotizacionResult) {
                if (cotizacionResult != null && cotizacionResult.getImagen() != null) {
                    String imagePath = cotizacionResult.getImagen();
                    File imageFile = new File(imagePath);

                    if (imageFile.exists()) {
                        Glide.with(CrearEditarCotizacionActivity.this)
                                .load(imageFile)
                                .placeholder(R.drawable.maquinaria)  // Imagen temporal mientras carga
                                .error(R.drawable.logo)        // Imagen en caso de error
                                .into(selectedImage);
                    } else {
                        Toast.makeText(CrearEditarCotizacionActivity.this,
                                "La imagen no existe en la ruta especificada",
                                Toast.LENGTH_SHORT).show();
                        selectedImage.setImageResource(R.drawable.logo); // Imagen de error
                    }
                }
            }
        });
    }
}