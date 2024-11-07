package com.ulpsoft.inmobiliaria_final.ui.password;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ulpsoft.inmobiliaria_final.databinding.ActivityCambiarPasswordBinding;

public class CambiarPassword extends AppCompatActivity {
    private ActivityCambiarPasswordBinding binding;
    private CambiarPasswordViewModel vm;
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCambiarPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = new ViewModelProvider(this).get(CambiarPasswordViewModel.class);

        vm.getMensaje().observe(this, mensaje -> {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        });

        vm.getNavigateEvent().observe(this, event -> {
            finish();
        });

        Uri data = getIntent().getData();
        vm.restablecerPass(data);

        binding.btCambiar.setOnClickListener(v -> {
            String passwordActual = binding.etPassActual.getText().toString().trim();
            String nuevaPassword = binding.etNueva1.getText().toString().trim();
            String repitePassword = binding.etNueva2.getText().toString().trim();
            String token = vm.obtenerToken(data, getIntent());
            vm.cambiarPassword(token, passwordActual, nuevaPassword, repitePassword);
        });
    }
}


