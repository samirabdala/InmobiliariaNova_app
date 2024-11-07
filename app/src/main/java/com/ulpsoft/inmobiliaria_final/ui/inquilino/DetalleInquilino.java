package com.ulpsoft.inmobiliaria_final.ui.inquilino;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulpsoft.inmobiliaria_final.R;

import com.bumptech.glide.Glide;
import com.ulpsoft.inmobiliaria_final.databinding.FragmentDetalleInquilinoBinding;
import com.ulpsoft.inmobiliaria_final.model.Inquilino;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.ulpsoft.inmobiliaria_final.databinding.FragmentDetalleInquilinoBinding;
import com.ulpsoft.inmobiliaria_final.model.Contrato;
import com.ulpsoft.inmobiliaria_final.model.Inquilino;
import com.ulpsoft.inmobiliaria_final.model.Inmueble;

public class DetalleInquilino extends Fragment {
    private DetalleInquilinoViewModel vm;
    private FragmentDetalleInquilinoBinding binding;

    public static DetalleInquilino newInstance() {
        return new DetalleInquilino();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);

        vm = new ViewModelProvider(this).get(DetalleInquilinoViewModel.class);

        int id = getArguments() != null ? getArguments().getInt("id") : -1; // Asegúrate de que el ID sea correcto
        Log.d("DetalleInquilino", "ID: " + id);
        vm.setContrato(id);
        vm.getContrato().observe(getViewLifecycleOwner(), this::cargarContrato);

        return binding.getRoot();
    }

    private void cargarContrato(Contrato contrato) {

        Inquilino inquilino = contrato.getInqui();
        Inmueble inmueble = contrato.getInmu();

        binding.tvNombreDetalle.setText(inquilino.getNombre());
        binding.tvApellidoDetalle.setText(inquilino.getApellido());
        binding.tvDocumentoDetalle.setText(inquilino.getDocumento());
        binding.tvTelefonoDetalle.setText(inquilino.getTelefono());
        binding.tvEmailDetalle.setText(inquilino.getEmail());


        binding.tvDireccionEnDetalleInquilino.setText(inmueble.getDireccion());
        Glide.with(this)
                .load(vm.getUrl(inmueble))
                .placeholder(R.drawable.nova_gris)
                .error(R.drawable.nova_gris)
                .into(binding.imageView);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

