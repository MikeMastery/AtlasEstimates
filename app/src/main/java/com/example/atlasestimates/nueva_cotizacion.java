package com.example.atlasestimates;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class nueva_cotizacion extends AppCompatActivity {

    private EditText etNombreCotizacion, etNombreCliente, etFecha, et_camposDNI, et_camposRUC, etcamposRazonSocial, etIdentificacion, et_Ubicacion;
    private EditText imageEditText;
    private ImageButton addImageButton;
    private TextView tv_dni, tv_ruc, tv_razonSocial;
    private Spinner spinnerIdentificacion;

    private Bitmap selectedImageBitmap;
    private Uri imageUri;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cotizacion);

        etNombreCotizacion = findViewById(R.id.namecoti);
        etNombreCliente = findViewById(R.id.cliente);
        etFecha = findViewById(R.id.fecha);
        imageEditText = findViewById(R.id.imageEditText);
        addImageButton = findViewById(R.id.add_image);
        et_camposDNI = findViewById(R.id.ed_iden_dni);
        et_camposRUC = findViewById(R.id.ed_iden_ruc);
        etcamposRazonSocial = findViewById(R.id.razon_social);
        etIdentificacion = findViewById(R.id.ed_identificacion);
        tv_dni = findViewById(R.id.textviewDNI);
        tv_ruc = findViewById(R.id.textviewRUC);
        tv_razonSocial = findViewById(R.id.textviewRAZON);
        spinnerIdentificacion = findViewById(R.id.spinner_identificacion);
        et_Ubicacion = findViewById(R.id.ubicacion);


        // Configurar el Spinner con el ArrayAdapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.opciones_identificacion, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdentificacion.setAdapter(adapter);

        // Configura el listener para el Spinner
        spinnerIdentificacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Verifica el ítem seleccionado en el Spinner
                String selectedOption = parentView.getItemAtPosition(position).toString();
                etIdentificacion.setText(selectedOption);

                // Ocultar todos los campos primero
                tv_dni.setVisibility(View.GONE);
                et_camposDNI.setVisibility(View.GONE);
                tv_ruc.setVisibility(View.GONE);
                et_camposRUC.setVisibility(View.GONE);
                tv_razonSocial.setVisibility(View.GONE);
                etcamposRazonSocial.setVisibility(View.GONE);

                // Luego, dependiendo de la opción, muestra los campos correspondientes
                if (selectedOption.equals("DNI")) {
                    tv_dni.setVisibility(View.VISIBLE);
                    et_camposDNI.setVisibility(View.VISIBLE);
                } else if (selectedOption.equals("RUC")) {
                    tv_ruc.setVisibility(View.VISIBLE);
                    et_camposRUC.setVisibility(View.VISIBLE);
                    tv_razonSocial.setVisibility(View.VISIBLE);
                    etcamposRazonSocial.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Opcional: ocultar todos los campos si no se selecciona nada
            }
        });



        ImageButton imageButton = findViewById(R.id.sisuiente_coti);
        imageButton.setOnClickListener(v -> {
            guardarDatosPrimeraParte();
            Intent intent = new Intent(this, layout_2_cotiza.class);
            startActivity(intent);
        });

        addImageButton.setOnClickListener(v -> openGallery());
    }

    private void guardarDatosPrimeraParte() {
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setNombreCotizacion(etNombreCotizacion.getText().toString());
        cotizacion.setNombreCliente(etNombreCliente.getText().toString());
        cotizacion.setFecha(etFecha.getText().toString());
        cotizacion.setIdentificacion(etIdentificacion.getText().toString());
        cotizacion.setUbicacion(et_Ubicacion.getText().toString());
        cotizacion.setDni(et_camposDNI.getText().toString());
        cotizacion.setRuc(et_camposRUC.getText().toString());
        cotizacion.setRazonSocial(etcamposRazonSocial.getText().toString());


        if (imageUri != null) {
            cotizacion.setImagenUri(imageUri.toString()); // Usar URI como String
        }
        CotizacionManager.getInstance().setCotizacion(cotizacion);
    }


    public void AbrirCalendario(View view) {
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(nueva_cotizacion.this, (view1, year, month, dayOfMonth) -> {
            String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
            etFecha.setText(fecha);
        }, anio, mes, dia);
        dpd.show();
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            this.imageUri = imageUri;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                selectedImageBitmap = bitmap;

                Drawable drawable = new BitmapDrawable(getResources(), bitmap);

                int drawableWidth = drawable.getIntrinsicWidth();
                int drawableHeight = drawable.getIntrinsicHeight();
                int editTextWidth = imageEditText.getWidth();
                int editTextHeight = imageEditText.getHeight();

                int newWidth = drawableWidth;
                int newHeight = drawableHeight;
                if (drawableWidth > editTextWidth / 2) {
                    newWidth = editTextWidth / 2;
                    newHeight = (newWidth * drawableHeight) / drawableWidth;
                }
                if (newHeight > editTextHeight - 30) {
                    newHeight = editTextHeight - 30;
                    newWidth = (newHeight * drawableWidth) / drawableHeight;
                }

                Drawable scaledDrawable = scaleDrawable(drawable, newWidth, newHeight);

                imageEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, scaledDrawable, null);
                imageEditText.setHint("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Drawable scaleDrawable(Drawable drawable, int width, int height) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        return new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, width, height, true));
    }

}
