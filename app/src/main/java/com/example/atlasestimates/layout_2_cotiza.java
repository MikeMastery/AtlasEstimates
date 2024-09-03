package com.example.atlasestimates;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.DecimalFormat;

public class layout_2_cotiza extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private TextView tvML;
    private EditText etML;
    private TextView tvPrecio;
    private EditText etPrecio;
    private Cotizacion cotizacion;
    private static final double IGV = 0.18; // 18% IGV

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout2_cotiza);

        // Inicialización de vistas
        spinner = findViewById(R.id.spinner2);
        editText = findViewById(R.id.editText2);
        tvML = findViewById(R.id.tv_ml);
        etML = findViewById(R.id.et_ml);
        tvPrecio = findViewById(R.id.tex_precio);
        etPrecio = findViewById(R.id.ed_precio);

        // Inicialización de objetos
        cotizacion = CotizacionManager.getInstance().getCotizacion();

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

                // Actualizar la cotización
                cotizacion.setProducto(selectedProduct);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                editText.setText("");
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
                    tvPrecio.setVisibility(View.VISIBLE);
                    etPrecio.setVisibility(View.VISIBLE);
                } else {
                    tvML.setVisibility(View.GONE);
                    etML.setVisibility(View.GONE);
                    tvPrecio.setVisibility(View.GONE);
                    etPrecio.setVisibility(View.GONE);
                }
            }
        };

        // Asignar el listener a cada checkbox
        checkboxML1.setOnCheckedChangeListener(listener);
        checkboxHM2.setOnCheckedChangeListener(listener);
        checkboxGLOBAL3.setOnCheckedChangeListener(listener);
        checkboxUN4.setOnCheckedChangeListener(listener);
        checkboxPL5.setOnCheckedChangeListener(listener);

        // Añadir TextWatcher a etML y etPrecio
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                calcularTotal();
            }
        };

        etML.addTextChangedListener(textWatcher);
        etPrecio.addTextChangedListener(textWatcher);

        // Configuración del botón para mostrar cotización
        Button btn_mostrar_cotización = findViewById(R.id.GuardarButton);
        btn_mostrar_cotización.setOnClickListener(v -> {
            // Obtener los datos adicionales de la cotización
            String descripcion = ((EditText) findViewById(R.id.descripcion)).getText().toString();
            String metrosLineales = etML.getText().toString();
            String precio = etPrecio.getText().toString();

            // Actualizar la cotizacion con los nuevos datos
            cotizacion.setDescripcion(descripcion);
            cotizacion.setMetrosLineales(metrosLineales);
            cotizacion.setPrecio(precio);

            // Realizar el cálculo final antes de pasar a la siguiente actividad
            calcularTotal();

            // Crear un Intent para iniciar la siguiente actividad
            Intent intent = new Intent(layout_2_cotiza.this, Activity_mostrar_cotizacon.class);
            intent.putExtra("cotizacion", cotizacion);
            startActivity(intent);
        });
    }

    private void calcularTotal() {
        try {
            double metrosLineales = Double.parseDouble(etML.getText().toString());
            double precio = Double.parseDouble(etPrecio.getText().toString());

            double total = metrosLineales * precio;
            double totalIGV = total * (1 + IGV);

            DecimalFormat df = new DecimalFormat("#.##");

            // Guardar los resultados en el objeto cotizacion
            cotizacion.setTotal(df.format(total));
            cotizacion.setTotalIGV(df.format(totalIGV));
        } catch (NumberFormatException e) {
            // En caso de error, establecer valores por defecto o manejar el error
            cotizacion.setTotal("0.00");
            cotizacion.setTotalIGV("0.00");
        }
    }
}