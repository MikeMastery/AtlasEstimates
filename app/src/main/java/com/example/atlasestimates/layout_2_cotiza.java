package com.example.atlasestimates;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class layout_2_cotiza extends AppCompatActivity {

    private Spinner spinnerCategoria;
    private Spinner spinnerProducto;
    private TextView tvSeleccionarProducto;
    private EditText etDescripcion, etMl, etPrecio, etHorasMaquina, etPrecioHora;
    private TextView tvMl, tvPrecio, tvHorasMaquina, tvPrecioHora;
    private Button btnGuardar;
    private ImageButton btnAtras;
    private EditText editex_categoria;
    private Cotizacion cotizacion;
    private EditText edittext_producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout2_cotiza);

        // Inicializar la clase Cotizacion
        cotizacion = CotizacionManager.getInstance().getCotizacion();

        // Inicializar vistas
        spinnerCategoria = findViewById(R.id.spinner1);
        spinnerProducto = findViewById(R.id.spinner2);
        tvSeleccionarProducto = findViewById(R.id.tvseleccionar_prodcuto);
        etDescripcion = findViewById(R.id.descripcion);
        etMl = findViewById(R.id.et_ml);
        etPrecio = findViewById(R.id.ed_precio);
        etHorasMaquina = findViewById(R.id.et_hm);
        etPrecioHora = findViewById(R.id.ed_precio_hm);
        tvMl = findViewById(R.id.tv_ml);
        editex_categoria = findViewById(R.id.editText1);
        edittext_producto = findViewById(R.id.editText2);
        tvPrecio = findViewById(R.id.tex_precio);
        tvHorasMaquina = findViewById(R.id.tv_hm);
        tvPrecioHora = findViewById(R.id.tex_precio_hm);
        btnGuardar = findViewById(R.id.GuardarButton);
        btnAtras = findViewById(R.id.atras_coti1);


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
                    hideAllFields();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Configurar TextWatcher para cálculos
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                calculateResult();
            }
        };

        etMl.addTextChangedListener(textWatcher);
        etPrecio.addTextChangedListener(textWatcher);
        etHorasMaquina.addTextChangedListener(textWatcher);
        etPrecioHora.addTextChangedListener(textWatcher);

        // Configurar botones
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCotizacion();
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Vuelve a la actividad anterior
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
                cotizacion.setProducto(selectedProduct);
                updateUnidadMedida(selectedProduct);
                edittext_producto.setText(selectedProduct);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateUnidadMedida(String product) {
        if ("Cercos prefabricados".equals(product) || "Cerco cabeza caballo".equals(product)) {
            showMetrosLinealesFields();
            hideHorasMaquinaFields();
        } else if ("Block de concreto".equals(product) || "Poste de concreto".equals(product)) {
            showHorasMaquinaFields();
            hideMetrosLinealesFields();
        } else {
            hideAllFields();
        }
    }

    private void showMetrosLinealesFields() {
        etMl.setVisibility(View.VISIBLE);
        etPrecio.setVisibility(View.VISIBLE);
        tvMl.setVisibility(View.VISIBLE);
        tvPrecio.setVisibility(View.VISIBLE);
    }

    private void hideMetrosLinealesFields() {
        etMl.setVisibility(View.GONE);
        etPrecio.setVisibility(View.GONE);
        tvMl.setVisibility(View.GONE);
        tvPrecio.setVisibility(View.GONE);
    }

    private void showHorasMaquinaFields() {
        etHorasMaquina.setVisibility(View.VISIBLE);
        etPrecioHora.setVisibility(View.VISIBLE);
        tvHorasMaquina.setVisibility(View.VISIBLE);
        tvPrecioHora.setVisibility(View.VISIBLE);
    }

    private void hideHorasMaquinaFields() {
        etHorasMaquina.setVisibility(View.GONE);
        etPrecioHora.setVisibility(View.GONE);
        tvHorasMaquina.setVisibility(View.GONE);
        tvPrecioHora.setVisibility(View.GONE);
    }

    private void hideAllFields() {
        hideMetrosLinealesFields();
        hideHorasMaquinaFields();
    }

    private void calculateResult() {
        try {
            double cantidad, precio, subtotal, igv, total;

            if (etMl.getVisibility() == View.VISIBLE) {
                cantidad = Double.parseDouble(etMl.getText().toString());
                precio = Double.parseDouble(etPrecio.getText().toString());
                cotizacion.setMetrosLineales(String.valueOf(cantidad));
                cotizacion.setPrecio(String.valueOf(precio));
            } else if (etHorasMaquina.getVisibility() == View.VISIBLE) {
                cantidad = Double.parseDouble(etHorasMaquina.getText().toString());
                precio = Double.parseDouble(etPrecioHora.getText().toString());
                cotizacion.setHorasMaquina(String.valueOf(cantidad));
                cotizacion.setPrecioHora(String.valueOf(precio));
            } else {
                return; // No hay campos visibles para calcular
            }

            subtotal = cantidad * precio;
            igv = subtotal * 0.18; // 18% de IGV
            total = subtotal + igv;

            cotizacion.setSubtotal(String.format("%.2f", subtotal));
            cotizacion.setIgv(String.format("%.2f", igv));
            cotizacion.setTotal(String.format("%.2f", total));


        } catch (NumberFormatException e) {
            // Manejar el error si los campos están vacíos o no son números válidos
            cotizacion.setSubtotal("0.0");
            cotizacion.setIgv("0.0");
            cotizacion.setTotal("0.0");
            cotizacion.setMetrosLineales("0");
            cotizacion.setPrecio("0");
        }
    }

    private void guardarCotizacion() {
        cotizacion.setDescripcion(etDescripcion.getText().toString());
        cotizacion.setCategoria(editex_categoria.getText().toString());

        // Iniciar la actividad layout_mostrar_cotización
        Intent intent = new Intent(this,Activity_mostrar_cotizacon.class);
        intent.putExtra("cotizacion", cotizacion);
        startActivity(intent);
    }
}