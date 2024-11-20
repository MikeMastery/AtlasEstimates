package com.example.atlasestimates;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
            totaldeIngenieriayArquitectura, textviewTotalTopografia, tv_comentario, ed_comentario ;
    private EditText editextImagen;
    private String imagePath;
    private AppDatabase db;
    private CotizacionDao cotizacionDao;
    private Button btnGuardarCotizacion;
    private ClienteDao clienteDao;
    private DetalleCotizacionDao detalleDao;
    private CategoriaDao categoriaDao;
    private ItemsDao  itemsDao;
    private LinearLayout layoutTotal,layoutIGV, layoutSubTotal, ocultarRazonSocial, ocultarTotalServicios, layoutMetrosUnidadees, layoutprecio, layoutsupervision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cotizacon);

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
        tv_comentario = findViewById(R.id.mostrarComentario);
        ed_comentario = findViewById(R.id.cajaComentario);
    }

    private void mostrarDatosTemporales() {
        Cotizacion cotizacion = (Cotizacion) getIntent().getSerializableExtra("cotizacion");
        if (cotizacion != null) {
            textViewTitulo.setText(cotizacion.getNombreCotizacion());
            textviewCliente.setText(cotizacion.getNombreCliente());
            textview_mostrarUbicacion.setText(cotizacion.getUbicacion());
            String sub = cotizacion.getIdentificacion();

            if ("DNI".equals(sub)){
                mostrarTipoIden.setText("DNI:");
                textviewIdentificacion.setText(cotizacion.getDni());
                ocultarRazonSocial.setVisibility(View.GONE);
            } else if ("RUC".equals(sub)) {
                mostrarTipoIden.setText("RUC:");
                textviewIdentificacion.setText(cotizacion.getRuc());
                textviewMostrarRazon.setText(cotizacion.getRazonsocial());
                textviewRazoncial.setVisibility(View.VISIBLE);
            }else {
                return;
            }

            textviewFecha.setText(cotizacion.getFecha());
            textviewRequerimiento.setText(cotizacion.getProducto());
            textviewDescripcion.setText(cotizacion.getDescripcion());
            textviewCategoria.setText(cotizacion.getCategoria());
            ed_comentario.setText(cotizacion.getcoementarioTopografia());

            // Ajustes condicionales basados en la subcategoría
            String subcategoria = cotizacion.getProducto(); // Usar el campo adecuado para la subcategoría

            if ("Block de concreto".equals(subcategoria) || "Poste de concreto".equals(subcategoria)) {
                // Muestra unidades en lugar de metros
                mostrarMedida.setText("Unidades:");
                textviewUnidadMedida.setText(cotizacion.getHorasMaquina());
                textviewPrecio.setText(cotizacion.getPrecioHora());
                textmostrarsupervision.setVisibility(View.GONE);
                ocultarTotalServicios.setVisibility(View.GONE);
                layoutsupervision.setVisibility(View.GONE);


            } else if ("Cercos prefabricados".equals(subcategoria) || "Cerco cabeza caballo".equals(subcategoria)) {
                // Muestra metros en lugar de metros/unidades
                mostrarMedida.setText("Metros:");
                textviewUnidadMedida.setText(cotizacion.getMetrosLineales());
                textviewPrecio.setText(cotizacion.getPrecio());
                textmostrarsupervision.setVisibility(View.GONE);
                ocultarTotalServicios.setVisibility(View.GONE);
                layoutsupervision.setVisibility(View.GONE);

            } else if ("Generador (10 KW)".equals(subcategoria) || "Rotomartillo Demoledor (17 K)".equals(subcategoria) ||
                    "Rotomartillo Demoledor (11 K)".equals(subcategoria) || "Cortadora Pavimento".equals(subcategoria) ||
                    "Mezcladora".equals(subcategoria) || "Vibrador Concreto".equals(subcategoria)) {
                mostrarMedida.setText("Dias:");
                textviewUnidadMedida.setText(cotizacion.getEquipoMenor());
                textviewPrecio.setText(cotizacion.getPrecioEquiposMenores());
                textmostrarsupervision.setVisibility(View.GONE);
                ocultarTotalServicios.setVisibility(View.GONE);
                layoutsupervision.setVisibility(View.GONE);

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


            }else if ("Ingenieria".equals(subcategoria) || "Arquitectura".equals(subcategoria)) {
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

            }else if ("Unidad".equals(subcategoria) || "Global".equals(subcategoria)) {
                textviewTotalTopografia.setText("Medida:");
                mostrartotalInAR.setText("S/ " + cotizacion.getTotalTopogrgafia());
                layoutMetrosUnidadees.setVisibility(View.GONE);
                layoutprecio.setVisibility(View.GONE);
                layoutsupervision.setVisibility(View.GONE);
                layoutTotal.setVisibility(View.GONE);
                layoutIGV.setVisibility(View.GONE);
                layoutSubTotal.setVisibility(View.GONE);

            }else if ("Medida Global".equals(subcategoria)) {
                textviewTotalTopografia.setText("Medida:");
                mostrartotalInAR.setText("S/ " + cotizacion.getCampoConstruccionObra());
                layoutMetrosUnidadees.setVisibility(View.GONE);
                layoutprecio.setVisibility(View.GONE);
                layoutsupervision.setVisibility(View.GONE);
                layoutTotal.setVisibility(View.GONE);
                layoutIGV.setVisibility(View.GONE);
                layoutSubTotal.setVisibility(View.GONE);

            }else if ("Agua potable".equals(subcategoria) || "Agua no potable".equals(subcategoria)) {
                mostrarMedida.setText("Metro Cubicos:");
                textviewUnidadMedida.setText(cotizacion.getCantidaAgua());
                textviewPrecio.setVisibility(View.GONE);
                tvmostrarvalor.setVisibility(View.GONE);
                layoutsupervision.setVisibility(View.GONE);
                mostrartotalInAR.setText("S/ " + cotizacion.getCampoTotalAgua());
                // Ocultar los LinearLayouts completos en lugar de solo los TextViews
                layoutTotal.setVisibility(View.GONE);
                layoutIGV.setVisibility(View.GONE);
                layoutSubTotal.setVisibility(View.GONE);

            }else if ("Alquiler".equals(subcategoria)) {
                textviewTotalTopografia.setText("Medida:");
                textviewUnidadMedida.setText(cotizacion.getMaquina());
                mostrarMedida.setText("Maquina:");
                textviewPrecio.setText(cotizacion.getHorasAlquiler());
                tvmostrarvalor.setText("Horas Alquiladas:");
                textmostrarsupervision.setText("Costo Hora:");
                textmostrarsupervisionSINO.setText(cotizacion.getCostoHora());
                ocultarTotalServicios.setVisibility(View.GONE);



            }else{
                return;

            }

            textviewTotal.setText("S/ " + cotizacion.getTotal());
            textviewTotalIGV.setText("S/ " + cotizacion.getIgv());
            textviewSubTotal.setText("S/ " + cotizacion.getSubTotal());


            manejarImagenCotizacion(cotizacion);
        }
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

    private void limpiarCamposOcultos() {
        // Limpia los campos de texto que podrían estar ocultos
        textviewSubTotal.setText("");
        textviewTotalIGV.setText("");
        textviewTotal.setText("");
    }

    private void guardarCotizacionEnRoom() {
        try {
            // Crear objeto de cotización
            table_cotizacion nuevaCotizacion = new table_cotizacion();
            nuevaCotizacion.setTitulo(textViewTitulo.getText().toString());
            nuevaCotizacion.setFecha(textviewFecha.getText().toString());
            nuevaCotizacion.setDescripcion(textviewDescripcion.getText().toString());
            nuevaCotizacion.setUbicacion(textview_mostrarUbicacion.getText().toString());

            String totalText = textviewTotal.getText().toString().replaceAll("[^\\d.]", "");
            double total = Double.parseDouble(totalText);
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
            String subtotalText = textviewSubTotal.getText().toString().replaceAll("[^\\d.]", "");
            String igvText = textviewTotalIGV.getText().toString().replaceAll("[^\\d.]", "");
            String cantidad = textviewUnidadMedida.getText().toString().replaceAll("[^\\d.]", "");
            nuevoDetalle.setCantidad(Double.parseDouble(cantidad));
            nuevoDetalle.setSubtotal(Double.parseDouble(subtotalText));
            nuevoDetalle.setIgv(Double.parseDouble(igvText));

            // Crear objeto de categoría
            table_categoria nuevaCategoria = new table_categoria();
            nuevaCategoria.setNombre_categoria(textviewCategoria.getText().toString());

            // Crear objeto de item
            table_items nuevoItem = new table_items();
            nuevoItem.setNombre_Item(textviewRequerimiento.getText().toString());
            nuevoItem.setPrecio(Double.parseDouble(textviewPrecio.getText().toString()));
            nuevoItem.setDescripcion_product("Descripción estática"); // Descripción estática

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
                                Toast.makeText(Activity_mostrar_cotizacon.this,
                                        "Cotización guardada exitosamente",
                                        Toast.LENGTH_SHORT).show();

                                // Obtener la fecha actual en formato String
                                String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                                // Obtener la instancia del ViewModel
                                NotificationsViewModel notificationsViewModel = new ViewModelProvider(Activity_mostrar_cotizacon.this).get(NotificationsViewModel.class);

                                // Llamar al método agregarNotificacion con la fecha actual
                                notificationsViewModel.agregarNotificacion(fechaActual);


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
        } catch (Exception e) {
            Toast.makeText(this, "Error al preparar la cotización: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    private void configurarImageButton() {
        ImageButton imageButton = findViewById(R.id.conpartir_descargarPDF);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Activity_mostrar_cotizacon.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(android.view.MenuItem item) {
                        if (item.getItemId() == R.id.action_download) {
                            createPDFWithIText();
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
    // Método para crear el PDF con iText7
    public void createPDFWithIText() {
        // Ruta del archivo PDF
        String pdfPath = getExternalFilesDir(null).getAbsolutePath() + "/Cotización_Atlas.pdf";
        File file = new File(pdfPath);

        try {
            // Inicializar PdfWriter y PdfDocument
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);

            // Crear la imagen desde los recursos
            Image headerImage = new Image(ImageDataFactory.create(inputStreamToByteArray(getResources().openRawResource(R.drawable.encabezado))));
            float pageWidth = PageSize.A4.getWidth();
            float pageHeight = PageSize.A4.getHeight();

            // Ajustar el tamaño de la imagen
            float imageWidth = pageWidth - 100; // Ajusta el ancho para que ocupe más espacio horizontal (ajusta el valor según lo que desees)
            float imageHeight = 118f; // Ajusta la altura para que sea más corta

            // Configurar la posición y tamaño de la imagen
            headerImage.setFixedPosition((pageWidth - imageWidth) / 2, pageHeight - imageHeight - 10);
            headerImage.setWidth(imageWidth);
            headerImage.setHeight(imageHeight);

            document.add(headerImage);

            // Crear título
            Paragraph title = new Paragraph(  textViewTitulo.getText().toString())
                    .setBold()
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(113); // Ajusta este valor para bajar más el título
            document.add(title);

            // Obtén los datos de los TextViews
            String tipoidentificacion = mostrarTipoIden.getText().toString();
            String cliente = textviewCliente.getText().toString();
            String fecha = textviewFecha.getText().toString();
            String identificacion = textviewIdentificacion.getText().toString();
            String ubicacion = textview_mostrarUbicacion.getText().toString();

            // Verifica si el campo de razón social está vacío y asigna "NO" si es necesario
            String razonSocial = textviewMostrarRazon.getText().toString().isEmpty() ? "No" : textviewMostrarRazon.getText().toString();
            String tipoIdentRazonSocial = textviewRazoncial.getText().toString();

            // Crear la cadena de datos del cliente, incluyendo razón social con "NO" si está vacía
            String datosCliente = "Cliente: " + cliente + "\n" +
                    "Fecha: " + fecha + "\n" +
                    tipoidentificacion + ": " + identificacion + "\n" +
                    tipoIdentRazonSocial + ": " + razonSocial + "\n" +
                    "Ubicación: " + ubicacion;

            // Crear el Paragraph con los datos
            Paragraph rightAlignedData = new Paragraph(datosCliente)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginLeft(28)
                    .setMarginTop(10);

            // Añadir el Paragraph al documento
            document.add(rightAlignedData);


            //Añadir un breve texto de agradecimiento
            Paragraph textAgradecimiento = new Paragraph(
                    "Es grato dirigirme a usted, para saludarle, agradecer la invitación y presentar nuestra propuesta de vuestro servicio: " )
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginLeft(28)
                    .setMarginTop(20); // Ajusta este valor para bajar más el título

            document.add(textAgradecimiento);



            // Tabla en el centro
            float[] columnWidths = {200f, 200f};
            Table table = new Table(columnWidths);
            table.setMarginTop(15);

            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            // Usar el campo adecuado para la subcategoría

            // Recuperar el texto actual de mostrarMedida
            String unidadMedidaTexto = mostrarMedida.getText().toString();

            String requerimiento = textviewTotalTopografia.getText().toString();

            String precio = tvmostrarvalor.getText().toString();

            String supervision =  textmostrarsupervision.getText().toString();

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

            if (!textmostrarsupervisionSINO.getText().toString().isEmpty()) {
                addCellToTable(table, supervision, textmostrarsupervisionSINO.getText().toString(), true);
            }


            // Agregar imagen dentro de la tabla
            if (imagePath != null) {
                Image img = new Image(ImageDataFactory.create(imagePath));

                // Obtener dimensiones originales de la imagen
                float originalWidth = img.getImageWidth();
                float originalHeight = img.getImageHeight();

                // Definir el ancho máximo de la celda en la tabla
                float maxWidth = 155f;
                float maxHeight = 122f;

                // Calcular ratio para mantener proporción
                float ratio = Math.min(maxWidth / originalWidth, maxHeight / originalHeight);

                // Establecer nuevas dimensiones manteniendo la proporción
                float newWidth = originalWidth * ratio;
                float newHeight = originalHeight * ratio;

                img.setWidth(newWidth);
                img.setHeight(newHeight);

                // Crear un Paragraph para la imagen y centrarla
                Paragraph imageParagraph = new Paragraph().add(img).setTextAlignment(TextAlignment.CENTER);

                // Crear la celda y agregar el Paragraph con la imagen centrada
                Cell imageCell = new Cell(1, 2)
                        .add(imageParagraph)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setPadding(5);

                table.addCell(imageCell);
            }


            if (!textviewSubTotal.getText().toString().trim().isEmpty()) {
                addCellToTable(table, "Subtotal", textviewSubTotal.getText().toString(), true);
            }

            if (!textviewTotalIGV.getText().toString().trim().isEmpty()) {
                addCellToTable(table, "IGV", textviewTotalIGV.getText().toString(), true);
            }

            if (!textviewTotal.getText().toString().trim().isEmpty()) {
                addCellToTable(table, "Total", textviewTotal.getText().toString(), true);
            }

            if (! mostrartotalInAR.getText().toString().trim().isEmpty()) {
                addCellToTable(table, "Total",  mostrartotalInAR.getText().toString(), true);
            }


            document.add(table);

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
            Toast.makeText(this, "PDF generado con éxito", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al generar PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addCellToTable(Table table, String label, String value, boolean isHeader) {
        // Crear la celda para el encabezado (columna izquierda)
        Cell labelCell = new Cell()
                .add(new Paragraph(label))
                .setTextAlignment(TextAlignment.LEFT)
                .setPadding(5);

        // Aplicar estilo celeste y blanco solo si es encabezado
        if (isHeader) {
            labelCell.setBackgroundColor(new DeviceRgb(173, 216, 230)); // Fondo celeste
            labelCell.setFontColor(ColorConstants.BLACK); // Texto blanco
        }

        // Crear la celda para el valor (columna derecha)
        Cell valueCell = new Cell()
                .add(new Paragraph(value))
                .setTextAlignment(TextAlignment.LEFT)
                .setPadding(5);

        // Agregar ambas celdas a la tabla
        table.addCell(labelCell);
        table.addCell(valueCell);
    }



    private void sharePDF() {
        File file = new File(getExternalFilesDir(null) + "/Cotización_Atlas.pdf");
        Uri uri = FileProvider.getUriForFile(this, "com.example.atlasestimates.fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Compartir PDF"));
    }


    // Método para guardar la imagen en el almacenamiento externo
    private String saveImage(Bitmap bitmap) {
        String imagePath = getExternalFilesDir(null).getAbsolutePath() + "/imagen.png";
        File imageFile = new File(imagePath);
        try (FileOutputStream out = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
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

}