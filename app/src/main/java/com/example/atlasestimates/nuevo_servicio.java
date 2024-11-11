package com.example.atlasestimates;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.atlasestimates.databinding.FragmentHomeBinding;

public class nuevo_servicio extends AppCompatActivity {

    private FragmentHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_servicio);

        // Aquí ya no es necesario configurar el Spinner ni agregar un listener
    }
}
