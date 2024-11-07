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
    private TextView tvDesarrolloProyecto, tvSuperviSion, tvtotaL;
    private TextView checkSupervision, checkdesarrolloProyecto, checkunidad, checkglobal, tvMedida, tvTopografia,
            tvCaracteristicas, tvcantidadAgua, tvPrecioAgua, tvcalcular_Dia, tvcalcularPrecio;
    private EditText edtotalPagar, etCaracteristicas,  ettotalTopogrgafia, etPrecioAgua, etcantidadAgua, edcalcularDia, edcalcularPrecio;
    private Button btnGuardar;
    private ImageButton btnAtras;
    private EditText editex_categoria, edittext_producto;
    private Cotizacion cotizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout2_cotiza);

        // Inicializar la clase Cotizacion
        cotizacion = CotizacionManager.getInstance().getCotizacion();

        // Inicializar vistas
        inicializarVistas();

        // Configurar Spinners
        configurarSpinnerCategoria();

        // Configurar TextWatcher para los cálculos
        configurarTextWatcher();

        // Configurar botones
        configurarBotones();
    }

    private void inicializarVistas() {
        spinnerCategoria = findViewById(R.id.spinner1);
        spinnerProducto = findViewById(R.id.spinner2);
        tvSeleccionarProducto = findViewById(R.id.tvseleccionar_prodcuto);
        etDescripcion = findViewById(R.id.descripcion);
        etMl = findViewById(R.id.et_ml);
        tvDesarrolloProyecto = findViewById(R.id.tvdesarrolloProyecto);
        tvSuperviSion = findViewById(R.id.tvsupervicion);
        tvtotaL = findViewById(R.id.textviewTotal);
        checkSupervision = findViewById(R.id.checkboxSupervision);
        checkdesarrolloProyecto = findViewById(R.id.checkboxdesarrollo);
        checkunidad = findViewById(R.id.checkbox_Unidad);
        checkglobal = findViewById(R.id.checkbox_Global);
        edtotalPagar = findViewById(R.id.ed_totalapagar);
        tvMedida = findViewById(R.id.Medida);
        tvTopografia = findViewById(R.id.tvtotalTopografia);
        ettotalTopogrgafia = findViewById(R.id.ed_totalTopografia);
        tvCaracteristicas = findViewById(R.id.tv_caracteres);
        etCaracteristicas = findViewById(R.id.ed_caracteristicas);
        tvcantidadAgua = findViewById(R.id.tv_abastecimientoAgua);
        etcantidadAgua = findViewById(R.id.ed_abastecimientoAgua);
        etPrecioAgua = findViewById(R.id.et_preciometro);
        tvPrecioAgua = findViewById(R.id.tv_precioLitros);
        tvcalcular_Dia = findViewById(R.id.tvcalculoDia);
        tvcalcularPrecio = findViewById(R.id.tvPrecioEquipo);
        edcalcularDia = findViewById(R.id.ed_calculoDia);
        edcalcularPrecio = findViewById(R.id.ed_precioEquipo);
        etPrecio = findViewById(R.id.ed_precio);
        etHorasMaquina = findViewById(R.id.et_hm);
        etPrecioHora = findViewById(R.id.ed_precio_hm);
        tvMl = findViewById(R.id.tv_ml);
        tvPrecio = findViewById(R.id.tex_precio);
        tvHorasMaquina = findViewById(R.id.tv_hm);
        tvPrecioHora = findViewById(R.id.tex_precio_hm);
        btnGuardar = findViewById(R.id.GuardarButton);
        btnAtras = findViewById(R.id.atras_coti1);
        editex_categoria = findViewById(R.id.editText1);
        edittext_producto = findViewById(R.id.editText2);
    }

    private void configurarSpinnerCategoria() {
        ArrayAdapter<CharSequence> adapterCategoria = ArrayAdapter.createFromResource(this,
                R.array.spinner_categorias, android.R.layout.simple_spinner_item);
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategoria);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategoria = parent.getItemAtPosition(position).toString();
                editex_categoria.setText(selectedCategoria);
                manejarCategoriaSeleccionada(selectedCategoria);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada si no se selecciona ninguna opción
            }
        });
    }

    private void manejarCategoriaSeleccionada(String selectedCategoria) {
        switch (selectedCategoria) {
            case "Materiales":
                tvSeleccionarProducto.setText("Materiales");
                setSpinnerSubcategorias(R.array.spinner_productos);
                break;

            case "Servicios":
                tvSeleccionarProducto.setText("Servicios");
                setSpinnerSubcategorias(R.array.spinner_servicios);
                break;

            case "Topografia":
                tvSeleccionarProducto.setText("Medida");
                setSpinnerSubcategorias(R.array.spinner_medida);
                break;

            case "Estructuras Metálicas":
                tvSeleccionarProducto.setText("Seleccionar Estructura");
                setSpinnerSubcategorias(R.array.spinner_estructurasMetalicas);
                break;

            case "Abastecimiento de Agua":
                tvSeleccionarProducto.setText("Tipo de Agua");
                setSpinnerSubcategorias(R.array.spinner_AbasAgua);
                break;

            case "Maquinaria Pesada":
                tvSeleccionarProducto.setText("Subcategoría");
                setSpinnerSubcategorias(R.array.spinner_MaquinariaPesada);
                break;

            case "Construcción de Obra":
                tvSeleccionarProducto.setText("Subcategoría");
                setSpinnerSubcategorias(R.array.spinner_Constru_Obra);
                break;


            case "Equipos menores":
                tvSeleccionarProducto.setText("Tipo de maquina");
                setSpinnerSubcategorias(R.array.spinner_Equipos_Menores);
                break;


            default:
                tvSeleccionarProducto.setText("Seleccionar");
                spinnerProducto.setAdapter(null);
                hideAllFields();
                break;
        }
    }

    private void setSpinnerSubcategorias(int arrayResource) {
        ArrayAdapter<CharSequence> adapterSubcategorias = ArrayAdapter.createFromResource(this,
                arrayResource, android.R.layout.simple_spinner_item);
        adapterSubcategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProducto.setAdapter(adapterSubcategorias);

        spinnerProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubcategoria = parent.getItemAtPosition(position).toString();
                cotizacion.setProducto(selectedSubcategoria);
                edittext_producto.setText(selectedSubcategoria);
                updateUnidadMedida(selectedSubcategoria);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada si no se selecciona ninguna opción
            }
        });
    }

    private void configurarTextWatcher() {
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
        etcantidadAgua.addTextChangedListener(textWatcher);
        etPrecioAgua.addTextChangedListener(textWatcher);


    }

    private void configurarBotones() {
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCotizacion();
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Volver a la actividad anterior
            }
        });
    }

    private void updateUnidadMedida(String product) {
        // Primero ocultamos todos los campos
        hideAllFields();

        switch (product) {
            case "Cercos prefabricados":
            case "Cerco cabeza caballo":
                showMetrosLinealesFields();
                hideHorasMaquinaFields();
                hidecamposIngArqui();
                hideCamposTopografia();
                break;

            case "Block de concreto":
            case "Poste de concreto":
                showHorasMaquinaFields();
                hideMetrosLinealesFields();
                hidecamposIngArqui();
                hideCamposTopografia();
                break;

            case "Ingenieria":
            case "Arquitectura":
                showcamposIngnieriaArquitectura();
                hideHorasMaquinaFields();
                hideMetrosLinealesFields();
                hideCamposTopografia();
                break;

            case "Unidad":
            case "Global":
                showCamposTopografia();
                hideMetrosLinealesFields();
                hideHorasMaquinaFields();
                hidecamposIngArqui();
                break;

            case "Coberturas":
            case "Puertas":
            case "Portones":
            case "Barandas":
            case "Escaleras":
                showCamposEstructuras();
                hideMetrosLinealesFields();
                hideHorasMaquinaFields();
                hidecamposIngArqui();
                break;

            case "Agua potable":
            case "Agua no potable":
                showCamposAbatecimeintoAgua();
                hideMetrosLinealesFields();
                hideHorasMaquinaFields();
                hidecamposIngArqui();
                hideCamposTopografia();
                hideCamposEstructuras();
                break;

            case "Generador (10 KW)":
            case "Cortadora Padimento":
            case "Mescladora":
            case "Vibrador Concreto":
                showcamposEquiposMenores();
                hideMetrosLinealesFields();
                hideHorasMaquinaFields();
                hidecamposIngArqui();
                hideCamposTopografia();
                hideCamposEstructuras();
                hideCamposAbastecimientoAgua();
                break;

            case "Rotomartillo Demoledor":
                break;

            default:
                hideAllFields();
                break;
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
    private void showcamposIngnieriaArquitectura(){
        checkdesarrolloProyecto.setVisibility(View.VISIBLE);
        checkSupervision.setVisibility(View.VISIBLE);
        checkunidad.setVisibility(View.VISIBLE);
        checkglobal.setVisibility(View.VISIBLE);
        tvSuperviSion.setVisibility(View.VISIBLE);
        tvtotaL.setVisibility(View.VISIBLE);
        tvDesarrolloProyecto.setVisibility(View.VISIBLE);
        tvMedida.setVisibility(View.VISIBLE);
        edtotalPagar.setVisibility(View.VISIBLE);

    }
    private void hidecamposIngArqui(){
        checkdesarrolloProyecto.setVisibility(View.GONE);
        checkSupervision.setVisibility(View.GONE);
        checkunidad.setVisibility(View.GONE);
        checkglobal.setVisibility(View.GONE);
        tvSuperviSion.setVisibility(View.GONE);
        tvDesarrolloProyecto.setVisibility(View.GONE);
        tvtotaL.setVisibility(View.GONE);
        tvMedida.setVisibility(View.GONE);
        edtotalPagar.setVisibility(View.GONE);
    }
    private void showCamposTopografia(){
        tvTopografia.setVisibility(View.VISIBLE);
        ettotalTopogrgafia.setVisibility(View.VISIBLE);
    }

    private void  hideCamposTopografia(){
        tvTopografia.setVisibility(View.GONE);
        ettotalTopogrgafia.setVisibility(View.GONE);
    }
    private void showCamposEstructuras(){
        tvCaracteristicas.setVisibility(View.VISIBLE);
        etCaracteristicas.setVisibility(View.VISIBLE);
        tvTopografia.setVisibility(View.VISIBLE);
        ettotalTopogrgafia.setVisibility(View.VISIBLE);
    }

    private void hideCamposEstructuras(){
        tvCaracteristicas.setVisibility(View.GONE);
        etCaracteristicas.setVisibility(View.GONE);

    }
    private void showCamposAbatecimeintoAgua(){
        tvcantidadAgua.setVisibility(View.VISIBLE);
        etcantidadAgua.setVisibility(View.VISIBLE);
        tvPrecioAgua.setVisibility(View.VISIBLE);
        etPrecioAgua.setVisibility(View.VISIBLE);
    }

    private void hideCamposAbastecimientoAgua(){
        tvcantidadAgua.setVisibility(View.GONE);
        etcantidadAgua.setVisibility(View.GONE);
        tvPrecioAgua.setVisibility(View.GONE);
        etPrecioAgua.setVisibility(View.GONE);
    }

    private void showcamposEquiposMenores(){
        tvcalcular_Dia.setVisibility(View.VISIBLE);
        tvcalcularPrecio.setVisibility(View.VISIBLE);
        edcalcularDia.setVisibility(View.VISIBLE);
        edcalcularPrecio.setVisibility(View.VISIBLE);
    }
    private void hidecamposEquiposMenores(){
        tvcalcular_Dia.setVisibility(View.GONE);
        tvcalcularPrecio.setVisibility(View.GONE);
        edcalcularDia.setVisibility(View.GONE);
        edcalcularPrecio.setVisibility(View.GONE);
    }


    private void hideAllFields() {
        hideMetrosLinealesFields();
        hideHorasMaquinaFields();
        hidecamposIngArqui();
        hideCamposTopografia();
        hideCamposEstructuras();
        hideCamposAbastecimientoAgua();
        hidecamposEquiposMenores();
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

            }else if (etcantidadAgua.getVisibility() == View.VISIBLE) {
                cantidad = Double.parseDouble(etcantidadAgua.getText().toString());
                precio = Double.parseDouble(etPrecioAgua.getText().toString());
                cotizacion.setCantidadAgua(String.valueOf(cantidad));
                cotizacion.setPrecioAgua(String.valueOf(precio));

            }else{
                return;
            }

            subtotal = cantidad * precio;
            igv = subtotal * 0.18;
            total = subtotal + igv;

            cotizacion.setSubtotal(String.format("%.2f", subtotal));
            cotizacion.setIgv(String.format("%.2f", igv));
            cotizacion.setTotal(String.format("%.2f", total));

        } catch (NumberFormatException e) {
            // Manejar error si los campos están vacíos o tienen formato incorrecto
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
        cotizacion.setProducto(edittext_producto.getText().toString());

        Intent intent = new Intent(this,Activity_mostrar_cotizacon.class);
        intent.putExtra("cotizacion", cotizacion);
        startActivity(intent);

    }
}
