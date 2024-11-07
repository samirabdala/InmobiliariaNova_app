package com.ulpsoft.inmobiliaria_final.ui.contrato;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulpsoft.inmobiliaria_final.R;
import com.ulpsoft.inmobiliaria_final.databinding.FragmentDetalleContratoBinding;
import com.ulpsoft.inmobiliaria_final.model.Contrato;

public class DetalleContrato extends Fragment {
    private DetalleContratoViewModel vm;
    private FragmentDetalleContratoBinding binding;

    public static com.ulpsoft.inmobiliaria_final.ui.inquilino.DetalleInquilino newInstance() {
        return new com.ulpsoft.inmobiliaria_final.ui.inquilino.DetalleInquilino();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);

        vm = new ViewModelProvider(this).get(DetalleContratoViewModel.class);

        int id = getArguments().getInt("id") ;

        vm.setContrato(id);

        // Observa los cambios en el contrato
        vm.getContrato().observe(getViewLifecycleOwner(), this::cargarContrato);

        binding.btPagos.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idContrato", id);
            NavHostFragment.findNavController(this).navigate(R.id.action_detalleContrato_to_pagoFragment, bundle);
        });



        return binding.getRoot();
    }

    private void cargarContrato(Contrato contrato) {
        binding.tvDireccionEnDetalleContrato.setText(contrato.getInmu().getDireccion());
        binding.tvNombreDetalle.setText(contrato.getInqui().getNombre() + " " + contrato.getInqui().getApellido());
        binding.tvFechaInicio.setText(contrato.getFechaInicio().substring(0, 10));
        binding.tvFechaFin.setText(contrato.getFechaFin().substring(0, 10)); // Muestra solo los primeros 10 caracteres
        binding.tvObservacionesDetalle.setText(contrato.getObservaciones());
        binding.tvObservacionesDetalle.setMovementMethod(new ScrollingMovementMethod());

        binding.tvDescripcionDetalle.setText(contrato.getDescripcion());
        binding.tvDescripcionDetalle.setMovementMethod(new ScrollingMovementMethod());
        binding.tvMontoDetalleContrato.setText(contrato.getInmu().getPrecio().toString());
        binding.tvDireccionEnDetalleContrato.setText(contrato.getInmu().getDireccion());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

