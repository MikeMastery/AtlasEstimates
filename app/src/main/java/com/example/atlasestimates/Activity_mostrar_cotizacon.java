package com.example.atlasestimates;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Activity_mostrar_cotizacon extends AppCompatActivity {


    private TextView textViewTitulo, textviewCliente, textviewFecha, textviewRequerimiento, textviewDescripcion, mostrartotalInAR;
    private TextView textviewCategoria, textviewUnidadMedida, textviewPrecio, textviewTotal, textviewTotalIGV,
            textviewSubTotal, textviewIdentificacion, textview_mostrarUbicacion, mostrarMedida, mostrarTipoIden,
            textviewRazoncial, textviewMostrarRazon, tvmostrarvalor, textmostrarsupervision, textmostrarsupervisionSINO,
            totaldeIngenieriayArquitectura, textviewTotalTopografia, Mostrar_Maquina, ed_comentario, Maquina;
    private EditText editextImagen, Ed_plazoEntrega;
    private String imagePath;
    private AppDatabase db;
    private ImageButton imageButtonPDF;
    private ImageView imageViewPDFGreen;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private boolean isPDFGenerated = false;
    private boolean pdfGenerated = false;
    private String pdfPath = null;
    private CotizacionDao cotizacionDao;
    private Button btnGuardarCotizacion;
    private ClienteDao clienteDao;
    private DetalleCotizacionDao detalleDao;
    private CategoriaDao categoriaDao;
    private ItemsDao itemsDao;
    private LinearLayout layoutTotal, layoutIGV, layoutSubTotal, ocultarRazonSocial, ocultarTotalServicios, layoutMaquina, layoutMetrosUnidadees, layoutprecio, layoutsupervision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cotizacon);


        // Configurar ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Habilita el botón
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.atlas2); // Establece tu ícono
        }


        // Inicializar la base de datos
        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "atlas_estimates_db")
                .allowMainThreadQueries() // Solo para pruebas, en producción usar hilos
                .build();
        cotizacionDao = db.cotizacionDao();
        clienteDao = db.clienteDao();
        detalleDao = db.detalleCotizacionDao();
        categoriaDao = db.categoriaDao();
        itemsDao = db.itemsDao();


        // Inicializar vistas
        inicializarVistas();

        // Recuperar y mostrar datos temporales
        mostrarDatosTemporales();

        // Configurar botón de guardado
        btnGuardarCotizacion = findViewById(R.id.guardar_cotización);
        btnGuardarCotizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCotizacionEnRoom();
            }
        });



        // Configurar el ImageButton para mostrar el menú
        configurarImageButton();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Crear un Intent para ir al MainActivity
            Intent intent = new Intent(this, MainActivity.class);

            // Agregar banderas para limpiar la pila de actividades
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            // Asegúrate de que se abra directamente en el HomeFragment
            intent.putExtra("fragment", "home");

            // Iniciar la actividad
            startActivity(intent);

            // Finalizar la actividad actual
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void inicializarVistas() {
        textViewTitulo = findViewById(R.id.textViewTitulo);
        textviewCliente = findViewById(R.id.nombre_cliente);
        textviewFecha = findViewById(R.id.fecha_coti);
        textviewRequerimiento = findViewById(R.id.producto);
        editextImagen = findViewById(R.id.imagen_cotizacion);
        textviewDescripcion = findViewById(R.id.descripcion_cotizacion);
        textviewCategoria = findViewById(R.id.categoriaMostrar);
        textviewUnidadMedida = findViewById(R.id.un_medida);
        textviewPrecio = findViewById(R.id.mostrar_precio);
        textviewTotal = findViewById(R.id.ed_total);
        textviewTotalIGV = findViewById(R.id.mostrar_igv);
        textviewSubTotal = findViewById(R.id.ed_SubTotal);
        textviewIdentificacion = findViewById(R.id.tv_mostrar_ident);
        textview_mostrarUbicacion = findViewById(R.id.tv_mostrar_ubi);
        mostrarMedida = findViewById(R.id.label_unidad_medida);
        mostrarTipoIden = findViewById(R.id.mostrar_tipo_ident);
        textviewRazoncial = findViewById(R.id.mostrar_razonSocial);
        textviewMostrarRazon = findViewById(R.id.tv_mostrar_razonSocial);
        tvmostrarvalor = findViewById(R.id.label_mostrar_precio);
        textmostrarsupervision = findViewById(R.id.tv_mostrarsupervicion);
        textmostrarsupervisionSINO = findViewById(R.id.mostrar_supersion);
        mostrartotalInAR = findViewById(R.id.totalIngeArqui);
        totaldeIngenieriayArquitectura = findViewById(R.id.tvtotalInAr);
        layoutTotal = findViewById(R.id.layoutTotal);
        layoutIGV = findViewById(R.id.layoutIGV);
        layoutSubTotal = findViewById(R.id.layoutSubTotal);
        ocultarRazonSocial = findViewById(R.id.ocultarrazon);
        ocultarTotalServicios = findViewById(R.id.ocultartotalServicios);
        textviewTotalTopografia = findViewById(R.id.MedidaTopografia);
        layoutprecio = findViewById(R.id.tv_precio);
        layoutsupervision = findViewById(R.id.tv_super);
        layoutMetrosUnidadees = findViewById(R.id.tv_metrounidades);
        Mostrar_Maquina = findViewById(R.id.mostrar_maquina);
        ed_comentario = findViewById(R.id.cajaComentario);
        layoutMaquina = findViewById(R.id.tv_maquina);
        Ed_plazoEntrega = findViewById(R.id.plazoentrega);
        Maquina = findViewById(R.id.maquina);
    }



    private void mostrarDatosTemporales() {
        Cotizacion cotizacion = (Cotizacion) getIntent().getSerializableExtra("cotizacion");
        if (cotizacion != null) {
            textViewTitulo.setText(cotizacion.getNombreCotizacion());
            textviewCliente.setText(cotizacion.getNombreCliente());
            textview_mostrarUbicacion.setText(cotizacion.getUbicacion());
            String sub = cotizacion.getIdentificacion();

            if ("DNI".equals(sub)) {
                mostrarTipoIden.setText("DNI:");
                textviewIdentificacion.setText(cotizacion.getDni());
                ocultarRazonSocial.setVisibility(View.GONE);
            } else if ("RUC".equals(sub)) {
                mostrarTipoIden.setText("RUC:");
                textviewIdentificacion.setText(cotizacion.getRuc());
                textviewMostrarRazon.setText(cotizacion.getRazonsocial());
                textviewRazoncial.setVisibility(View.VISIBLE);
            } else {
                return;
            }

            textviewFecha.setText(cotizacion.getFecha());
            textviewRequerimiento.setText(cotizacion.getProducto());
            textviewDescripcion.setText(cotizacion.getDescripcion());
            textviewCategoria.setText(cotizacion.getCategoria());
            ed_comentario.setText(cotizacion.getcoementarioTopografia());
            Ed_plazoEntrega.setText(cotizacion.getPlazoEntrega());

            // Ajustes condicionales basados en la subcategoría
            String subcategoria = cotizacion.getProducto(); // Usar el campo adecuado para la subcategoría

            // Verificar si la subcategoría es una personalizada (vacía o no coincide con ninguna opción predefinida)
            if (subcategoria == null || subcategoria.isEmpty() || !esSubcategoriaPredefinida(subcategoria)) {
                // Mostrar los campos de "Metros Lineales" y "Precio"
                mostrarMedida.setText("Cantidad:");
                textviewUnidadMedida.setText(cotizacion.getMetrosLineales());
                textviewPrecio.setText("S/ " + cotizacion.getPrecio());
                // Mostrar los layouts relacionados
                layoutMetrosUnidadees.setVisibility(View.VISIBLE);
                layoutprecio.setVisibility(View.VISIBLE);
                layoutTotal.setVisibility(View.VISIBLE);
                layoutIGV.setVisibility(View.VISIBLE);
                layoutSubTotal.setVisibility(View.VISIBLE);
                textmostrarsupervision.setVisibility(View.GONE);
                ocultarTotalServicios.setVisibility(View.GONE);
                layoutsupervision.setVisibility(View.GONE);
                layoutMaquina.setVisibility(View.GONE);
            } else {
                // Procesar las subcategorías predefinidas
                procesarSubcategoria(cotizacion, subcategoria);
            }


            textviewTotal.setText("S/ " + cotizacion.getTotal());
            textviewTotalIGV.setText("S/ " + cotizacion.getIgv());
            textviewSubTotal.setText("S/ " + cotizacion.getSubTotal());


            manejarImagenCotizacion(cotizacion);
        }
    }

    // Método para verificar si la subcategoría es predefinida
    private boolean esSubcategoriaPredefinida(String subcategoria) {
        // Aquí puedes incluir todas las subcategorías conocidas que están en las opciones
        String[] subcategoriasPredefinidas = {
                "Block de concreto", "Poste de concreto", "Cercos prefabricados",
                "Cerco cabeza caballo", "Generador (10 KW)", "Rotomartillo Demoledor (17 K)",
                "Rotomartillo Demoledor (11 K)", "Cortadora Pavimento", "Mezcladora",
                "Vibrador Concreto", "Coberturas", "Puertas", "Portones", "Barandas",
                "Escaleras", "Ingenieria", "Arquitectura", "Unidad", "Global", "Medida Global",
                "Agua potable", "Agua no potable", "Alquiler", "Global MP"
        };

        // Compara la subcategoría con las predefinidas
        for (String categoria : subcategoriasPredefinidas) {
            if (categoria.equals(subcategoria)) {
                return true;
            }
        }
        return false;
    }

    // Método para procesar las subcategorías predefinidas
    private void procesarSubcategoria(Cotizacion cotizacion, String subcategoria) {
        if ("Block de concreto".equals(subcategoria) || "Poste de concreto".equals(subcategoria)) {
            // Muestra unidades en lugar de metros
            mostrarMedida.setText("Unidades:");
            textviewUnidadMedida.setText(cotizacion.getHorasMaquina());
            textviewPrecio.setText("S/ " + cotizacion.getPrecioHora());
            textmostrarsupervision.setVisibility(View.GONE);
            ocultarTotalServicios.setVisibility(View.GONE);
            layoutsupervision.setVisibility(View.GONE);
            layoutMaquina.setVisibility(View.GONE);


        } else if ("Cercos prefabricados".equals(subcategoria) || "Cerco cabeza caballo".equals(subcategoria)) {
            // Muestra metros en lugar de metros/unidades
            mostrarMedida.setText("Metros:");
            textviewUnidadMedida.setText(cotizacion.getMetrosLineales());
            textviewPrecio.setText("S/ " + cotizacion.getPrecio());
            textmostrarsupervision.setVisibility(View.GONE);
            ocultarTotalServicios.setVisibility(View.GONE);
            layoutsupervision.setVisibility(View.GONE);
            layoutMaquina.setVisibility(View.GONE);

        } else if ("Generador (10 KW)".equals(subcategoria) || "Rotomartillo Demoledor (17 K)".equals(subcategoria) ||
                "Rotomartillo Demoledor (11 K)".equals(subcategoria) || "Cortadora Pavimento".equals(subcategoria) ||
                "Mezcladora".equals(subcategoria) || "Vibrador Concreto".equals(subcategoria)) {
            mostrarMedida.setText("Dias:");
            textviewUnidadMedida.setText(cotizacion.getEquipoMenor());
            textviewPrecio.setText("S/ " + cotizacion.getPrecioEquiposMenores());
            textmostrarsupervision.setVisibility(View.GONE);
            ocultarTotalServicios.setVisibility(View.GONE);
            layoutsupervision.setVisibility(View.GONE);
            layoutMaquina.setVisibility(View.GONE);

        } else if ("Coberturas".equals(subcategoria) || "Puertas".equals(subcategoria) ||
                "Portones".equals(subcategoria) || "Barandas".equals(subcategoria) ||
                "Escaleras".equals(subcategoria)) {
            mostrartotalInAR.setText("S/ " + cotizacion.getCampoEstructura());
            textmostrarsupervision.setVisibility(View.GONE);
            layoutsupervision.setVisibility(View.GONE);
            layoutprecio.setVisibility(View.GONE);
            layoutMetrosUnidadees.setVisibility(View.GONE);
            layoutTotal.setVisibility(View.GONE);
            layoutIGV.setVisibility(View.GONE);
            layoutSubTotal.setVisibility(View.GONE);
            layoutMaquina.setVisibility(View.GONE);


        } else if ("Ingenieria".equals(subcategoria) || "Arquitectura".equals(subcategoria)) {
            mostrarMedida.setText("Tipo de Medida:");
            textviewUnidadMedida.setText(cotizacion.getMedida());
            textviewPrecio.setText(cotizacion.getDesarrolloProyecto());
            tvmostrarvalor.setText(cotizacion.gettextodesarrollo());
            textmostrarsupervisionSINO.setText(cotizacion.getsupersionSINO());
            mostrartotalInAR.setText("S/ " + cotizacion.gettotalInAr());
            // Ocultar los LinearLayouts completos en lugar de solo los TextViews
            layoutTotal.setVisibility(View.GONE);
            layoutIGV.setVisibility(View.GONE);
            layoutSubTotal.setVisibility(View.GONE);
            layoutMaquina.setVisibility(View.GONE);

        } else if ("Unidad".equals(subcategoria) || "Global".equals(subcategoria)) {
            textviewTotalTopografia.setText("Medida:");
            mostrartotalInAR.setText("S/ " + cotizacion.getTotalTopogrgafia());
            layoutMetrosUnidadees.setVisibility(View.GONE);
            layoutprecio.setVisibility(View.GONE);
            layoutsupervision.setVisibility(View.GONE);
            layoutTotal.setVisibility(View.GONE);
            layoutIGV.setVisibility(View.GONE);
            layoutSubTotal.setVisibility(View.GONE);
            layoutMaquina.setVisibility(View.GONE);

        } else if ("Medida Global".equals(subcategoria)) {
            textviewTotalTopografia.setText("Medida:");
            mostrartotalInAR.setText("S/ " + cotizacion.getCampoConstruccionObra());
            layoutMetrosUnidadees.setVisibility(View.GONE);
            layoutprecio.setVisibility(View.GONE);
            layoutsupervision.setVisibility(View.GONE);
            layoutTotal.setVisibility(View.GONE);
            layoutIGV.setVisibility(View.GONE);
            layoutSubTotal.setVisibility(View.GONE);
            layoutMaquina.setVisibility(View.GONE);

        } else if ("Agua potable".equals(subcategoria) || "Agua no potable".equals(subcategoria)) {
            mostrarMedida.setText("Metros Cubicos:");
            textviewUnidadMedida.setText(cotizacion.getCantidaAgua());
            textviewPrecio.setVisibility(View.GONE);
            tvmostrarvalor.setVisibility(View.GONE);
            layoutsupervision.setVisibility(View.GONE);
            mostrartotalInAR.setText("S/ " + cotizacion.getCampoTotalAgua());
            // Ocultar los LinearLayouts completos en lugar de solo los TextViews
            layoutTotal.setVisibility(View.GONE);
            layoutIGV.setVisibility(View.GONE);
            layoutSubTotal.setVisibility(View.GONE);
            layoutMaquina.setVisibility(View.GONE);

        } else if ("Alquiler".equals(subcategoria)) {
            textviewTotalTopografia.setText("Medida:");
            Mostrar_Maquina.setText(cotizacion.getMaquina());
            textviewPrecio.setText(cotizacion.getHorasAlquiler());
            textviewUnidadMedida.setText(cotizacion.getMovilizacion());
            mostrarMedida.setText("Movilización / Desmovilización:");
            tvmostrarvalor.setText("Horas Alquiladas:");
            textmostrarsupervision.setText("Costo Hora:");
            textmostrarsupervisionSINO.setText("S/ " + cotizacion.getCostoHora());
            ocultarTotalServicios.setVisibility(View.GONE);

        } else if ("Global MP".equals(subcategoria)) {
            textviewTotalTopografia.setText("Medida:");
            Mostrar_Maquina.setText(cotizacion.getMaquina());
            textviewPrecio.setText(cotizacion.getCantidadMaquinaGlobal());
            tvmostrarvalor.setText("Cantidad:");
            mostrartotalInAR.setText("S/ " + cotizacion.getCostoMaquinaGlobal());
            layoutsupervision.setVisibility(View.GONE);
            layoutTotal.setVisibility(View.GONE);
            layoutIGV.setVisibility(View.GONE);
            layoutSubTotal.setVisibility(View.GONE);
            layoutMetrosUnidadees.setVisibility(View.GONE);


        } else {
            return;

        }
        // Aquí puedes agregar más condiciones para las demás subcategorías predefinidas...
    }

    private void manejarImagenCotizacion(Cotizacion cotizacion) {
        String imageUriString = cotizacion.getImagenUri();
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                int originalWidth = bitmap.getWidth();
                int originalHeight = bitmap.getHeight();
                int resizedWidth;
                int resizedHeight;

                if (originalHeight > originalWidth) {
                    int desiredHeight = 535;
                    resizedHeight = desiredHeight;
                    resizedWidth = (int) ((float) originalWidth * ((float) desiredHeight / originalHeight));
                } else {
                    int desiredWidth = 550;
                    resizedWidth = desiredWidth;
                    resizedHeight = (int) ((float) originalHeight * ((float) desiredWidth / originalWidth));
                }

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, true);
                Drawable drawable = new BitmapDrawable(getResources(), resizedBitmap);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                editextImagen.setCompoundDrawables(null, null, drawable, null);
                imagePath = saveImage(resizedBitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



    private void configurarImageButton() {
        // Inicializar los botones
        imageButtonPDF = findViewById(R.id.conpartir_descargarPDF); // Botón rojo
        imageViewPDFGreen = findViewById(R.id.icon2); // Botón verde (inicialmente oculto)

        // Ocultar el botón verde al inicio
        imageViewPDFGreen.setVisibility(View.GONE);

        // Configurar el botón rojo (generar PDF)
        imageButtonPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generar el PDF
                createPDFWithIText();

                // Cambiar visibilidad de los botones
                imageButtonPDF.setVisibility(View.GONE); // Ocultar el rojo
                imageViewPDFGreen.setVisibility(View.VISIBLE); // Mostrar el verde
            }
        });

        // Configurar el botón verde (mostrar PopupMenu)
        imageViewPDFGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el PopupMenu
                PopupMenu popupMenu = new PopupMenu(Activity_mostrar_cotizacon.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(android.view.MenuItem item) {
                        if (item.getItemId() == R.id.action_download) {
                            downloadPDF();
                            return true;
                        } else if (item.getItemId() == R.id.action_share) {
                            sharePDF();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }


    private void guardarCotizacionEnRoom() {
        try {
            // Verificar si el PDF ha sido generado
            if (pdfPath == null || pdfPath.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Generar PDF")
                        .setMessage("Por favor, genere el PDF antes de guardar la cotización.")
                        .setPositiveButton("Entendido", (dialog, which) -> dialog.dismiss())
                        .show();
                return;
            }

            // Crear objeto de cotización
            table_cotizacion nuevaCotizacion = new table_cotizacion();
            nuevaCotizacion.setTitulo(textViewTitulo.getText().toString());
            nuevaCotizacion.setFecha(textviewFecha.getText().toString());
            nuevaCotizacion.setDescripcion(textviewDescripcion.getText().toString());
            nuevaCotizacion.setUbicacion(textview_mostrarUbicacion.getText().toString());
            nuevaCotizacion.setTotal_Servicio(mostrartotalInAR.getText().toString());
            nuevaCotizacion.setComentario_plazo(Ed_plazoEntrega.getText().toString());
            nuevaCotizacion.setEstado("Pendiente"); // Configura el estado predeterminado

            // Añadir la ruta del PDF a la cotización
            nuevaCotizacion.setPdfPath(pdfPath);


            String totalText = textviewTotal.getText().toString().replaceAll("[^\\d.]", "");
            double total = totalText.isEmpty() ? 0.0 : Double.parseDouble(totalText);  // Valor por defecto 0.0
            nuevaCotizacion.setTotal(total);

            if (imagePath != null && !imagePath.isEmpty()) {
                nuevaCotizacion.setImagen(imagePath);

            }

            // Crear objeto de cliente (solo con nombre)
            table_clientes nuevoCliente = new table_clientes();
            nuevoCliente.setNombre_cliente(textviewCliente.getText().toString());
            nuevoCliente.setDni_ruc(textviewIdentificacion.getText().toString());
            nuevoCliente.setRazon_social(textviewMostrarRazon.getText().toString());

            // Crear objeto de detalle de cotización
            table_detalleCotizacion nuevoDetalle = new table_detalleCotizacion();

            // Verificamos si el campo Subtotal es visible antes de obtener su valor
            String subtotalText = textviewSubTotal.getVisibility() == View.VISIBLE ? textviewSubTotal.getText().toString().replaceAll("[^\\d.]", "") : "";
            double subtotal = subtotalText.isEmpty() ? 0.0 : Double.parseDouble(subtotalText);
            nuevoDetalle.setSubtotal(subtotal);

            // Verificamos si el campo IGV es visible antes de obtener su valor
            String igvText = textviewTotalIGV.getVisibility() == View.VISIBLE ? textviewTotalIGV.getText().toString().replaceAll("[^\\d.]", "") : "";
            double igv = igvText.isEmpty() ? 0.0 : Double.parseDouble(igvText);
            nuevoDetalle.setIgv(igv);

            // Verificamos si el campo Unidad de Medida es visible antes de obtener su valor
            String cantidad = textviewUnidadMedida.getVisibility() == View.VISIBLE ? textviewUnidadMedida.getText().toString().replaceAll("[^\\d.]", "") : "";
            double cantidadValor = cantidad.isEmpty() ? 0.0 : Double.parseDouble(cantidad);
            nuevoDetalle.setCantidad(cantidadValor);

            // Crear objeto de categoría
            table_categoria nuevaCategoria = new table_categoria();
            nuevaCategoria.setNombre_categoria(textviewCategoria.getText().toString());
            nuevaCategoria.setDescripcion_categoria(ed_comentario.getText().toString());

            // Crear objeto de item
            table_items nuevoItem = new table_items();

            // Verificamos si el campo Requerimiento es visible antes de obtener su valor
            String requerimiento = textviewRequerimiento.getVisibility() == View.VISIBLE ? textviewRequerimiento.getText().toString() : "";
            nuevoItem.setNombre_Item(requerimiento);

            String medida = textviewUnidadMedida.getVisibility() == View.VISIBLE ? textviewUnidadMedida.getText().toString() : "";
            nuevoItem.setMedida(medida);

            String maquina = Mostrar_Maquina.getVisibility() == View.VISIBLE ? Mostrar_Maquina.getText().toString() : "";
            nuevoItem.setMaquina(maquina);

            // Verificamos si el campo Precio es visible antes de obtener su valor
            String precio = textviewPrecio.getVisibility() == View.VISIBLE ? textviewPrecio.getText().toString() : "";
            nuevoItem.setPrecio(precio);

            // Verificamos si el campo Supervision es visible antes de obtener su valor
            String supervision = textmostrarsupervisionSINO.getVisibility() == View.VISIBLE ? textmostrarsupervisionSINO.getText().toString() : "";
            nuevoItem.setSupervision(supervision);

            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Guardar cliente usando ClienteDao
                        long clienteId = clienteDao.insert(nuevoCliente);

                        // Asignar ID del cliente a la cotización
                        nuevaCotizacion.setId_cliente((int) clienteId);

                        // Guardar cotización usando CotizacionDao
                        long cotizacionId = cotizacionDao.insert(nuevaCotizacion);

                        // Guardar categoría usando CategoriaDao
                        long categoriaId = categoriaDao.insert(nuevaCategoria);

                        // Asignar el ID de la categoría al item (si aplica)
                        nuevoItem.setId_categoria((int) categoriaId);

                        // Guardar item usando ItemsDao
                        long itemId = itemsDao.insert(nuevoItem);

                        // Asignar el ID del item al detalle de la cotización
                        nuevoDetalle.setId_items((int) itemId);

                        // Asignar ID de la cotización al detalle
                        nuevoDetalle.setId_cotizacion((int) cotizacionId);

                        // Guardar detalle usando DetalleCotizacionDao
                        detalleDao.insert(nuevoDetalle);

                        // ** NOTIFICACIÓN DESPUÉS DE GUARDAR LA COTIZACIÓN **
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Mostrar mensaje de éxito
                                Toast toast = Toast.makeText(Activity_mostrar_cotizacon.this,
                                        "Cotización guardada exitosamente",
                                        Toast.LENGTH_SHORT);
                                toast.show();



                                // Reducir el tiempo de duración del Toast
                                new Thread(() -> {
                                    try {
                                        // Hacer que el Toast dure solo 2 segundos
                                        Thread.sleep(1500);  // 1500 ms (1.5 segundos)
                                        toast.cancel();  // Cancelar el Toast
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }).start();

                            }
                        });
                    } catch (Exception e) {
                        final String errorMessage = e.getMessage();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Activity_mostrar_cotizacon.this,
                                        "Error al guardar: " + errorMessage,
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });
            // ... (rest of your existing code remains the same)
        } catch (Exception e) {
            Toast.makeText(this, "Error al preparar la cotización: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    // Método para crear el PDF con iText7
    public String createPDFWithIText() {

        String pdfPath = obtenerRutaDelPDF(); // Obtener la ruta dinámica
        File file = new File(pdfPath);

        try {

            // Crear las carpetas si no existen
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Inicializar PdfWriter y PdfDocument
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);

            // Crear la imagen desde los recursos
            Image headerImage = new Image(ImageDataFactory.create(inputStreamToByteArray(getResources().openRawResource(R.drawable.encabezado))));
            float pageWidth = PageSize.A4.getWidth();
            float pageHeight = PageSize.A4.getHeight();

            // Ajustar el tamaño de la imagen
            float imageWidth = pageWidth - 130; // Ajusta el ancho para que ocupe más espacio horizontal (ajusta el valor según lo que desees)
            float imageHeight = 95f; // Ajusta la altura para que sea más corta

            // Configurar la posición y tamaño de la imagen
            headerImage.setFixedPosition((pageWidth - imageWidth) / 2, pageHeight - imageHeight - 10);
            headerImage.setWidth(imageWidth);
            headerImage.setHeight(imageHeight);

            document.add(headerImage);

            // Crear título
            String tituloTexto = textViewTitulo.getText().toString();
            Paragraph title = new Paragraph(tituloTexto)
                    .setBold()
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(85); // Ajusta este valor para bajar más el título
            document.add(title);

// Calcular el ancho de la línea basado en el tamaño del texto
            float fontSize = 16; // Tamaño de fuente del título
            float lineWidthPerCharacter = fontSize * 0.6f; // Aproximadamente, dependiendo de la fuente
            float calculatedWidth = tituloTexto.length() * lineWidthPerCharacter;

// Crear una línea debajo del título con ancho dinámico
            LineSeparator lineSeparator = new LineSeparator(new SolidLine());
            lineSeparator.setWidth(calculatedWidth);
            lineSeparator.setMarginTop(-9); // Ajusta la distancia entre el título y la línea
            lineSeparator.setHorizontalAlignment(HorizontalAlignment.CENTER); // Centrar la línea
            document.add(lineSeparator);

            // Obtén los datos de los TextViews
            String cliente = textviewCliente.getText().toString();
            String fecha = textviewFecha.getText().toString();
            String identificacion = textviewIdentificacion.getText().toString();
            String ubicacion = textview_mostrarUbicacion.getText().toString();

// Verifica si el campo de razón social está vacío y asigna "NO" si es necesario
            String razonSocial = textviewMostrarRazon.getText().toString().isEmpty() ? "No" : textviewMostrarRazon.getText().toString();
            String tipoIdentRazonSocial = textviewRazoncial.getText().toString();

// Inicializar la variable para la cadena de datos del cliente
            String datosCliente = "";

// Validación de la longitud del número de identificación
            if (identificacion.length() == 8) {
                // Si tiene 8 dígitos, es un DNI: Mostrar cliente y ocultar razón social
                if (!cliente.isEmpty()) {
                    datosCliente = "Cliente: " + cliente + "\n";
                }
                datosCliente += "Fecha: " + fecha + "\n" +
                        "DNI: " + identificacion + "\n" +
                        "Ubicación: " + ubicacion;
            } else if (identificacion.length() == 11) {
                // Si tiene 11 dígitos, es un RUC: Mostrar razón social después del RUC
                if (!razonSocial.isEmpty() && !"No".equals(razonSocial)) {
                    datosCliente = "RUC: " + identificacion + "\n" +
                            tipoIdentRazonSocial + ": " + razonSocial + "\n";
                }
                datosCliente += "Fecha: " + fecha + "\n" +
                        "Ubicación: " + ubicacion;
            }

// Crear el Paragraph con los datos del cliente o razón social
            Paragraph rightAlignedData = new Paragraph(datosCliente)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginLeft(28)
                    .setFontSize(11)
                    .setMarginTop(16);

// Añadir el Paragraph al documento
            document.add(rightAlignedData);



            //Añadir un breve texto de agradecimiento
            Paragraph textAgradecimiento = new Paragraph(
                    "Es grato dirigirme a usted, para saludarle, agradecer la invitación y presentar nuestra propuesta de vuestro servicio: ")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginLeft(28)
                    .setFontSize(12)
                    .setMarginTop(10); // Ajusta este valor para bajar más el texto

            document.add(textAgradecimiento);

            // Agregar la imagen centrada debajo del texto de agradecimiento
            if (imagePath != null) {
                Image centeredImage = new Image(ImageDataFactory.create(imagePath));

                // Ajustar el tamaño de la imagen
                float maxWidth = 175f; // Ancho máximo de la imagen
                float maxHeight = 140f; // Altura máxima de la imagen
                float originalWidth = centeredImage.getImageWidth();
                float originalHeight = centeredImage.getImageHeight();

                // Calcular el ratio para mantener la proporción
                float ratio = Math.min(maxWidth / originalWidth, maxHeight / originalHeight);
                float newWidth = originalWidth * ratio;
                float newHeight = originalHeight * ratio;

                // Establecer dimensiones
                centeredImage.setWidth(newWidth);
                centeredImage.setHeight(newHeight);

                // Centrar la imagen
                Paragraph imageParagraph = new Paragraph().add(centeredImage)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginTop(5); // Espacio antes de la imagen

                document.add(imageParagraph);
            }

            // Tabla en el centro con ajuste dinámico
            float maxWidth = 455f; // Ancho máximo permitido
            Table table = new Table(2); // 2 columnas
            table.setWidth(UnitValue.createPercentValue(100)); // La tabla se ajustará al 100% del contenido
            table.setMaxWidth(maxWidth); // Pero no superará este ancho máximo
            table.setMarginTop(9);
            table.setFixedLayout();

            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            // Usar el campo adecuado para la subcategoría

            // Recuperar el texto actual de mostrarMedida
            String unidadMedidaTexto = mostrarMedida.getText().toString();

            String requerimiento = textviewTotalTopografia.getText().toString();

            String precio = tvmostrarvalor.getText().toString();

            String maquina = Maquina.getText().toString();

            String supervision = textmostrarsupervision.getText().toString();

// Añadir datos a la tabla
            addCellToTable(table, "Categoría", textviewCategoria.getText().toString(), true);
            addCellToTable(table, requerimiento, textviewRequerimiento.getText().toString(), true);
            addCellToTable(table, "Descripción", textviewDescripcion.getText().toString(), true);

            if (!textviewUnidadMedida.getText().toString().isEmpty()) {
                addCellToTable(table, unidadMedidaTexto, textviewUnidadMedida.getText().toString(), true);
            }

            if (!textviewPrecio.getText().toString().isEmpty()) {
                addCellToTable(table, precio, textviewPrecio.getText().toString(), true);
            }

            if (!Mostrar_Maquina.getText().toString().isEmpty()) {
                addCellToTable(table, maquina, Mostrar_Maquina.getText().toString(), true);
            }

            if (!textmostrarsupervisionSINO.getText().toString().isEmpty()) {
                addCellToTable(table, supervision, textmostrarsupervisionSINO.getText().toString(), true);
            }

            if (!textviewSubTotal.getText().toString().isEmpty()
                    && !"S/ 0".equals(textviewSubTotal.getText().toString())) {
                addCellToTable(table, "Subtotal", textviewSubTotal.getText().toString(), true);
            }
            if (!textviewTotalIGV.getText().toString().isEmpty()
                    && !"S/ 0".equals(textviewTotalIGV.getText().toString())) {
                addCellToTable(table, "IGV", textviewTotalIGV.getText().toString(), true);
            }

            if (!textviewTotal.getText().toString().isEmpty()
                    && !"S/ 0".equals(textviewTotal.getText().toString())) {
                addCellToTable(table, "Total", textviewTotal.getText().toString(), true);
            }

            if (!mostrartotalInAR.getText().toString().isEmpty()
                    && !"S/ 0".equals(mostrartotalInAR.getText().toString())) {
                addCellToTable(table, "Total", mostrartotalInAR.getText().toString(), true);
            }


            document.add(table);

            // Crear el texto "Respecto a la propuesta:"
            String propuestaTexto = "Respecto a la propuesta:";
            Paragraph propuesta = new Paragraph(propuestaTexto)
                    .setBold()               // Negrita
                    .setFontSize(12)
                    .setMarginLeft(28)        // Tamaño de letra más pequeño
                    .setTextAlignment(TextAlignment.LEFT)  // Alineado a la izquierda
                    .setMarginTop(12);        // Espacio consistente después de la tabla
            document.add(propuesta);

// Datos estáticos
            String formaPagoStatic = "Forma de pago: ";
            String plazoEntregaStatic = "Plazo de entrega: ";

// Obtener el texto ingresado por el usuario desde el campo EditText
            String textoUsuario = ed_comentario.getText().toString();
            String plazo = Ed_plazoEntrega.getText().toString();

// Si el campo de comentario del usuario está vacío, se usa el texto estático por defecto
            String anticipo = textoUsuario.isEmpty() ? "Anticipo 50%, saldo al culminar" : textoUsuario;

// Datos dinámicos
            String plazoEntrega = plazo.isEmpty() ? "3 días hábiles" : plazo;

// Crear la cadena para "Forma de pago" y "Plazo de entrega"
            String formaPagoText = formaPagoStatic + anticipo + "\n";
            String plazoEntregaText = plazoEntregaStatic + plazoEntrega + "\n";

// Crear el Paragraph con los datos estáticos y dinámicos
            Paragraph formaPagoParagraph = new Paragraph(formaPagoText)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginLeft(28)
                    .setFontSize(11)
                    .setMarginTop(1); // Espacio consistente

// Añadir el Paragraph de "Forma de pago"
            document.add(formaPagoParagraph);

// Crear el Paragraph para "Plazo de entrega"
            Paragraph plazoEntregaParagraph = new Paragraph(plazoEntregaText)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginLeft(28)
                    .setFontSize(11)
                    .setMarginTop(-2); // Espacio consistente

// Añadir el Paragraph de "Plazo de entrega"
            document.add(plazoEntregaParagraph);


// Crear y añadir la imagen del pie de página (footer)
            Image footerImage = new Image(ImageDataFactory.create(inputStreamToByteArray(getResources().openRawResource(R.drawable.empresas))));
            float footerHeight = 50f; // Altura del pie de página, ajusta según necesites
            float footerWidth = (footerImage.getImageWidth() / footerImage.getImageHeight()) * footerHeight;

// Posicionar el pie de página en la parte inferior
            float footerBottomMargin = 5f; // Posición del pie de página (ajusta según necesites)
            footerImage.setFixedPosition((pageWidth - footerWidth) / 2, footerBottomMargin);
            footerImage.setWidth(footerWidth);
            footerImage.setHeight(footerHeight);

// Añadir el pie de página al documento
            document.add(footerImage);

// Crear y añadir imagen de la firma (por ejemplo, un cuadrado pequeño)
            Image firmaImage = new Image(ImageDataFactory.create(inputStreamToByteArray(getResources().openRawResource(R.drawable.firma))));
            float firmaHeight = 50f; // Altura de la firma, ajusta según necesites
            float firmaWidth = (firmaImage.getImageWidth() / firmaImage.getImageHeight()) * firmaHeight;

// Posicionar la imagen de la firma encima del footer
            float firmaMarginAboveFooter = footerBottomMargin + footerHeight + 9f; // Espacio para separar firma del footer

            firmaImage.setFixedPosition((pageWidth - firmaWidth) / 2, firmaMarginAboveFooter);
            firmaImage.setWidth(firmaWidth);
            firmaImage.setHeight(firmaHeight);

// Añadir la firma al documento
            document.add(firmaImage);


            document.close();

            // Establecer las banderas de generación
            isPDFGenerated = true;
            this.pdfPath = pdfPath; // Asignar a la variable de instancia

            // Cambiar la visibilidad del ícono verde
            imageViewPDFGreen.setVisibility(View.VISIBLE);

            // Opcional: Mostrar mensaje de éxito
            Toast.makeText(this, "PDF generado correctamente", Toast.LENGTH_SHORT).show();

            return pdfPath;
        } catch (Exception e) {
            isPDFGenerated = false;
            this.pdfPath = null;
            Toast.makeText(this, "Error al generar PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }
    // En el método addCellToTable, ajusta el ancho de las columnas
    private void addCellToTable(Table table, String label, String value, boolean isHeader) {
        // Crear la celda para el encabezado (columna izquierda)
        Cell labelCell = new Cell()
                .add(new Paragraph(label)
                        .setFontSize(11f))
                .setTextAlignment(TextAlignment.LEFT)
                .setPadding(2)
                .setWidth(UnitValue.createPercentValue(30)); // La primera columna ocupa 30%

        if (isHeader) {
            labelCell.setBackgroundColor(new DeviceRgb(173, 216, 230));
            labelCell.setFontColor(ColorConstants.BLACK);
        }

        // Crear la celda para el valor (columna derecha)
        Cell valueCell = new Cell()
                .add(new Paragraph(value)
                        .setFontSize(11f))
                .setTextAlignment(TextAlignment.LEFT)
                .setPadding(2)
                .setWidth(UnitValue.createPercentValue(70)); // La segunda columna ocupa 70%

        // Agregar ambas celdas a la tabla
        table.addCell(labelCell);
        table.addCell(valueCell);
    }
    private String obtenerRutaDelPDF() {
        // Cambia el nombre del archivo para cada cotización
        return new File(getExternalFilesDir(null), "Cotizacion_" + System.currentTimeMillis() + ".pdf").getAbsolutePath();
    }


    private void sharePDF() {
        if (pdfPath == null || pdfPath.isEmpty()) {
            Toast.makeText(this, "No se encontró el archivo PDF para compartir.", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(pdfPath);
        if (!file.exists()) {
            Toast.makeText(this, "El archivo PDF no existe.", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = FileProvider.getUriForFile(this, "com.example.atlasestimates.fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Compartir PDF"));
    }


    private String saveImage(Bitmap bitmap) {
        // Genera un nombre de archivo único utilizando una marca de tiempo
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".png";

        // Obtiene el directorio de almacenamiento externo para archivos de la aplicación
        File storageDir = getExternalFilesDir(null);

        try {
            // Crea un archivo con el nombre único
            File imageFile = File.createTempFile(
                    imageFileName,  // prefijo
                    ".png",         // sufijo
                    storageDir      // directorio
            );

            // Guarda la imagen en el archivo
            try (FileOutputStream out = new FileOutputStream(imageFile)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                return imageFile.getAbsolutePath();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void downloadPDF() {
        if (!isPDFGenerated || pdfPath == null) {
            Toast.makeText(this, "Primero genera el PDF.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Para Android 11 o superior
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            } else {
                realizarDescargaPDF();
            }
        } else {
            // Para versiones anteriores a Android 11
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE
                );
            } else {
                realizarDescargaPDF();
            }
        }
    }

    private void realizarDescargaPDF() {
        try {
            File sourceFile = new File(pdfPath); // Usa la ruta dinámica
            if (!sourceFile.exists()) {
                Toast.makeText(this, "Archivo PDF no encontrado.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ruta de destino en la carpeta de descargas
            File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File destinationFile = new File(downloadDir, sourceFile.getName());

            // Copiar el archivo
            FileInputStream in = new FileInputStream(sourceFile);
            FileOutputStream out = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            in.close();
            out.close();

            // Notificar al sistema para actualizar la carpeta de descargas
            MediaScannerConnection.scanFile(
                    this,
                    new String[]{destinationFile.getAbsolutePath()},
                    null,
                    null
            );

            Toast.makeText(this, "PDF descargado en Descargas", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al descargar PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private byte[] inputStreamToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    // Manejar el resultado de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, realizar la descarga
                realizarDescargaPDF();
            } else {
                Toast.makeText(this, "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}