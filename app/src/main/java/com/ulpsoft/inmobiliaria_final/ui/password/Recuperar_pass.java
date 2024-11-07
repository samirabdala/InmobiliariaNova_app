package com.ulpsoft.inmobiliaria_final.ui.password;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ulpsoft.inmobiliaria_final.R;
import com.ulpsoft.inmobiliaria_final.databinding.ActivityRecuperarPassBinding;

public class Recuperar_pass extends AppCompatActivity {
    private ActivityRecuperarPassBinding binding;
    private Recuperar_passViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperarPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vm = new ViewModelProvider(this).get(Recuperar_passViewModel.class);

        vm.getLd1().observe(this, email -> binding.etCorreo1.setText(email));
        vm.getLd2().observe(this, emailRepetido -> binding.etCorreo2.setText(emailRepetido));

        // Observa el LiveData para manejar la navegacion
       // vm.getNavigateBack().observe(this, unused -> finish());

        binding.btRecuperar.setOnClickListener(v -> {

            vm.recuperarPass(binding.etCorreo1.getText().toString(), binding.etCorreo2.getText().toString());
        });



    }

}