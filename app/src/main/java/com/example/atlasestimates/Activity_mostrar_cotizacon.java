package com.example.atlasestimates;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
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

public class Activity_mostrar_cotizacon extends AppCompatActivity {

    private TextView textViewTitulo, textviewCliente, textviewFecha, textviewRequerimiento, textviewDescripcion;
    private TextView textviewCategoria, textviewUnidadMedida, textviewPrecio, textviewTotal, textviewTotalIGV, textviewSubTotal;
    private EditText editextImagen;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cotizacon);

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

        // Recuperar la cotización
        Cotizacion cotizacion = (Cotizacion) getIntent().getSerializableExtra("cotizacion");
        if (cotizacion != null) {
            textViewTitulo.setText(cotizacion.getNombreCotizacion());
            textviewCliente.setText(cotizacion.getNombreCliente());
            textviewFecha.setText(cotizacion.getFecha());
            textviewRequerimiento.setText(cotizacion.getProducto());
            textviewDescripcion.setText(cotizacion.getDescripcion());
            textviewCategoria.setText(cotizacion.getCategoria());
            textviewUnidadMedida.setText(cotizacion.getMetrosLineales() + " / " + cotizacion.getHorasMaquina());
            textviewPrecio.setText(cotizacion.getPrecio() + " / " + cotizacion.getPrecioHora());
            textviewTotal.setText(cotizacion.getTotal());
            textviewTotalIGV.setText(cotizacion.getIgv());
            textviewSubTotal.setText(cotizacion.getSubTotal());

            // Manejar la imagen de la cotización
            String imageUriString = cotizacion.getImagenUri();
            if (imageUriString != null) {
                Uri imageUri = Uri.parse(imageUriString);
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    // Obtener el tamaño original del bitmap
                    int originalWidth = bitmap.getWidth();
                    int originalHeight = bitmap.getHeight();

                    // Variables para el tamaño redimensionado
                    int resizedWidth;
                    int resizedHeight;

                    // Definir el tamaño basado en la orientación de la imagen
                    if (originalHeight > originalWidth) {
                        // Imagen vertical: ajustar según la altura deseada
                        int desiredHeight = 535;  // Ajusta la altura deseada
                        resizedHeight = desiredHeight;
                        resizedWidth = (int) ((float) originalWidth * ((float) desiredHeight / originalHeight));
                    } else {
                        // Imagen horizontal: ajustar según el ancho deseado
                        int desiredWidth = 550;  // Ajusta el ancho deseado
                        resizedWidth = desiredWidth;
                        resizedHeight = (int) ((float) originalHeight * ((float) desiredWidth / originalWidth));
                    }

                    // Redimensionar el bitmap manteniendo la proporción
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, true);

                    // Crear el drawable con el bitmap redimensionado
                    Drawable drawable = new BitmapDrawable(getResources(), resizedBitmap);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                    // Establecer el drawable en el EditText (a la derecha en este caso)
                    editextImagen.setCompoundDrawables(null, null, drawable, null);

                    // Guardar la imagen redimensionada si es necesario
                    imagePath = saveImage(resizedBitmap);  // Guardar la imagen en el almacenamiento externo para usarla en el PDF
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }


        }

        // Configurar el ImageButton para mostrar el menú
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
                            createPDFWithIText();  // Genera y guarda el PDF
                            return true;
                        } else if (item.getItemId() == R.id.action_share) {
                            sharePDF();  // Método para compartir el PDF
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

            Image headerImage = new Image(ImageDataFactory.create(inputStreamToByteArray(getResources().openRawResource(R.drawable.encabezado))));
            float pageWidth = PageSize.A4.getWidth();
            float pageHeight = PageSize.A4.getHeight();
            float imageHeight = 140f; // Ajusta este valor según lo que necesites

            // Mantener la relación de aspecto de la imagen
            float imageWidth = (headerImage.getImageWidth() / headerImage.getImageHeight()) * imageHeight;
            headerImage.setFixedPosition((pageWidth - imageWidth) / 2, pageHeight - imageHeight - 10);
            headerImage.setWidth(imageWidth);
            headerImage.setHeight(imageHeight);
            document.add(headerImage);

            // Crear título
            Paragraph title = new Paragraph(  textViewTitulo.getText().toString())
                    .setBold()
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(130); // Ajusta este valor para bajar más el título
            document.add(title);

            // Datos a la derecha con sangría
            Paragraph rightAlignedData = new Paragraph(
                    "Cliente: " + textviewCliente.getText().toString() + "\n" +
                            "Fecha: " + textviewFecha.getText().toString())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginLeft(28)
                    .setMarginTop(20);
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
            table.setMarginTop(20);

            table.setHorizontalAlignment(HorizontalAlignment.CENTER);

            // Añadir datos a la tabla
            addCellToTable(table, "Categoría", textviewCategoria.getText().toString());
            addCellToTable(table, "Producto", textviewRequerimiento.getText().toString());
            addCellToTable(table, "Descripción", textviewDescripcion.getText().toString());
            addCellToTable(table, "Metros Lineales/ UND", textviewUnidadMedida.getText().toString());
            addCellToTable(table, "Precio", textviewPrecio.getText().toString());

            // Agregar imagen dentro de la tabla
            if (imagePath != null) {
                Image img = new Image(ImageDataFactory.create(imagePath));

                // Obtener dimensiones originales de la imagen
                float originalWidth = img.getImageWidth();
                float originalHeight = img.getImageHeight();

                // Definir el ancho máximo de la celda en la tabla
                float maxWidth = 135f;
                float maxHeight = 120f;

                // Calcular ratio para mantener proporción
                float ratio = Math.min(maxWidth / originalWidth, maxHeight / originalHeight);

                // Establecer nuevas dimensiones manteniendo la proporción
                float newWidth = originalWidth * ratio;
                float newHeight = originalHeight * ratio;

                img.setWidth(newWidth);
                img.setHeight(newHeight);

                // Crear la celda y centrar la imagen tanto horizontal como verticalmente
                Cell imageCell = new Cell(1, 2)
                        .add(img)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setPadding(5);  // Añadir un poco de padding para que no esté pegada a los bordes

                table.addCell(imageCell);
            }


            addCellToTable(table, "Subtotal", textviewSubTotal.getText().toString());
            addCellToTable(table, "IGV", textviewTotalIGV.getText().toString());
            addCellToTable(table, "Total", textviewTotal.getText().toString());

            document.add(table);


            // Crear y añadir imagen de pie de página
            Image footerImage = new Image(ImageDataFactory.create(inputStreamToByteArray(getResources().openRawResource(R.drawable.empresas))));
            float footerHeight = 53f; // Ajusta este valor según lo que necesites
            float footerWidth = (footerImage.getImageWidth() / footerImage.getImageHeight()) * footerHeight;

            // Posicionar el pie de página en la parte inferior
            float bottomMargin = 10f; // Margen inferior, ajusta según necesites
            footerImage.setFixedPosition((pageWidth - footerWidth) / 2, bottomMargin);
            footerImage.setWidth(footerWidth);
            footerImage.setHeight(footerHeight);

            // Añadir el pie de página al documento
            document.add(footerImage);

            document.close();
            Toast.makeText(this, "PDF generado con éxito", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al generar PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void addCellToTable(Table table, String label, String value) {
        table.addCell(new Cell().add(new Paragraph(label)));
        table.addCell(new Cell().add(new Paragraph(value)));
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