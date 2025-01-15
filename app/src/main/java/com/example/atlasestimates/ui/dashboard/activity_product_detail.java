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
    private TextView tvNumber4;
    private CotizacionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Inicializar las vistas
        ivProductImageDetail = findViewById(R.id.ivProductImageDetail);
        tvProductNameDetail = findViewById(R.id.tvProductNameDetail);
        tvProductDescriptionDetail = findViewById(R.id.tvProductDescriptionDetail);
        tvNumber4 = findViewById(R.id.tvNumber4);

        // Importante: Usar ViewModelProvider con el application scope
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(CotizacionViewModel.class);

        // Obtener el ítem desde el Intent
        if (getIntent() != null) {
            DashboardFragment.Item item = (DashboardFragment.Item) getIntent().getSerializableExtra("selectedItem");
            if (item != null) {
                ivProductImageDetail.setImageResource(item.getImageResource());
                tvProductNameDetail.setText(item.getName());
                tvProductDescriptionDetail.setText(item.getDescription());
            }
        }

        // Observar el LiveData con try-catch para depuración
        try {
            viewModel.getSumaTotalIngresos().observe(this, sumaTotal -> {
                if (sumaTotal != null && tvNumber4 != null) {
                    runOnUiThread(() -> {
                        try {
                            tvNumber4.setText(String.format("%.2f", sumaTotal));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            });

            // Cargar los ingresos totales
            viewModel.loadSumaTotalIngresos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpiar observadores si es necesario
        viewModel.getSumaTotalIngresos().removeObservers(this);
    }
}