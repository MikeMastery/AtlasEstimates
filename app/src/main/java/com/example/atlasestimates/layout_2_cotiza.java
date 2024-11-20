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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class layout_2_cotiza extends AppCompatActivity {


    private TextView textviewMonto, tvmovilización, tvHorasAlquiler, tvcostoMaquina;
    private EditText edMontoMaquinaria, ed_horasAlquiler, ed_costoMaquina;
    private RadioGroup radioGroupMovilizar;
    private Spinner spinnerCategoria;
    private Spinner spinnerProducto, spinnerTipodemaquina, spinnerAbastecimientoAgua;
    private EditText edCantidadAgua, edTotalAgua;
    private TextView tvSeleccionarProducto, tvseleccionarTipoMaquina, textviewAgua, textviewTotalAgua;
    private EditText etDescripcion, etMl, etPrecio, etHorasMaquina, etPrecioHora;
    private TextView tvMl, tvPrecio, tvHorasMaquina, tvPrecioHora;
    private TextView tvDesarrolloProyecto, tvSuperviSion, tvtotaL;
    private TextView tvMedida, tvTopografia, textviewcomentario,
            tvcantidadAgua, tvPrecioAgua, tvcalcularPrecio, textviewObra, editextObra,
            tv_estructurametalica;
    private EditText edtotalPagar, ettotalTopogrgafia, etPrecioAgua, etcantidadAgua, edcalcularDia, edcalcularPrecio, editextestructura,
            editext_maquinapesada;
    private Button btnGuardar;
    private FrameLayout frame_TipoMaquina, frame_CantidadAgua;
    private ImageButton btnAtras;
    private EditText editex_categoria, edittext_producto, editextcomentario, edtotalingenieria;
    private Cotizacion cotizacion;
    private RadioGroup radioGroup, radioGroupSINO, radioGroupSupervision;
    private RadioButton radioButtonUnidad, radioButtonGlobal, radioButtonSI, radioButtonNO, radioSupervisionSI, radioSupervisionNO;


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

        // Configurar el RadioGroup
        configurarRadioGroupMovilizar();

        configurarSpinnerAbastecimientoAgua();

        // Configurar TextWatcher para los cálculos
        configurarTextWatcher();

        // Configurar botones
        configurarBotones();

        // Configurar el Spinner
        configurarSpinnerMaquinaria();


    }

    private void inicializarVistas() {

        textviewMonto = findViewById(R.id.textviewmovilizarSi);
        tvmovilización = findViewById(R.id.textviewMovilizaciónEquipo);
        ed_horasAlquiler = findViewById(R.id.edMontodeAlquiler);
        tvHorasAlquiler = findViewById(R.id.textviewHorasAlquiler);
        ed_costoMaquina = findViewById(R.id.edCostoAlquiler);
        tvcostoMaquina = findViewById(R.id.textviewCostoHora);
        edMontoMaquinaria = findViewById(R.id.edMontoMaquinaria);
        radioGroupMovilizar = findViewById(R.id.radioGroupMovilizar);
        spinnerCategoria = findViewById(R.id.spinner1);
        spinnerProducto = findViewById(R.id.spinner2);
        spinnerTipodemaquina = findViewById(R.id.spinnerMaquina);
        spinnerAbastecimientoAgua = findViewById(R.id.spinnerTipoAgua);
        edCantidadAgua = findViewById(R.id.editextTipoAgua);
        textviewTotalAgua = findViewById(R.id.textviewTotalAgua);
        edTotalAgua = findViewById(R.id.edTotalAgua);
        textviewAgua = findViewById(R.id.tvAgua);
        frame_CantidadAgua = findViewById(R.id.frame_abasAgua);
        tvSeleccionarProducto = findViewById(R.id.tvseleccionar_prodcuto);
        etDescripcion = findViewById(R.id.descripcion);
        etMl = findViewById(R.id.et_ml);
        tvDesarrolloProyecto = findViewById(R.id.tvdesarrolloProyecto);
        radioButtonSI = findViewById(R.id.radiobutton_SI);
        radioButtonNO = findViewById(R.id.radiobutton_NO);
        radioGroupSINO = findViewById(R.id.radioGroup2);
        tvSuperviSion = findViewById(R.id.tvsupervicion);
        tvtotaL = findViewById(R.id.textviewTotal);
        edtotalPagar = findViewById(R.id.ed_totalapagar);
        tvMedida = findViewById(R.id.Medida);
        tvTopografia = findViewById(R.id.tvtotalTopografia);
        ettotalTopogrgafia = findViewById(R.id.ed_totalTopografia);
        tvcantidadAgua = findViewById(R.id.tv_abastecimientoAgua);
        etcantidadAgua = findViewById(R.id.ed_abastecimientoAgua);
        etPrecioAgua = findViewById(R.id.et_preciometro);
        tvPrecioAgua = findViewById(R.id.tv_precioLitros);
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
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonUnidad = findViewById(R.id.radiobutton_Unidad);
        radioButtonGlobal = findViewById(R.id.radiobutton_Global);
        radioGroupSupervision = findViewById(R.id.radioGroup3);
        radioSupervisionSI = findViewById(R.id.radiosupervision_SI);
        radioSupervisionNO = findViewById(R.id.radiosupervision_NO);
        editextcomentario = findViewById(R.id.ed_comentarios);
        textviewcomentario = findViewById(R.id.tv_comentarios);
        textviewObra = findViewById(R.id.textTotalObra);
        editextObra = findViewById(R.id.ed_totalObra);
        tv_estructurametalica = findViewById(R.id.textTotalEstructura);
        editextestructura = findViewById(R.id.ed_totalEstructura);
        editext_maquinapesada = findViewById(R.id.editextTipoMaquina);
        tvseleccionarTipoMaquina = findViewById(R.id.tvseleccionar_maquina);
        frame_TipoMaquina = findViewById(R.id.framelayoutTipoMaquina);




    }



    private void configurarSpinnerMaquinaria() {
        // Crear un ArrayAdapter usando el recurso de string-array y el diseño predeterminado
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_TipodeMaquinariaPesads,
                android.R.layout.simple_spinner_item
        );

        // Configurar el diseño desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asignar el adaptador al Spinner
        spinnerTipodemaquina.setAdapter(adapter);

        // Configurar el manejador de selección
        spinnerTipodemaquina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el elemento seleccionado
                String seleccion = parent.getItemAtPosition(position).toString();
                // Actualizar el EditText con la selección
                editext_maquinapesada.setText(seleccion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada si no se selecciona una opción
            }
        });
    }

    private void configurarSpinnerAbastecimientoAgua() {
        // Crear un ArrayAdapter usando el recurso de string-array y el diseño predeterminado
        ArrayAdapter<CharSequence> adapterAgua = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_tipoCisterna,
                android.R.layout.simple_spinner_item
        );

        // Configurar el diseño desplegable
        adapterAgua.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asignar el adaptador al Spinner
        spinnerAbastecimientoAgua.setAdapter(adapterAgua);

        // Configurar el manejador de selección
        spinnerAbastecimientoAgua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el elemento seleccionado
                String seleccionAgua = parent.getItemAtPosition(position).toString();
                // Actualizar el EditText con la selección
                edCantidadAgua.setText(seleccionAgua);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada si no se selecciona una opción
            }
        });
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

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
        edMontoMaquinaria.addTextChangedListener(textWatcher);
        ed_horasAlquiler.addTextChangedListener(textWatcher);
        ed_costoMaquina.addTextChangedListener(textWatcher);


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
                hideCampoConstruccionObra();
                hidecamposMaquinariaPesada();
                hideCamposAbastecimientoAgua();
                break;

            case "Block de concreto":
            case "Poste de concreto":
                showHorasMaquinaFields();
                hideMetrosLinealesFields();
                hidecamposIngArqui();
                hideCamposTopografia();
                hideCampoConstruccionObra();
                hidecamposMaquinariaPesada();
                hideCamposAbastecimientoAgua();
                break;

            case "Ingenieria":
            case "Arquitectura":
                showcamposIngnieriaArquitectura();
                hideHorasMaquinaFields();
                hideMetrosLinealesFields();
                hideCamposTopografia();
                hideCampoConstruccionObra();
                hidecamposMaquinariaPesada();
                hideCamposAbastecimientoAgua();
                break;

            case "Unidad":
            case "Global":
                showCamposTopografia();
                hideMetrosLinealesFields();
                hideHorasMaquinaFields();
                hidecamposIngArqui();
                hideCampoConstruccionObra();
                hidecamposMaquinariaPesada();
                hideCamposAbastecimientoAgua();
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
                hideCampoConstruccionObra();
                hidecamposMaquinariaPesada();
                hideCamposAbastecimientoAgua();
                break;

            case "Agua potable":
            case "Agua no potable":
                showCamposAbatecimeintoAgua();
                hideMetrosLinealesFields();
                hideHorasMaquinaFields();
                hidecamposIngArqui();
                hideCamposTopografia();
                hideCamposEstructuras();
                hideCampoConstruccionObra();
                hidecamposMaquinariaPesada();
                break;

            case "Medida Global":
                showCampoConstruccionObra();
                hideMetrosLinealesFields();
                hideHorasMaquinaFields();
                hidecamposIngArqui();
                hideCamposTopografia();
                hideCamposEstructuras();
                hidecamposMaquinariaPesada();
                hideCamposAbastecimientoAgua();
                break;

            case "Generador (10 KW)":
            case "Rotomartillo Demoledor (17 K)":
            case "Rotomartillo Demoledor (11 K)":
            case "Cortadora Pavimento":
            case "Mezcladora":
            case "Vibrador Concreto":
                showcamposEquiposMenores();
                hideMetrosLinealesFields();
                hideHorasMaquinaFields();
                hidecamposIngArqui();
                hideCamposTopografia();
                hideCamposEstructuras();
                hideCamposAbastecimientoAgua();
                hideCampoConstruccionObra();
                hidecamposMaquinariaPesada();
                hideCamposAbastecimientoAgua();
                break;

            case "Rotomartillo Demoledor":
                break;

            case "Alquiler":
                showcamposMaquinariaPesasa();
                hideMetrosLinealesFields();
                hideHorasMaquinaFields();
                hidecamposIngArqui();
                hideCamposTopografia();
                hideCamposEstructuras();
                hideCamposAbastecimientoAgua();
                hideCampoConstruccionObra();
                hidecamposEquiposMenores();
                hideCamposAbastecimientoAgua();
                break;

            case "Global MP":
                showcamposMaquinariaGlobal();
                hideMetrosLinealesFields();
                hideHorasMaquinaFields();
                hidecamposIngArqui();
                hideCamposTopografia();
                hideCamposEstructuras();
                hideCamposAbastecimientoAgua();
                hideCampoConstruccionObra();
                hidecamposEquiposMenores();
                hideCamposAbastecimientoAgua();
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
        etMl.setText("");
        etPrecio.setVisibility(View.GONE);
        etPrecio.setText("");
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
        etHorasMaquina.setText("");
        etPrecioHora.setVisibility(View.GONE);
        etPrecioHora.setText("");
        tvHorasMaquina.setVisibility(View.GONE);
        tvPrecioHora.setVisibility(View.GONE);

    }

    private void showcamposIngnieriaArquitectura() {
        radioButtonSI.setVisibility(View.VISIBLE);
        radioButtonNO.setVisibility(View.VISIBLE);
        radioButtonGlobal.setVisibility(View.VISIBLE);
        radioButtonUnidad.setVisibility(View.VISIBLE);
        radioSupervisionNO.setVisibility(View.VISIBLE);
        radioSupervisionSI.setVisibility(View.VISIBLE);
        tvSuperviSion.setVisibility(View.VISIBLE);
        tvtotaL.setVisibility(View.VISIBLE);
        tvDesarrolloProyecto.setVisibility(View.VISIBLE);
        tvMedida.setVisibility(View.VISIBLE);
        edtotalPagar.setVisibility(View.VISIBLE);

    }

    private void hidecamposIngArqui() {
        radioButtonSI.setVisibility(View.GONE);
        radioButtonSI.setChecked(false); // Limpia selección
        radioButtonNO.setVisibility(View.GONE);
        radioButtonNO.setChecked(false); // Limpia selección
        radioButtonGlobal.setVisibility(View.GONE);
        radioButtonGlobal.setChecked(false); // Limpia selección
        radioButtonUnidad.setVisibility(View.GONE);
        radioButtonUnidad.setChecked(false); // Limpia selección
        radioSupervisionNO.setVisibility(View.GONE);
        radioSupervisionNO.setChecked(false); // Limpia selección
        radioSupervisionSI.setVisibility(View.GONE);
        radioSupervisionSI.setChecked(false); // Limpia selección
        tvSuperviSion.setVisibility(View.GONE);
        tvDesarrolloProyecto.setVisibility(View.GONE);
        tvtotaL.setVisibility(View.GONE);
        tvMedida.setVisibility(View.GONE);
        edtotalPagar.setVisibility(View.GONE);

    }

    private void showCamposTopografia() {
        tvTopografia.setVisibility(View.VISIBLE);
        ettotalTopogrgafia.setVisibility(View.VISIBLE);
    }

    private void hideCamposTopografia() {
        tvTopografia.setVisibility(View.GONE);
        ettotalTopogrgafia.setVisibility(View.GONE);

    }

    private void showCamposEstructuras() {
        tv_estructurametalica.setVisibility(View.VISIBLE);
        editextestructura.setVisibility(View.VISIBLE);

    }


    private void hideCamposEstructuras() {
        tv_estructurametalica.setVisibility(View.GONE);
        editextestructura.setVisibility(View.GONE);

    }

    private void showCamposAbatecimeintoAgua() {
        textviewAgua.setVisibility(View.VISIBLE);
        frame_CantidadAgua.setVisibility(View.VISIBLE);
        textviewTotalAgua.setVisibility(View.VISIBLE);
        edTotalAgua.setVisibility(View.VISIBLE);


    }

    private void hideCamposAbastecimientoAgua() {
        textviewAgua.setVisibility(View.GONE);
        frame_CantidadAgua.setVisibility(View.GONE);
        textviewTotalAgua.setVisibility(View.GONE);
        edTotalAgua.setVisibility(View.GONE);

    }

    private void showcamposMaquinariaPesasa() {
        tvseleccionarTipoMaquina.setVisibility(View.VISIBLE);
        frame_TipoMaquina.setVisibility(View.VISIBLE);
        radioGroupMovilizar.setVisibility(View.VISIBLE);
        tvmovilización.setVisibility(View.VISIBLE);
        ed_horasAlquiler.setVisibility(View.VISIBLE);
        tvHorasAlquiler.setVisibility(View.VISIBLE);
        tvcostoMaquina.setVisibility(View.VISIBLE);
        ed_costoMaquina.setVisibility(View.VISIBLE);


    }


    private void configurarRadioGroupMovilizar() {
        radioGroupMovilizar.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioMovilizar_SI) {
                // Mostrar campos relacionados
                textviewMonto.setVisibility(View.VISIBLE);
                edMontoMaquinaria.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radioMovilizar_NO) {
                // Ocultar campos relacionados
                textviewMonto.setVisibility(View.GONE);
                edMontoMaquinaria.setVisibility(View.GONE);

            }
        });
    }


    private void hidecamposMaquinariaPesada() {
        tvseleccionarTipoMaquina.setVisibility(View.GONE);
        frame_TipoMaquina.setVisibility(View.GONE);
        textviewMonto.setVisibility(View.GONE);
        edMontoMaquinaria.setVisibility(View.GONE);
        radioGroupMovilizar.setVisibility(View.GONE);
        tvmovilización.setVisibility(View.GONE);
        ed_horasAlquiler.setVisibility(View.GONE);
        tvHorasAlquiler.setVisibility(View.GONE);
        tvcostoMaquina.setVisibility(View.GONE);
        ed_costoMaquina.setVisibility(View.GONE);

    }

    private void showcamposMaquinariaGlobal(){
        tvseleccionarTipoMaquina.setVisibility(View.VISIBLE);
        frame_TipoMaquina.setVisibility(View.VISIBLE);

    }
    private void hidecamposMaquinariaGlobal() {
        tvseleccionarTipoMaquina.setVisibility(View.GONE);
        frame_TipoMaquina.setVisibility(View.GONE);

    }

    private void showCampoConstruccionObra(){
        editextObra.setVisibility(View.VISIBLE);
        textviewObra.setVisibility(View.VISIBLE);
    }

    private void hideCampoConstruccionObra() {
        editextObra.setVisibility(View.GONE);
         // Limpia el contenido
        textviewObra.setVisibility(View.GONE);
    }


    private void showcamposEquiposMenores(){
        tvcantidadAgua.setVisibility(View.VISIBLE);
        etcantidadAgua.setVisibility(View.VISIBLE);
        tvPrecioAgua.setVisibility(View.VISIBLE);
        etPrecioAgua.setVisibility(View.VISIBLE);
    }
    private void hidecamposEquiposMenores(){
        tvcantidadAgua.setVisibility(View.GONE);
        etcantidadAgua.setVisibility(View.GONE);

        tvPrecioAgua.setVisibility(View.GONE);
        etPrecioAgua.setVisibility(View.GONE);

    }


    private void hideAllFields() {
        hideMetrosLinealesFields();
        hideHorasMaquinaFields();
        hidecamposIngArqui();
        hideCamposTopografia();
        hideCamposEstructuras();
        hideCamposAbastecimientoAgua();
        hidecamposEquiposMenores();
        hideCampoConstruccionObra();
        hidecamposMaquinariaPesada();
        hidecamposMaquinariaGlobal();
    }
    private void calculateResult() {
        try {
            double cantidad, precio, subtotal, igv, total;
            subtotal = 0; // Inicializar el subtotal

            // Verificar si el campo de metros lineales está visible
            if (etMl.getVisibility() == View.VISIBLE) {
                cantidad = Double.parseDouble(etMl.getText().toString());
                precio = Double.parseDouble(etPrecio.getText().toString());
                cotizacion.setMetrosLineales(formatNumber(cantidad));
                cotizacion.setPrecio(formatearNumeroConComas(precio));
                subtotal = cantidad * precio;

                // Verificar si el campo de horas de máquina está visible
            } else if (etHorasMaquina.getVisibility() == View.VISIBLE) {
                cantidad = Double.parseDouble(etHorasMaquina.getText().toString());
                precio = Double.parseDouble(etPrecioHora.getText().toString());
                cotizacion.setHorasMaquina(formatNumber(cantidad));
                cotizacion.setPrecioHora(formatearNumeroConComas(precio));
                subtotal = cantidad * precio;

                // Verificar si el campo de cantidad de agua está visible
            } else if (etcantidadAgua.getVisibility() == View.VISIBLE) {
                cantidad = Double.parseDouble(etcantidadAgua.getText().toString());
                precio = Double.parseDouble(etPrecioAgua.getText().toString());
                cotizacion.setEquipoMenor(formatNumber(cantidad));
                cotizacion.setPrecioEquiposMenores(formatearNumeroConComas(precio));
                subtotal = cantidad * precio;

                // Verificar si el campo de maquinaria pesada está visible
            } else if (ed_horasAlquiler.getVisibility() == View.VISIBLE && ed_costoMaquina.getVisibility() == View.VISIBLE) {
                cantidad = Double.parseDouble(ed_horasAlquiler.getText().toString());
                precio = Double.parseDouble(ed_costoMaquina.getText().toString());
                cotizacion.setHorasAlquiler(formatNumber(cantidad));
                cotizacion.setCostoHora(formatearNumeroConComas(precio));
                subtotal = cantidad * precio;

                // Si la movilización es "Sí", agregar el monto de movilización al subtotal
                if (radioGroupMovilizar.getCheckedRadioButtonId() == R.id.radioMovilizar_SI) {
                    double montoMovilizacion = 0.0;
                    if (!edMontoMaquinaria.getText().toString().isEmpty()) {
                        montoMovilizacion = Double.parseDouble(edMontoMaquinaria.getText().toString());
                    }
                    // Asegurarse de sumar el monto de movilización al subtotal correctamente
                    subtotal += montoMovilizacion;
                    cotizacion.setMontoMovilizacion(String.valueOf(montoMovilizacion));
                }
            }

            // Calcular IGV y total
            igv = subtotal * 0.18;
            total = subtotal + igv;

            // Formateamos los valores con comas
            cotizacion.setSubtotal(formatearNumeroConComas(subtotal));
            cotizacion.setIgv(formatearNumeroConComas(igv));
            cotizacion.setTotal(formatearNumeroConComas(total));

        } catch (NumberFormatException e) {
            // Manejar error si los campos están vacíos o tienen formato incorrec
            cotizacion.setSubtotal("");
            cotizacion.setIgv("");
            cotizacion.setTotal("");
            cotizacion.setMetrosLineales("");
            cotizacion.setPrecio("");
            cotizacion.setHorasAlquiler("");
            cotizacion.setPrecioHora("");
            cotizacion.setMontoMovilizacion("");
        }
    }

    private String formatNumber(double number) {
        if (number % 1 == 0) {
            // Si es un número entero, devolverlo como entero
            return String.format("%.0f", number);
        } else {
            // Si tiene decimales, devolverlo con 2 decimales
            return String.format("%.2f", number);
        }
    }

    private String formatearNumeroConComas(double numero) {
        DecimalFormat formato = new DecimalFormat("#,###");
        return formato.format(numero);
    }





    private void guardarCotizacion() {
        cotizacion.setDescripcion(etDescripcion.getText().toString());
        cotizacion.setCategoria(editex_categoria.getText().toString());
        cotizacion.setProducto(edittext_producto.getText().toString());
        cotizacion.setTextodesarrollo(tvDesarrolloProyecto.getText().toString());
        cotizacion.setTotalIngeArqui(edtotalPagar.getText().toString());
        cotizacion.settotaltopografia(ettotalTopogrgafia.getText().toString());
        cotizacion.setComentarioTopografia(editextcomentario.getText().toString());
        cotizacion.setCampoConstruccionObra(editextObra.getText().toString());
        cotizacion.setCampoEstructura(editextestructura.getText().toString());
        cotizacion.setCampoTotalAgua(edTotalAgua.getText().toString());
        cotizacion.setCantidadAgua(edCantidadAgua.getText().toString());
        cotizacion.setMaquina(editext_maquinapesada.getText().toString());



        // Recuperar el valor seleccionado del RadioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radiobutton_Unidad) {
            cotizacion.setMedida("Unidad");
        } else if (selectedId == R.id.radiobutton_Global) {
            cotizacion.setMedida("Global");
        }

        int seleccionarSINO = radioGroupSINO.getCheckedRadioButtonId();
        if (seleccionarSINO == R.id.radiobutton_SI){
            cotizacion.setDesarrolloProyecto("SI");
        }else if (seleccionarSINO == R.id.radiobutton_NO){
            cotizacion.setDesarrolloProyecto("NO");
        }
        int seleccionarSupervision = radioGroupSupervision.getCheckedRadioButtonId();
        if (seleccionarSupervision == R.id.radiosupervision_SI){
            cotizacion.setSupervisonSINO("SI");
        }else if (seleccionarSupervision == R.id.radiosupervision_NO){
            cotizacion.setSupervisonSINO("NO");
        }



        Intent intent = new Intent(this,Activity_mostrar_cotizacon.class);
        intent.putExtra("cotizacion", cotizacion);
        startActivity(intent);

    }
}
