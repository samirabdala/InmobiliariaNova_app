package com.ulpsoft.inmobiliaria_final.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ulpsoft.inmobiliaria_final.databinding.FragmentInicioBinding;

import org.jetbrains.annotations.Nullable;

public class InicioFragment extends Fragment implements OnMapReadyCallback {

    private InicioViewModel inicioViewModel;
    private GoogleMap mMap; // Para almacenar la referencia al GoogleMap
    private FragmentInicioBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        inicioViewModel = new ViewModelProvider(this).get(InicioViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(binding.map.getId());
            mapFragment.getMapAsync(this);


        return rootView;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Observa los cambios en el LiveData del ViewModel
        inicioViewModel.getMapaActual().observe(getViewLifecycleOwner(), mapaActual -> {

                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                mMap.addMarker(new MarkerOptions().position(mapaActual.getPringles1000()).title("Pringles 1000"));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(mapaActual.getPringles1000())
                        .zoom(20)
                        .bearing(50)
                        .tilt(60)
                        .build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mMap.animateCamera(cameraUpdate);

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
