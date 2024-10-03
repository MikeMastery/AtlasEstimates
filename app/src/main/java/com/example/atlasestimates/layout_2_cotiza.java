package com.example.atlasestimates;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
;

public class layout_2_cotiza extends AppCompatActivity {

    private Spinner spinnerCategoria;
    private Spinner spinnerProducto;
    private TextView tvSeleccionarProducto;
    private EditText etMl;
    private EditText etPrecio;
    private TextView tvMl;
    private TextView tvPrecio;
    private EditText editex_categoria;
    private EditText edittext_producto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout2_cotiza);

        // Inicializar vistas
        spinnerCategoria = findViewById(R.id.spinner1);
        spinnerProducto = findViewById(R.id.spinner2);
        tvSeleccionarProducto = findViewById(R.id.tvseleccionar_prodcuto);
        etMl = findViewById(R.id.et_ml);
        etPrecio = findViewById(R.id.ed_precio);
        tvMl = findViewById(R.id.tv_ml);
        tvPrecio = findViewById(R.id.tex_precio);
        editex_categoria = findViewById(R.id.editText1);
        edittext_producto = findViewById(R.id.editText2);


        // Configurar Spinner de Categoría
        ArrayAdapter<CharSequence> adapterCategoria = ArrayAdapter.createFromResource(this,
                R.array.spinner_categorias, android.R.layout.simple_spinner_item);
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategoria);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategoria = parent.getItemAtPosition(position).toString();

                // Mostrar la selección en editText1
                editex_categoria.setText(selectedCategoria);

                if ("Productos".equals(selectedCategoria)) {
                    tvSeleccionarProducto.setText("Seleccionar Producto");
                    configureProductSpinner();
                } else {
                    // Restablecer a estado predeterminado o manejar otras categorías
                    tvSeleccionarProducto.setText("Seleccionar");
                    spinnerProducto.setAdapter(null);
                    hideAdditionalFields();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void configureProductSpinner() {
        ArrayAdapter<CharSequence> adapterProducto = ArrayAdapter.createFromResource(this,
                R.array.spinner_productos, android.R.layout.simple_spinner_item);
        adapterProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProducto.setAdapter(adapterProducto);

        spinnerProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProduct = parent.getItemAtPosition(position).toString();
                updateUnidadMedida(selectedProduct);

                edittext_producto.setText(selectedProduct);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateUnidadMedida(String product) {
        TextView tvUnidadMedida = findViewById(R.id.tvUnidadMedida);
        if ("Cercos prefabricados".equals(product) || "Cerco cabeza caballo".equals(product)) {
            tvUnidadMedida.setText("ML (metros lineales)");
            showAdditionalFields();
        } else if ("Block de concreto".equals(product) || "Poste de concreto".equals(product)) {
            tvUnidadMedida.setText("UMD (unidades)");
            hideAdditionalFields();
        } else {
            tvUnidadMedida.setText("No especificada");
            hideAdditionalFields();
        }
    }

    private void showAdditionalFields() {
        etMl.setVisibility(View.VISIBLE);
        etPrecio.setVisibility(View.VISIBLE);
        tvMl.setVisibility(View.VISIBLE);
        tvPrecio.setVisibility(View.VISIBLE);
    }

    private void hideAdditionalFields() {
        etMl.setVisibility(View.GONE);
        etPrecio.setVisibility(View.GONE);
        tvMl.setVisibility(View.GONE);
        tvPrecio.setVisibility(View.GONE);
    }
}