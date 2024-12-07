package com.example.atlasestimates;

import android.content.Intent;
import android.graphics.Insets;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.atlasestimates.Cotizacion;  // Asegúrate de que la importación esté correcta
import com.google.android.material.button.MaterialButton;

import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class mostrardetalles extends AppCompatActivity {

    private CotizacionViewModel viewModel;
    private ImageView imagenCotizacion;
    private LinearLayout LayoutMedida, LayoutMaquina, LayoutRazon_Social, LayoutSubtotal, LayoutIGV, LayoutTotal1, LayoutTotal2,
             LayoutSupervision, LayoutPrecio;
    private TextView tvNombreCliente, tvTitulo, tvUbicacion, tvdescripcion, tvRuc, tvRazonSocial, tvCategoria,
            tvRequerimiento, tvSubTotal, tvIgv, tvTotal, textviewMetros, textviewprecio, mostrarMedida,
            Requerimiento, MostrarMaquina, Precio, Identificacion, MostrarTexto, MostrarSupervision, ED_Total2, Tv_Supervisiion,
             Tv_comentario, Tv_plazo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrardetalles);

        // Inicializar los TextViews
        tvNombreCliente = findViewById(R.id.nombre_cliente);
        imagenCotizacion = findViewById(R.id.imagen_cotizacion);
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
        textviewMetros = findViewById(R.id.mostrarMetros);
        textviewprecio = findViewById(R.id.mostrarPreciodetails);
        mostrarMedida = findViewById(R.id.mostrarmedida);
        Requerimiento = findViewById(R.id.requerimiento);
        MostrarMaquina = findViewById(R.id.mostraMaquina);
        LayoutMedida = findViewById(R.id.layoutmedida);
        LayoutMaquina = findViewById(R.id.layoutmaquina);
        Precio = findViewById(R.id.precio);
        Identificacion = findViewById(R.id.identificacion);
        LayoutRazon_Social = findViewById(R.id.layoutrazonsocial);
        MostrarTexto = findViewById(R.id.mostrartexto);
        MostrarSupervision = findViewById(R.id.mostrarsupervision);
        LayoutSubtotal = findViewById(R.id.layoutsubtotal);
        LayoutIGV = findViewById(R.id.layoutigv);
        ED_Total2 = findViewById(R.id.ed_total2);
        LayoutTotal1 = findViewById(R.id.layouttotal1);
        LayoutTotal2 = findViewById(R.id.layouttotal2);
        LayoutSupervision = findViewById(R.id.layoutsupervision);
        LayoutPrecio = findViewById(R.id.layoutprecio);
        Tv_Supervisiion = findViewById(R.id.supervision);
        Tv_comentario = findViewById(R.id.comentario_costos);
        Tv_plazo = findViewById(R.id.comentario_plazo);


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

        // Obtener la subcategoría y mostrar el valor correspondiente

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
                    ED_Total2.setText(cotizacion.getTotal_Servicio());
                    Tv_plazo.setText(cotizacion.getComentario_plazo());

                    // Cargar la imagen desde la URI
                    String imageUriString = cotizacion.getImagen(); // La URI que obtienes de la base de datos
                    if (imageUriString != null && !imageUriString.isEmpty()) {
                        Uri imageUri = Uri.parse(imageUriString);

                        Glide.with(mostrardetalles.this)
                                .load(new File(imageUriString)) // Si es una ruta absoluta
                                .into(imagenCotizacion);
                    }

                    // Recuperar la ruta del PDF desde la base de datos
                    String pdfPath = cotizacion.getPdfPath(); // Supongamos que tienes un campo pdfPath
                    if (pdfPath != null && !pdfPath.isEmpty()) {
                        // Convierte la ruta relativa a una ruta absoluta
                        File pdfFile = new File(getExternalFilesDir(null), pdfPath); // Esto convierte la ruta relativa a una ruta absoluta
                        setupVerPdfButton(pdfFile.getAbsolutePath());
                    }
                }
            }
        });
    }

    private void setupVerPdfButton(String pdfPath) {
        MaterialButton verPDFButton = findViewById(R.id.verpdf);
        verPDFButton.setVisibility(View.VISIBLE); // Mostrar el botón si hay PDF

        verPDFButton.setOnClickListener(v -> {
            // Mostrar el PopupMenu con opciones
            showPdfOptions(v, pdfPath);
        });
    }

    private void showPdfOptions(View view, String pdfPath) {
        PopupMenu popupMenu = new PopupMenu(mostrardetalles.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_view) {
                // Ver PDF
                viewPdf(pdfPath);
                return true;
            } else if (item.getItemId() == R.id.action_download) {
                // Descargar PDF
                downloadPdf(pdfPath);
                return true;
            } else if (item.getItemId() == R.id.action_share) {
                // Compartir PDF
                sharePdf(pdfPath);
                return true;
            }
            return false;
        });

        popupMenu.show();
    }


    private void viewPdf(String pdfPath) {
        File pdfFile = new File(pdfPath);
        if (pdfFile.exists()) {
            // Crear un Intent para abrir el archivo PDF
            Uri pdfUri = Uri.fromFile(pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(pdfUri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(Intent.createChooser(intent, "Ver PDF"));
        } else {
            Toast.makeText(mostrardetalles.this, "El archivo PDF no existe", Toast.LENGTH_SHORT).show();
        }
    }


    private void downloadPdf(String pdfPath) {
        // Lógica para descargar el PDF
        File pdfFile = new File(pdfPath);
        if (pdfFile.exists()) {
            // Puedes copiar el archivo a una ubicación específica en el dispositivo si lo deseas
            File downloadDir = new File(getExternalFilesDir(null), "Descargas");
            if (!downloadDir.exists()) {
                downloadDir.mkdir();
            }
            File downloadedFile = new File(downloadDir, pdfFile.getName());

            try {
                // Usar FileInputStream y FileOutputStream para copiar el archivo
                InputStream inputStream = new FileInputStream(pdfFile);
                OutputStream outputStream = new FileOutputStream(downloadedFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                inputStream.close();
                outputStream.close();

                Toast.makeText(mostrardetalles.this, "PDF descargado", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(mostrardetalles.this, "Error al descargar el PDF", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sharePdf(String pdfPath) {
        File file = new File(pdfPath);
        Uri uri = FileProvider.getUriForFile(this, "com.example.atlasestimates.fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Compartir PDF"));
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
                    double cantidad = 0;
                    for (table_detalleCotizacion detalle : detalles) {
                        // Aquí puedes extraer información de los detalles y actualizar los TextViews correspondientes
                        subtotal += detalle.getSubtotal();  // Suponiendo que `getSubTotal()` esté en table_detalleCotizacion
                        igv += detalle.getIgv();
                        cantidad += detalle.getCantidad();
                        // Suponiendo que `getIgv()` esté en table_detalleCotizacion
                    }
                    // Actualizar los TextViews para mostrar los totales de los detalles
                    tvSubTotal.setText("S/ " + subtotal);  // Total de subtotales
                    tvIgv.setText("S/ " + igv);
                    textviewMetros.setText("" + cantidad);// Total de IGV
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
                        tvRequerimiento.setText(item.getNombre_Item());
                        textviewprecio.setText(item.getPrecio());

                        // Nueva lógica para establecer la unidad de medida según el nombre del ítem
                        String nombreItem = item.getNombre_Item();
                        if (nombreItem != null) {
                            switch (nombreItem) {
                                case "Cercos prefabricados":
                                case "Cerco cabeza caballo":
                                    mostrarMedida.setText("Metros:");
                                    LayoutMaquina.setVisibility(View.GONE);
                                    LayoutTotal2.setVisibility(View.GONE);
                                    LayoutSupervision.setVisibility(View.GONE);
                                    break;
                                case "Block de concreto":
                                case "Poste de concreto":
                                    mostrarMedida.setText("Unidades:");
                                    LayoutMaquina.setVisibility(View.GONE);
                                    LayoutTotal2.setVisibility(View.GONE);
                                    LayoutSupervision.setVisibility(View.GONE);
                                    break;
                                case "Ingenieria":
                                case "Arquitectura":

                                    MostrarMaquina.setText(item.getMedida());
                                    LayoutMedida.setVisibility(View.GONE);
                                    MostrarTexto.setText("Medida:");
                                    Precio.setText("Desarrollo de Proyecto:");
                                    MostrarSupervision.setText(item.getSupervision());
                                    LayoutSubtotal.setVisibility(View.GONE);
                                    LayoutTotal1.setVisibility(View.GONE);
                                    LayoutIGV.setVisibility(View.GONE);
                                    break;

                                case "Global MP":
                                    Requerimiento.setText("Medida:");
                                    mostrarMedida.setText("Maquina:");
                                    Precio.setText("Cantidad:");
                                    MostrarMaquina.setText(item.getMaquina());
                                    LayoutTotal1.setVisibility(View.GONE);
                                    LayoutIGV.setVisibility(View.GONE);
                                    LayoutSupervision.setVisibility(View.GONE);
                                    LayoutSubtotal.setVisibility(View.GONE);
                                    LayoutMedida.setVisibility(View.GONE);
                                    break;

                                case "Alquiler":
                                    Tv_Supervisiion.setText("Coto por Hora:");
                                    Requerimiento.setText("Medida:");
                                    mostrarMedida.setText("Movilización / Desmovilización:");
                                    MostrarSupervision.setText(item.getSupervision());
                                    Precio.setText("Horas:");
                                    MostrarMaquina.setText(item.getMaquina());
                                    LayoutTotal2.setVisibility(View.GONE);

                                    break;

                                case "Unidad":
                                case "Global":
                                    Requerimiento.setText("Medida:");
                                    LayoutMedida.setVisibility(View.GONE);
                                    LayoutMaquina.setVisibility(View.GONE);
                                    LayoutSubtotal.setVisibility(View.GONE);
                                    LayoutTotal1.setVisibility(View.GONE);
                                    LayoutMedida.setVisibility(View.GONE);
                                    LayoutIGV.setVisibility(View.GONE);
                                    LayoutSupervision.setVisibility(View.GONE);
                                    LayoutPrecio.setVisibility(View.GONE);
                                    break;

                                case "Coberturas":
                                case "Puertas":
                                case "Portones":
                                case "Barandas":
                                case "Escaleras":
                                    LayoutMedida.setVisibility(View.GONE);
                                    LayoutMaquina.setVisibility(View.GONE);
                                    LayoutSubtotal.setVisibility(View.GONE);
                                    LayoutTotal1.setVisibility(View.GONE);
                                    LayoutMedida.setVisibility(View.GONE);
                                    LayoutIGV.setVisibility(View.GONE);
                                    LayoutSupervision.setVisibility(View.GONE);
                                    LayoutPrecio.setVisibility(View.GONE);
                                    break;

                                case "Agua potable":
                                case "Agua no potable":
                                    mostrarMedida.setText("M3 Cisterna:");
                                    LayoutMaquina.setVisibility(View.GONE);
                                    LayoutSubtotal.setVisibility(View.GONE);
                                    LayoutTotal1.setVisibility(View.GONE);
                                    LayoutIGV.setVisibility(View.GONE);
                                    LayoutSupervision.setVisibility(View.GONE);
                                    LayoutPrecio.setVisibility(View.GONE);
                                    break;
                                case "Medida Global":
                                    Requerimiento.setText("Medida:");
                                    LayoutMaquina.setVisibility(View.GONE);
                                    LayoutSubtotal.setVisibility(View.GONE);
                                    LayoutTotal1.setVisibility(View.GONE);
                                    LayoutMedida.setVisibility(View.GONE);
                                    LayoutIGV.setVisibility(View.GONE);
                                    LayoutSupervision.setVisibility(View.GONE);
                                    LayoutPrecio.setVisibility(View.GONE);
                                    break;
                                case "Generador (10 KW)":
                                case "Rotomartillo Demoledor (17 KW)":
                                case "Rotomartillo Demoledor (11 KW)":
                                case "Cortadora de Pavimento":
                                case "Mezcladora":
                                case "Vibrador Concreto":
                                    Requerimiento.setText("Equipo:");
                                    mostrarMedida.setText("Dias:");
                                    LayoutMaquina.setVisibility(View.GONE);
                                    LayoutTotal2.setVisibility(View.GONE);
                                    LayoutSupervision.setVisibility(View.GONE);
                                    break;

                                // Agrega más casos según sea necesario para otros ítems
                                default:
                                    mostrarMedida.setText("Medida:"); // Texto predeterminado
                            }
                        }
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
                    String identificacion = cliente.getDni_ruc();

                    // Establecer datos del cliente
                    tvNombreCliente.setText(cliente.getNombre_cliente());
                    tvRuc.setText(identificacion);

                    // Lógica para DNI (8 dígitos)
                    if (identificacion.length() == 8) {
                        Identificacion.setText("DNI");
                        LayoutRazon_Social.setVisibility(View.GONE);  // Ocultar Razón Social
                    }
                    // Lógica para RUC (11 dígitos)
                    else if (identificacion.length() == 11) {
                        Identificacion.setText("RUC");
                        LayoutRazon_Social.setVisibility(View.VISIBLE);
                        tvRazonSocial.setText(cliente.getRazon_social());
                    }
                    // Por si acaso no cumple ninguna condición
                    else {
                        Identificacion.setText("ID");
                        tvRazonSocial.setVisibility(View.GONE);
                    }
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
                    Tv_comentario.setText(categoria.getDescripcion_categoria());

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
