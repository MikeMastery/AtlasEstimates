package com.example.atlasestimates;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_nuevoProducto extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText etImagen;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        etImagen = findViewById(R.id.etImagen);
        Button btnSelectImage = findViewById(R.id.btnSelectImage);
        Button btnGuardarProducto = findViewById(R.id.btnGuardarProducto);

        btnSelectImage.setOnClickListener(v -> openImageChooser());
        btnGuardarProducto.setOnClickListener(v -> guardarProducto());
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            etImagen.setText(imageUri.toString());
        }
    }

    private void guardarProducto() {
        String nombre = ((EditText) findViewById(R.id.etNombre)).getText().toString().trim();
        String descripcion = ((EditText) findViewById(R.id.etDescripcion)).getText().toString().trim();
        String precioStr = ((EditText) findViewById(R.id.etPrecio)).getText().toString().trim();
        String stockStr = ((EditText) findViewById(R.id.etStock)).getText().toString().trim();
        String imagenUrl = etImagen.getText().toString().trim();

        if (nombre.isEmpty() || descripcion.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio;
        int stock;
        try {
            precio = Double.parseDouble(precioStr);
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Precio y stock deben ser números válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("nombre", nombre);
        resultIntent.putExtra("descripcion", descripcion);
        resultIntent.putExtra("precio", precio);
        resultIntent.putExtra("stock", stock);
        resultIntent.putExtra("imagenUrl", imagenUrl);

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
