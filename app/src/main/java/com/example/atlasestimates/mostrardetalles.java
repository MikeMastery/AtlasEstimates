package com.example.atlasestimates;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Paint;


import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.atlasestimates.Cotizacion;  // Asegúrate de que la importación esté correcta
import com.google.android.material.button.MaterialButton;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import android.os.Environment;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

public class mostrardetalles extends AppCompatActivity {

    private CotizacionViewModel viewModel;
    private table_cotizacion cotizacion;
    private ImageView imagenCotizacion;
    private String pdfPath = null;
    private String imagePath;
    private LinearLayout LayoutMedida, LayoutMaquina, LayoutRazon_Social, LayoutSubtotal, LayoutIGV, LayoutTotal1, LayoutTotal2,
             LayoutSupervision, LayoutPrecio, LayoutCliente;
    private TextView tvNombreCliente, tvTitulo, tvUbicacion, tvdescripcion, tvRuc, tvRazonSocial, tvCategoria,
            tvRequerimiento, tvSubTotal, tvIgv, tvTotal, textviewMetros, textviewprecio, mostrarMedida,
            Requerimiento, MostrarMaquina, Precio, Identificacion, MostrarTexto, MostrarSupervision, ED_Total2, Tv_Supervisiion,
             Tv_comentario, Tv_plazo, Tv_fecha, Mostrar_razon ;
    private Button verpdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrardetalles);

        // Inicializar los TextViews
        tvNombreCliente = findViewById(R.id.nombre_cliente);
        imagenCotizacion = findViewById(R.id.imagen_cotizacion);
        Tv_fecha = findViewById(R.id.mostrarfecha);
        Mostrar_razon = findViewById(R.id.tv_razon);
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
        verpdf = findViewById(R.id.verpdf);
        LayoutCliente = findViewById(R.id.layoutcliente);


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
                    Tv_fecha.setText(cotizacion.getFecha());
                    // Formatear el total con comas
                    double total = cotizacion.getTotal(); // Asegúrate de que getTotal() devuelve un double
                    tvTotal.setText("S/ " + formatearNumeroConComas(total));
                    ED_Total2.setText(cotizacion.getTotal_Servicio());
                    Tv_plazo.setText(cotizacion.getComentario_plazo());

                    // Cargar la imagen desde la URI
                    String imageUriString = cotizacion.getImagen();
                    if (imageUriString != null && !imageUriString.isEmpty()) {
                        Uri imageUri = Uri.parse(imageUriString);
                        Glide.with(mostrardetalles.this)
                                .load(new File(imageUriString))
                                .into(imagenCotizacion);
                    }
                    verpdf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String pdfPath = cotizacion.getPdfPath();

                            if (pdfPath != null && !pdfPath.isEmpty()) {
                                // Crear un PopupMenu
                                PopupMenu popupMenu = new PopupMenu(mostrardetalles.this, verpdf);
                                popupMenu.getMenuInflater().inflate(R.menu.popup_menu2, popupMenu.getMenu());

                                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        int itemId = item.getItemId();

                                        if (itemId == R.id.menu_ver_pdf) {
                                            // Tu código existente para abrir PDF
                                            try {
                                                File file = new File(pdfPath);

                                                // Verificar si el archivo existe
                                                if (!file.exists()) {
                                                    Toast.makeText(mostrardetalles.this,
                                                            "El archivo PDF no existe",
                                                            Toast.LENGTH_SHORT).show();
                                                    return true;
                                                }

                                                Uri uri = FileProvider.getUriForFile(
                                                        mostrardetalles.this,
                                                        getPackageName() + ".fileprovider",
                                                        file
                                                );

                                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setDataAndType(uri, "application/pdf");
                                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                                startActivity(Intent.createChooser(intent, "Abrir PDF con"));
                                            } catch (Exception e) {
                                                Toast.makeText(mostrardetalles.this,
                                                        "Error al abrir el PDF: " + e.getMessage(),
                                                        Toast.LENGTH_SHORT).show();
                                                Log.e("PDF_ERROR", "Error abriendo PDF", e);
                                            }
                                        } else if (itemId == R.id.menu_compartir_pdf) {
                                            compartirPDF(pdfPath);
                                        } else if (itemId == R.id.menu_descargar_pdf) {
                                            descargarPDF(pdfPath);

                                        }
                                        else if (itemId == R.id.menu_actualizar_pdf) {
                                            if (pdfPath != null && !pdfPath.isEmpty()) {
                                                createPDFWithIText2(pdfPath, imageUriString); // Llama al método directamente

                                                // Supongamos que se generó correctamente
                                                Toast.makeText(mostrardetalles.this, "PDF actualizado correctamente", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(mostrardetalles.this, "No hay ruta de PDF para actualizar", Toast.LENGTH_SHORT).show();
                                            }
                                        }



                                        return true;
                                    }
                                });

                                popupMenu.show();
                            } else {
                                Toast.makeText(mostrardetalles.this,
                                        "No hay PDF disponible",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
                    double cantidad = 0;
                    for (table_detalleCotizacion detalle : detalles) {
                        // Aquí puedes extraer información de los detalles y actualizar los TextViews correspondientes
                        subtotal += detalle.getSubtotal();  // Suponiendo que `getSubTotal()` esté en table_detalleCotizacion
                        igv += detalle.getIgv();
                        cantidad += detalle.getCantidad();
                        // Suponiendo que `getIgv()` esté en table_detalleCotizacion
                    }
                    // Asegúrate de que subtotal e igv sean de tipo double antes de formatearlos
                    tvSubTotal.setText("S/ " + formatearNumeroConComas(subtotal)); // Formatear subtotal
                    tvIgv.setText("S/ " + formatearNumeroConComas(igv));           // Formatear IGV
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
                        LayoutCliente.setVisibility(View.GONE);
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
                    Tv_comentario.setText(categoria.getDescripcion_categoria());
                    tvCategoria.setText(categoria.getNombre_categoria());


                }
            }
        });
    }

    // Método para crear el PDF con iText7
    public String createPDFWithIText2(String pdfPath, String imageUriString) {

        // Verificar si la ruta del PDF es nula o vacía
        if (pdfPath == null || pdfPath.isEmpty()) {
            return null; // Devuelve null si no hay una ruta válida
        }

        File file = new File(pdfPath);


        try {

            // Crear directorio si no existe
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // El resto del c

            // Crear PdfWriter
            PdfWriter writer = new PdfWriter(pdfPath);

            // Crear PdfDocument
            PdfDocument pdfDoc = new PdfDocument(writer);

            // Crear documento
            Document document = new Document(pdfDoc);

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
            String tituloTexto = tvTitulo.getText().toString();
            Paragraph title = new Paragraph(tituloTexto)
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(85); // Ajusta este valor para bajar más el título
            document.add(title);

// Calcular el ancho de la línea basado en el tamaño del texto
            float fontSize = 18; // Tamaño de fuente del título
            float lineWidthPerCharacter = fontSize * 0.6f; // Aproximadamente, dependiendo de la fuente
            float calculatedWidth = tituloTexto.length() * lineWidthPerCharacter;

// Crear una línea debajo del título con ancho dinámico
            LineSeparator lineSeparator = new LineSeparator(new SolidLine());
            lineSeparator.setWidth(calculatedWidth);
            lineSeparator.setMarginTop(-9); // Ajusta la distancia entre el título y la línea
            lineSeparator.setHorizontalAlignment(HorizontalAlignment.CENTER); // Centrar la línea
            document.add(lineSeparator);

            // Obtén los datos de los TextViews
            String cliente = tvNombreCliente.getText().toString();
            String fecha = Tv_fecha.getText().toString();
            String identificacion = tvRuc.getText().toString();
            String ubicacion = tvUbicacion.getText().toString();

// Verifica si el campo de razón social está vacío y asigna "NO" si es necesario
            String razonSocial = tvRazonSocial.getText().toString().isEmpty() ? "No" : tvRazonSocial.getText().toString();
            String tipoIdentRazonSocial = Mostrar_razon.getText().toString();

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
                    .setMarginTop(15);

// Añadir el Paragraph al documento
            document.add(rightAlignedData);



            //Añadir un breve texto de agradecimiento
            Paragraph textAgradecimiento = new Paragraph(
                    "Es grato dirigirme a usted, para saludarle, agradecer la invitación y presentar nuestra propuesta de vuestro servicio: ")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginLeft(28)
                    .setMarginTop(5); // Ajusta este valor para bajar más el título

            document.add(textAgradecimiento);

            // Agregar la imagen centrada debajo del texto de agradecimiento
            if (imageUriString != null && !imageUriString.isEmpty()) {
                try {
                    Image centeredImage = new Image(ImageDataFactory.create(imageUriString));
                    // Ajustar el tamaño de la imagen
                    float maxWidth = 155f; // Ancho máximo de la imagen
                    float maxHeight = 122f; // Altura máxima de la imagen
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
                            .setMarginTop(-1); // Espacio antes de la imagen
                    document.add(imageParagraph);
                } catch (Exception e) {
                    // Manejo de error si no se puede cargar la imagen
                    Log.e("PDF Creation", "Error adding image to PDF", e);
                }
            }


            // Tabla en el centro
            float[] columnWidths = {200f, 200f};
            Table table = new Table(columnWidths);
            table.setMarginTop(2);

            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            // Usar el campo adecuado para la subcategoría

            // Recuperar el texto actual de mostrarMedida
            String unidadMedidaTexto = MostrarTexto.getText().toString();

            String requerimiento = Requerimiento.getText().toString();

            String medida = mostrarMedida.getText().toString();

            String precio = Precio.getText().toString();

            String supervision = Tv_Supervisiion.getText().toString();

// Añadir datos a la tabla
            addCellToTable(table, "Categoría", tvCategoria.getText().toString(), true);
            addCellToTable(table, requerimiento, tvRequerimiento.getText().toString(), true);
            addCellToTable(table, "Descripción", tvdescripcion.getText().toString(), true);

            if (!textviewMetros.getText().toString().isEmpty()) {
                String metrosTexto = textviewMetros.getText().toString();

                // Validar si el valor no es "0.0" o nulo
                if (!metrosTexto.equals("0.0") && metrosTexto != null) {
                    addCellToTable(table, medida, metrosTexto, true);
                }
            }


            if (!MostrarMaquina.getText().toString().isEmpty()) {
                addCellToTable(table, unidadMedidaTexto, MostrarMaquina.getText().toString(), true);
            }

            if (!textviewprecio.getText().toString().isEmpty()) {
                addCellToTable(table, precio, textviewprecio.getText().toString(), true);
            }

            if (!MostrarSupervision.getText().toString().isEmpty()) {
                addCellToTable(table, supervision, MostrarSupervision.getText().toString(), true);
            }

            if (!tvSubTotal.getText().toString().isEmpty()
                    && !"S/ 0".equals(tvSubTotal.getText().toString())) {
                addCellToTable(table, "Subtotal", tvSubTotal.getText().toString(), true);
            }
            if (!tvIgv.getText().toString().isEmpty()
                    && !"S/ 0".equals(tvIgv.getText().toString())) {
                addCellToTable(table, "IGV", tvIgv.getText().toString(), true);
            }

            if (!tvTotal.getText().toString().isEmpty()
                    && !"S/ 0".equals(tvTotal.getText().toString())) {
                addCellToTable(table, "Total", tvTotal.getText().toString(), true);
            }

            if (!ED_Total2.getText().toString().isEmpty()
                    && !"S/ 0".equals(ED_Total2.getText().toString())) {
                addCellToTable(table, "Total", ED_Total2.getText().toString(), true);
            }


            document.add(table);

            // Crear el texto "Respecto a la propuesta:"
            String propuestaTexto = "Respecto a la propuesta:";
            Paragraph propuesta = new Paragraph(propuestaTexto)
                    .setBold()               // Negrita
                    .setFontSize(11)
                    .setMarginLeft(28)// Tamaño de letra más pequeño
                    .setTextAlignment(TextAlignment.LEFT)  // Alineado a la izquierda
                    .setMarginTop(10);       // Ajusta este valor para bajar más el texto
            document.add(propuesta);

            // Datos estáticos
            String formaPagoStatic = "Forma de pago: ";
            String plazoEntregaStatic = "Plazo de entrega: ";

// Obtener el texto ingresado por el usuario desde el campo EditText
            String textoUsuario = Tv_comentario.getText().toString();
            String plazo = Tv_plazo.getText().toString();

// Si el campo de comentario del usuario está vacío, se usa el texto estático por defecto
            String anticipo = textoUsuario.isEmpty() ? "Anticipo 50%, saldo al culminar" : textoUsuario; // Este valor será dinámico dependiendo del usuario

// Datos dinámicos
            String plazoEntrega =  plazo.isEmpty() ? "3 días hábiles" : plazo; // Este valor también es dinámico

// Crear la cadena para "Forma de pago" y "Plazo de entrega"
            String formaPagoText = formaPagoStatic + anticipo + "\n";
            String plazoEntregaText = plazoEntregaStatic + plazoEntrega + "\n";

// Crear el Paragraph con los datos estáticos y dinámicos
            Paragraph formaPagoParagraph = new Paragraph(formaPagoText)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginLeft(28)
                    .setFontSize(10)
                    .setMarginTop(1); // Reducido el espacio entre párrafos

// Añadir el Paragraph de "Forma de pago"
            document.add(formaPagoParagraph);

// Crear el Paragraph para "Plazo de entrega"
            Paragraph plazoEntregaParagraph = new Paragraph(plazoEntregaText)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginLeft(28)
                    .setFontSize(10)
                    .setMarginTop(-3); // Reducido el espacio entre párrafos

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


            return pdfPath;
        } catch (Exception e) {
            this.pdfPath = null;

            Toast.makeText(this, "Error al generar PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
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

    private String obtenerRutaDelPDF() {
        // Cambia el nombre del archivo para cada cotización
        return new File(getExternalFilesDir(null), "Cotizacion_" + System.currentTimeMillis() + ".pdf").getAbsolutePath();
    }





    // Method to share PDF
    private void compartirPDF(String pdfPath) {
        if (pdfPath == null || pdfPath.isEmpty()) {
            Toast.makeText(this, "No hay PDF disponible para compartir", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(pdfPath);
        if (!file.exists()) {
            Toast.makeText(this, "El archivo PDF no existe", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = FileProvider.getUriForFile(
                this,
                getPackageName() + ".fileprovider",
                file
        );

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(Intent.createChooser(shareIntent, "Compartir PDF"));
    }

    // Method to download PDF (save to Downloads folder)
    private void descargarPDF(String pdfPath) {
        if (pdfPath == null || pdfPath.isEmpty()) {
            Toast.makeText(this, "No hay PDF disponible para descargar", Toast.LENGTH_SHORT).show();
            return;
        }

        File sourceFile = new File(pdfPath);
        if (!sourceFile.exists()) {
            Toast.makeText(this, "El archivo PDF no existe", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Crear la carpeta de descargas si no existe
            File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!downloadDir.exists()) {
                downloadDir.mkdirs();
            }

            // Nombre de archivo único
            String fileName = "Cotizacion_" + System.currentTimeMillis() + ".pdf";
            File destinationFile = new File(downloadDir, fileName);

            // Copiar el archivo
            FileInputStream inputStream = new FileInputStream(sourceFile);
            FileOutputStream outputStream = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            // Notificar al sistema sobre el nuevo archivo
            MediaScannerConnection.scanFile(
                    this,
                    new String[]{destinationFile.getAbsolutePath()},
                    null,
                    null
            );

            Toast.makeText(this, "PDF descargado en Descargas", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al descargar el PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("PDF_DOWNLOAD_ERROR", "Error descargando PDF", e);
        }
    }

    private String formatearNumeroConComas(double numero) {
        DecimalFormat formato = new DecimalFormat("#,###");
        return formato.format(numero);
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


    private void configurarPaddingVistaPrincipal() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()).toPlatformInsets();
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }
}
