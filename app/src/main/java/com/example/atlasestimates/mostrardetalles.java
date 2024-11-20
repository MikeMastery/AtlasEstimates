package com.example.atlasestimates;

import android.graphics.Insets;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class mostrardetalles extends AppCompatActivity {

    private CotizacionViewModel viewModel;
    private TextView tvNombreCliente, tvTitulo, tvUbicacion, tvdescripcion, tvRuc, tvRazonSocial, tvCategoria, tvRequerimiento, tvSubTotal, tvIgv, tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrardetalles);

        // Inicializar los TextViews
        tvNombreCliente = findViewById(R.id.nombre_cliente);
        tvUbicacion = findViewById(R.id.tv_mostrar_ubi);
        tvTitulo = findViewById(R.id.tvtitulo);
        tvRuc = findViewById(R.id.MostrarRuc);
        tvRazonSocial = findViewById(R.id.mostrarRazon);
        tvCategoria = findViewById(R.id.categoriaMostrar);
        tvRequerimiento = findViewById(R.id.mostrarItem);
        tvSubTotal = findViewById(R.id.ed_SubTotal);
        tvIgv = findViewById(R.id.mostrar_igv);
        tvTotal = findViewById(R.id.ed_total);
        tvdescripcion = findViewById(R.id.descripcion_cotizacion);

        // Obtener el ID de la cotización desde el Intent
        int cotizacionId = getIntent().getIntExtra("cotizacionId", -1);

        // Obtener el ViewModel
        viewModel = new ViewModelProvider(this).get(CotizacionViewModel.class);

        // Obtener la cotización
        obtenerCotizacion(cotizacionId);

        // Obtener los detalles de la cotización
        obtenerDetalles(cotizacionId);

        // Obtener el cliente asociado a la cotización
        obtenerCliente(cotizacionId);

        // Obtener la categoría asociada a los ítems
        obtenerCategoria(cotizacionId);

        // Configurar el padding para la vista principal
        configurarPaddingVistaPrincipal();
    }

    private void obtenerCotizacion(int cotizacionId) {
        viewModel.getCotizacion(cotizacionId).observe(this, new Observer<table_cotizacion>() {
            @Override
            public void onChanged(table_cotizacion cotizacion) {
                if (cotizacion != null) {
                    // Actualiza los TextViews con los datos de la cotización
                    tvdescripcion.setText(cotizacion.getDescripcion());
                    tvTitulo.setText(cotizacion.getTitulo());
                    tvUbicacion.setText(cotizacion.getUbicacion());
                    tvTotal.setText("S/ " + cotizacion.getTotal());
                }
            }
        });
    }

    private void obtenerDetalles(int cotizacionId) {
        // Obtener los detalles de la cotización (sin relación con los ítems)
        viewModel.getDetalles(cotizacionId).observe(this, new Observer<List<table_detalleCotizacion>>() {
            @Override
            public void onChanged(List<table_detalleCotizacion> detalles) {
                if (detalles != null && detalles.size() > 0) {
                    // Aquí procesamos los detalles de la cotización
                    double subtotal = 0;
                    double igv = 0;
                    for (table_detalleCotizacion detalle : detalles) {
                        // Aquí puedes extraer información de los detalles y actualizar los TextViews correspondientes
                        subtotal += detalle.getSubtotal();  // Suponiendo que `getSubTotal()` esté en table_detalleCotizacion
                        igv += detalle.getIgv();  // Suponiendo que `getIgv()` esté en table_detalleCotizacion
                    }
                    // Actualizar los TextViews para mostrar los totales de los detalles
                    tvSubTotal.setText("S/ " + subtotal);  // Total de subtotales
                    tvIgv.setText("S/ " + igv);  // Total de IGV
                }
            }
        });

        // Obtener los ítems asociados a la cotización (sin relación con los detalles)
        viewModel.getItems(cotizacionId).observe(this, new Observer<List<table_items>>() {
            @Override
            public void onChanged(List<table_items> items) {
                if (items != null && items.size() > 0) {
                    // Aquí puedes procesar los ítems de la cotización
                    for (table_items item : items) {
                        // Mostrar la información de los ítems en los TextViews correspondientes
                        tvRequerimiento.setText(item.getNombre_Item());  // Actualiza con el nombre del ítem
                        // Aquí puedes agregar más datos del ítem si lo deseas (como precios, cantidades, etc.)
                    }
                }
            }
        });
    }


    private void obtenerCliente(int cotizacionId) {
        viewModel.getCliente(cotizacionId).observe(this, new Observer<table_clientes>() {
            @Override
            public void onChanged(table_clientes cliente) {
                if (cliente != null) {
                    // Actualiza los TextViews con los datos del cliente
                    tvNombreCliente.setText(cliente.getNombre_cliente());
                    tvRuc.setText(cliente.getDni_ruc()); // Si tienes el RUC en la tabla cliente
                    tvRazonSocial.setText(cliente.getRazon_social()); // Si tienes razón social en la tabla cliente
                }
            }
        });
    }

    private void obtenerCategoria(int cotizacionId) {
        viewModel.getCategoria(cotizacionId).observe(this, new Observer<table_categoria>() {
            @Override
            public void onChanged(table_categoria categoria) {
                if (categoria != null) {
                    // Actualiza los TextViews con la información de la categoría
                    tvCategoria.setText(categoria.getNombre_categoria());
                }
            }
        });
    }

    private void configurarPaddingVistaPrincipal() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()).toPlatformInsets();
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }
}
