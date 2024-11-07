package com.ulpsoft.inmobiliaria_final.ui.inmueble;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ulpsoft.inmobiliaria_final.R;
import com.ulpsoft.inmobiliaria_final.databinding.FragmentNuevoInmuebleBinding;
import com.ulpsoft.inmobiliaria_final.model.Inmueble;
import com.ulpsoft.inmobiliaria_final.model.Tipo;

import java.util.HashMap;


public class NuevoInmueble extends Fragment {
    private NuevoInmuebleViewModel vm;
    private FragmentNuevoInmuebleBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    private HashMap<String, Integer> usoMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNuevoInmuebleBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(NuevoInmuebleViewModel.class);
        binding.swEstado.setChecked(true);



        // Configurar el Spinner
        vm.getmTipo().observe(getViewLifecycleOwner(), tipos -> {
            ArrayAdapter<Tipo> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, tipos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spTipo.setAdapter(adapter);
        });

        vm.getmUsos().observe(getViewLifecycleOwner(), usos -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, usos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spUso.setAdapter(adapter);
        });

        // Observo el liveData de la URL de la imagen y actualiza el iv
        vm.getAvatarPath().observe(getViewLifecycleOwner(), avatarPath -> {
            Glide.with(this)
                    .load(avatarPath)
                    .placeholder(R.drawable.nova_gris)
                    .error(R.drawable.nova_gris)
                    .into(binding.imageView);
        });

        Glide.with(this)
                .load(R.drawable.nova_gris) // Imagen por defecto
                .into(binding.imageView);

        // obtengo los tipos y usos para los spinners
        vm.obtenerTipos();
        vm.obtenerUsos();

        binding.btCargarImg.setOnClickListener(v -> pickImage());

        binding.btGuardar.setOnClickListener(v -> {
            try {
                Inmueble inmueble = new Inmueble();
                inmueble.setDireccion(binding.etDireccion.getText().toString());
                inmueble.setPrecio(Double.parseDouble(binding.etPrecio.getText().toString()));
                inmueble.setUso(vm.selectedUso(binding.spUso.getSelectedItemPosition()));
                inmueble.setTipo((Tipo) binding.spTipo.getSelectedItem());
                inmueble.setSuperficie(Double.parseDouble(binding.etSuperficie.getText().toString()));
                inmueble.setAmbientes(Integer.parseInt(binding.etAmbientes.getText().toString().trim()));
                inmueble.setLatitud(Double.parseDouble(binding.etLatitud.getText().toString()));
                inmueble.setLongitud(Double.parseDouble(binding.etLongitud.getText().toString()));
                inmueble.setEstado(binding.swEstado.isChecked());

                inmueble.setImgUrl(vm.getAvatarPath().getValue());
                Log.d("Log", "Uso: " + inmueble.getUso());
                vm.guardarInmueble(inmueble);
            } catch (Exception e) {
                Log.d("Log", "Error: " + e.getMessage());
                Toast.makeText(getContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
            }



        });
        vm.getmInmueble().observe(getViewLifecycleOwner(), inmueble -> {

                binding.etDireccion.setText("");
                binding.etPrecio.setText("");
                binding.spUso.setSelection(0);
                binding.spTipo.setSelection(0);
                binding.etSuperficie.setText("");
                binding.etAmbientes.setText("");
                binding.etLatitud.setText("");
                binding.etLongitud.setText("");
                binding.swEstado.setChecked(true);
                binding.imageView.setImageResource(R.drawable.nova_gris);

        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vm.handleImageResult(requestCode, resultCode, data, PICK_IMAGE_REQUEST);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
}





