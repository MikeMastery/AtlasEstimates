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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class nueva_cotizacion extends AppCompatActivity {

    private EditText etNombreCotizacion, etNombreCliente, etFecha;
    private EditText imageEditText;
    private ImageButton addImageButton;
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


// Método para guardar los datos en la base de datos
    /*private void guardarDatosCotizacion() {
        String nombreCotizacion = etNombreCotizacion.getText().toString();
        String nombreCliente = etNombreCliente.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String fecha = etFecha.getText().toString();

        // Validación básica
        if (nombreCotizacion.isEmpty() || nombreCliente.isEmpty() || descripcion.isEmpty() || fecha.isEmpty()) {
            // Aquí puedes mostrar un mensaje de error al usuario
            return;
        }

        // Inserta los datos en la base de datos
        dbHelper.insertCotizacion(nombreCotizacion, nombreCliente, descripcion, fecha, null, null, null);
    }
    */
}
