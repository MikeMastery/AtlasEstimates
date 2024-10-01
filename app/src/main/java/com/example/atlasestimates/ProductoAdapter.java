package com.example.atlasestimates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {
    private List<Producto> productos;

    public ProductoAdapter(List<Producto> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.bind(producto);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public void actualizarProductos(List<Producto> nuevosProductos) {
        this.productos = nuevosProductos;
        notifyDataSetChanged();
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagenProducto;
        private TextView nombreProducto, descripcionProducto, precioProducto, stockProducto;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenProducto = itemView.findViewById(R.id.productImage);
            nombreProducto = itemView.findViewById(R.id.productName);
            descripcionProducto = itemView.findViewById(R.id.productDescription);
            precioProducto = itemView.findViewById(R.id.productPrice);
            stockProducto = itemView.findViewById(R.id.productStock);
        }

        public void bind(Producto producto) {
            nombreProducto.setText(producto.getNombre());
            descripcionProducto.setText(producto.getDescripcion());
            precioProducto.setText(String.format("Precio: $%.2f", producto.getPrecio()));
            stockProducto.setText(String.format("Stock: %d", producto.getStock()));

            // Usar Glide para cargar la imagen desde la URL
            Glide.with(itemView.getContext())
                    .load(producto.getImagenUrl())
                    .placeholder(R.drawable.placeholder_image) // Imagen de placeholder si no hay imagen
                    // Imagen en caso de error
                    .into(imagenProducto);
        }
    }
}
