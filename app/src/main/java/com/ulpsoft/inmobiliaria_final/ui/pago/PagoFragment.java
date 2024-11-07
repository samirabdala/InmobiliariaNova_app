package com.ulpsoft.inmobiliaria_final.ui.pago;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulpsoft.inmobiliaria_final.R;

import java.util.ArrayList;

public class PagoFragment extends Fragment {
    private RecyclerView recyclerView;
    private PagoAdapter pagoAdapter;
    private PagoViewModel pagoViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pago, container, false);
        recyclerView = view.findViewById(R.id.rvPagos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        pagoAdapter = new PagoAdapter(new ArrayList<>());
        recyclerView.setAdapter(pagoAdapter);

        pagoViewModel = new ViewModelProvider(this).get(PagoViewModel.class);

        int idContrato = getArguments().getInt("idContrato");

        pagoViewModel.cargarPagos(idContrato);

        pagoViewModel.getPagosLd().observe(getViewLifecycleOwner(), pagos -> {
            pagoAdapter.setPagos(pagos);
        });

        return view;
    }
}
