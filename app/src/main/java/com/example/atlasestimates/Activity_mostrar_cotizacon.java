package com.example.atlasestimates;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;

public class Activity_mostrar_cotizacon extends AppCompatActivity {

    private TextView textViewTitulo;
    private TextView textviewCliente;
    private TextView textviewFecha;
    private TextView textviewRequerimiento;
    private EditText editextImagen;
    private TextView textviewUNmedida;
    private TextView textviewDescripcion;
    private TextView texviewPrecio;
    private TextView textviewTotal;
    private TextView textviewTotalIGV;
    private TextView textviewSubTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cotizacon);

        textViewTitulo = findViewById(R.id.textViewTitulo);
        textviewCliente = findViewById(R.id.nombre_cliente);
        textviewFecha = findViewById(R.id.fecha_coti);
        textviewRequerimiento = findViewById(R.id.producto);
        editextImagen = findViewById(R.id.imagen_cotizacion);
        textviewUNmedida = findViewById(R.id.un_medida);
        textviewDescripcion = findViewById(R.id.descripcion_cotizacion);
        texviewPrecio = findViewById(R.id.mostrar_precio);
        textviewTotal = findViewById(R.id.ed_total);
        textviewTotalIGV =findViewById(R.id.mostrar_igv);
        textviewSubTotal = findViewById(R.id.ed_SubTotal);

        Cotizacion cotizacion = (Cotizacion) getIntent().getSerializableExtra("cotizacion");
        if (cotizacion != null) {
            textViewTitulo.setText(cotizacion.getNombreCotizacion());
            textviewCliente.setText(cotizacion.getNombreCliente());
            textviewFecha.setText(cotizacion.getFecha());
            textviewRequerimiento.setText(cotizacion.getProducto());
            textviewUNmedida.setText(cotizacion.getMetrosLineales());
            textviewDescripcion.setText(cotizacion.getDescripcion());
            texviewPrecio.setText(cotizacion.getPrecio());
            textviewTotal.setText(cotizacion.getTotal());
            textviewTotalIGV.setText(cotizacion.getIgv());
            textviewSubTotal.setText(cotizacion.getSubTotal());

            String imageUriString = cotizacion.getImagenUri();
            if (imageUriString != null) {
                Uri imageUri = Uri.parse(imageUriString);
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                    // Escalar el bitmap para que se ajuste dentro de los límites del EditText
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int targetWidth = 600; // Ancho del EditText en dp
                    int targetHeight = 500; // Altura del EditText en dp

                    if (width > targetWidth || height > targetHeight) {
                        float aspectRatio = (float) width / height;
                        if (aspectRatio > 1) {
                            // Imagen más ancha que alta
                            width = targetWidth;
                            height = (int) (width / aspectRatio);
                        } else {
                            // Imagen más alta que ancha
                            height = targetHeight;
                            width = (int) (height * aspectRatio);
                        }
                    }

                    bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                editextImagen.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                editextImagen.setHint(""); // Ocultar el texto del hint
            }
        }
    }
}
