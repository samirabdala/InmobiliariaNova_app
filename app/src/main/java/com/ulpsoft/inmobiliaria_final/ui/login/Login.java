package com.ulpsoft.inmobiliaria_final.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import com.squareup.seismic.ShakeDetector;

import com.ulpsoft.inmobiliaria_final.databinding.ActivityLoginBinding;
import com.ulpsoft.inmobiliaria_final.ui.password.Recuperar_pass;

public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel vm;
    private ShakeDetector shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa el binding antes de setContentView
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        vm.tokenValido();
        setContentView(binding.getRoot());

        binding.btISesion.setOnClickListener(v -> {
            Log.d("DEBUG", "Botón Iniciar Sesión presionado");

            String user = binding.etUsuario.getText().toString();
            String pass = binding.etPassword.getText().toString();

            vm.login(user, pass);
        });

        binding.btRecuperar.setOnClickListener(v -> {
            vm.recuperarPass();
        });

//        // Observa el LiveData para manejar la navegación
//        vm.getNavigateToRecuperarPass().observe(this, unused -> {
//            Intent intent = new Intent(this, Recuperar_pass.class);
//            startActivity(intent);
//        });



        ShakeDetector.Listener listener = () -> {
            vm.realizarLlamada(this);
        };

        shaker = new ShakeDetector(listener);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        shaker.start(sensorManager, SensorManager.SENSOR_DELAY_UI);
    }

}


