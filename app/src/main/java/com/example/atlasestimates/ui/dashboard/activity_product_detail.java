package com.example.atlasestimates.ui.dashboard;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.atlasestimates.R;

public class activity_product_detail extends AppCompatActivity {


    private ImageView ivProductImageDetail;
    private TextView tvProductNameDetail;
    private TextView tvProductDescriptionDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Inicializar las vistas
        ivProductImageDetail = findViewById(R.id.ivProductImageDetail);
        tvProductNameDetail = findViewById(R.id.tvProductNameDetail);
        tvProductDescriptionDetail = findViewById(R.id.tvProductDescriptionDetail);

        // Obtener el ítem desde el Intent
        if (getIntent() != null) {
            DashboardFragment.Item item = (DashboardFragment.Item) getIntent().getSerializableExtra("selectedItem");
            if (item != null) {
                ivProductImageDetail.setImageResource(item.getImageResource());
                tvProductNameDetail.setText(item.getName());
                tvProductDescriptionDetail.setText(item.getDescription());
            }
        }
    }
}