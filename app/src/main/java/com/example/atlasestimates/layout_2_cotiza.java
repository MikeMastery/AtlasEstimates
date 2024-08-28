package com.example.atlasestimates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class layout_2_cotiza extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private TextView tvML;
    private EditText etML;
    private Cotizacion cotizacion;
    private TextView tvPrecio;
    private ProductManager productManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout2_cotiza);

        // Inicialización de vistas
        spinner = findViewById(R.id.spinner2);
        editText = findViewById(R.id.editText2);
        tvML = findViewById(R.id.tv_ml);
        etML = findViewById(R.id.et_ml);
        tvPrecio = findViewById(R.id.tvPrecio);

        // Inicialización de objetos
        cotizacion = CotizacionManager.getInstance().getCotizacion();
        productManager = new ProductManager();

        ImageButton imageButtonatras1 = findViewById(R.id.atras_coti1);
        imageButtonatras1.setOnClickListener(v -> {
            Intent intent = new Intent(layout_2_cotiza.this, nueva_cotizacion.class);
            startActivity(intent);
        });

        // Configuración del Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_productos,
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProduct = parent.getItemAtPosition(position).toString();
                editText.setText(selectedProduct);

                // Obtener y mostrar el precio
                Double price = productManager.getPrice(selectedProduct);
                String formattedPrice = String.format(Locale.getDefault(), "S/ %.2f", price);
                tvPrecio.setText(formattedPrice);

                // Actualizar la cotización
                cotizacion.setProducto(selectedProduct);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                editText.setText("");
                tvPrecio.setText("");
            }
        });

        // Configurar el EditText para abrir el spinner al hacer clic
        editText.setOnClickListener(v -> spinner.performClick());

        // Configuración de CheckBoxes
        CheckBox checkboxML1 = findViewById(R.id.checkbox_ml);
        CheckBox checkboxHM2 = findViewById(R.id.checkbox_hm);
        CheckBox checkboxGLOBAL3 = findViewById(R.id.checkbox_global);
        CheckBox checkboxUN4 = findViewById(R.id.checkbox_unidades);
        CheckBox checkboxPL5 = findViewById(R.id.checkbox_planos);

        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            if (isChecked) {
                // Deseleccionar los demás checkboxes
                if (buttonView != checkboxML1) checkboxML1.setChecked(false);
                if (buttonView != checkboxHM2) checkboxHM2.setChecked(false);
                if (buttonView != checkboxGLOBAL3) checkboxGLOBAL3.setChecked(false);
                if (buttonView != checkboxUN4) checkboxUN4.setChecked(false);
                if (buttonView != checkboxPL5) checkboxPL5.setChecked(false);

                // Mostrar los TextView y EditText correspondientes
                if (buttonView == checkboxML1) {
                    tvML.setVisibility(View.VISIBLE);
                    etML.setVisibility(View.VISIBLE);
                } else {
                    tvML.setVisibility(View.GONE);
                    etML.setVisibility(View.GONE);
                }
            }
        };

        // Asignar el listener a cada checkbox
        checkboxML1.setOnCheckedChangeListener(listener);
        checkboxHM2.setOnCheckedChangeListener(listener);
        checkboxGLOBAL3.setOnCheckedChangeListener(listener);
        checkboxUN4.setOnCheckedChangeListener(listener);
        checkboxPL5.setOnCheckedChangeListener(listener);

        // Configuración del botón para mostrar cotización
        Button btn_mostrar_cotización = findViewById(R.id.GuardarButton);
        btn_mostrar_cotización.setOnClickListener(v -> {
            // Obtener los datos adicionales de la cotización
            String descripcion = ((EditText) findViewById(R.id.descripcion)).getText().toString();
            String metrosLineales = etML.getText().toString();

            // Actualizar la cotizacion con los nuevos datos
            cotizacion.setDescripcion(descripcion);
            cotizacion.setMetrosLineales(metrosLineales);

            // Crear un Intent para iniciar la siguiente actividad
            Intent intent = new Intent(layout_2_cotiza.this, Activity_mostrar_cotizacon.class);
            intent.putExtra("cotizacion", cotizacion);
            startActivity(intent);
        });
    }
}
