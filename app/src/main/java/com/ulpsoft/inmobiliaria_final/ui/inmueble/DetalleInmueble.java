package com.ulpsoft.inmobiliaria_final.ui.inmueble;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ulpsoft.inmobiliaria_final.R;
import com.ulpsoft.inmobiliaria_final.databinding.FragmentDetalleInmuebleBinding;
import com.ulpsoft.inmobiliaria_final.model.Inmueble;

public class DetalleInmueble extends Fragment {

    private DetalleInmuebleViewModel vm;
    private FragmentDetalleInmuebleBinding binding;

    public static DetalleInmueble newInstance() {
        return new DetalleInmueble();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);

        vm = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);

        int id = getArguments().getInt("id");
        Log.d("DetalleContrato", "ID: " + id);
        vm.setmInmueble(id);

        // Observa los cambios en el inmueble
        vm.getmInmueble().observe(getViewLifecycleOwner(), this::cargarInmueble);

        vm.getmInmueble().observe(getViewLifecycleOwner(), inmueble -> {

                binding.swEstado.setChecked(inmueble.getEstado());

        });

        binding.swEstado.setOnCheckedChangeListener((buttonView, isChecked) -> {
            vm.cambiarEstado(id, isChecked);  // Llama al ViewModel y pasa el estado actual del switch
        });

        return binding.getRoot();
    }


    private void cargarInmueble(Inmueble inmueble) {
        if (inmueble != null) {
            binding.tvDireccionEnDetalleInmueble.setText(inmueble.getDireccion());
            binding.tvUsoDetalle.setText(inmueble.usoDescripcion());
            binding.tvAmbientesDetalle.setText(Integer.toString(inmueble.getAmbientes()));
            binding.tvTipoDetalle.setText(inmueble.getTipo().getNombre());
            binding.tvPrecioDetalle.setText("$" + inmueble.getPrecio().toString());
            binding.tvSuperficieDetalle.setText(inmueble.getSuperficie().toString());
            binding.tvLatitudDetalle.setText(inmueble.getLatitud().toString());
            binding.tvLongitudDetalle.setText(inmueble.getLongitud().toString());
            binding.swEstado.setChecked(inmueble.getEstado());

            Glide.with(this)
                    .load(vm.getUrl())
                    .placeholder(R.drawable.nova_gris)
                    .error(R.drawable.nova_gris)
                    .into(binding.imageView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
