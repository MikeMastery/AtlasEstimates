package com.example.atlasestimates;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class layout_2_cotiza extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private EditText imageEditText;
    private ImageButton addImageButton;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_layout2_cotiza);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nuevo código agregado para el ImageButton
        ImageButton imageButtonatras1 = findViewById(R.id.atras_coti1);
        imageButtonatras1.setOnClickListener(v -> {
            Intent intent = new Intent(layout_2_cotiza.this, nueva_cotizacion.class);
            startActivity(intent);
        });


        // Inicializar el Spinner y los EditText
        spinner = findViewById(R.id.spinner2);
        editText = findViewById(R.id.editText2);
        imageEditText = findViewById(R.id.imageEditText);
        addImageButton = findViewById(R.id.add_image);

        // Crear el adaptador para el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_productos,
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Configurar el listener para el Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                editText.setText(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No es necesario hacer nada aquí
            }
        });

        // Configurar el listener para el botón de agregar imagen
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
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
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);

                // Ajustar el tamaño de la imagen para que no se distorsione y ocupe el espacio adecuado
                int drawableWidth = drawable.getIntrinsicWidth();
                int drawableHeight = drawable.getIntrinsicHeight();
                int editTextWidth = imageEditText.getWidth();
                int editTextHeight = imageEditText.getHeight();

                // Calcular el nuevo tamaño manteniendo la proporción
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

                // Colocar la imagen en el lado derecho del EditText
                imageEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, scaledDrawable, null);
                imageEditText.setHint(""); // Elimina el hint una vez que se ha seleccionado una imagen
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
