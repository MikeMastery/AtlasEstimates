package com.example.atlasestimates.ui.dashboard;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.atlasestimates.CotizacionViewModel;
import com.example.atlasestimates.R;
public class activity_product_detail extends AppCompatActivity {

    private ImageView ivProductImageDetail;
    private TextView tvProductNameDetail;
    private TextView tvProductDescriptionDetail;
    private TextView tvNumber3; // Para mostrar total por item específico
    private TextView tvNumber4; // Para mostrar total general
    private CotizacionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Inicializar las vistas
        ivProductImageDetail = findViewById(R.id.ivProductImageDetail);
        tvProductNameDetail = findViewById(R.id.tvProductNameDetail);
        tvProductDescriptionDetail = findViewById(R.id.tvProductDescriptionDetail);
        tvNumber3 = findViewById(R.id.tvNumber3);
        tvNumber4 = findViewById(R.id.tvNumber4);

        // Inicializar ViewModel
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(CotizacionViewModel.class);

        // Obtener el ítem desde el Intent
        if (getIntent() != null) {
            DashboardFragment.Item item = (DashboardFragment.Item) getIntent().getSerializableExtra("selectedItem");
            if (item != null) {
                // Mostrar detalles del item
                ivProductImageDetail.setImageResource(item.getImageResource());
                tvProductNameDetail.setText(item.getName());
                tvProductDescriptionDetail.setText(item.getDescription());

                // Cargar el total de cotizaciones para este item específico
                viewModel.loadTotalPorItem(item.getName());

                // Observar el total por item específico
                viewModel.getTotalPorItem().observe(this, total -> {
                    if (total != null) {
                        tvNumber3.setText(String.format("S/ %.2f", total));
                    } else {
                        tvNumber3.setText("S/ 0.00");
                    }
                });

                // Observar el total general
                viewModel.getSumaTotalIngresos().observe(this, total -> {
                    if (total != null) {
                        tvNumber4.setText(String.format("S/ %.2f", total));
                    } else {
                        tvNumber4.setText("S/ 0.00");
                    }
                });

                // Cargar el total general
                viewModel.loadSumaTotalIngresos();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpiar observadores
        viewModel.getTotalPorItem().removeObservers(this);
        viewModel.getSumaTotalIngresos().removeObservers(this);
    }
}