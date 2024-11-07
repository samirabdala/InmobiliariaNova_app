package com.ulpsoft.inmobiliaria_final.ui.contrato;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulpsoft.inmobiliaria_final.R;
import com.ulpsoft.inmobiliaria_final.databinding.FragmentContratoBinding;
import com.ulpsoft.inmobiliaria_final.databinding.FragmentInmuebleBinding;
import com.ulpsoft.inmobiliaria_final.model.Contrato;
import com.ulpsoft.inmobiliaria_final.model.Usuario;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;
import com.ulpsoft.inmobiliaria_final.ui.inmueble.InmuebleAdapter;
import com.ulpsoft.inmobiliaria_final.ui.inmueble.InmuebleViewModel;

import java.util.ArrayList;

public class ContratoFragment extends Fragment implements ContratoAdapter.OnContratoClickListener {

    private FragmentContratoBinding binding;
    private ContratoViewModel vm;
    private RecyclerView recyclerView;
    private ContratoAdapter adapter;
    private Usuario usuario;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /// inicializa el ViewModel y el RecyclerView
        vm = new ViewModelProvider(this).get(ContratoViewModel.class);
        recyclerView = binding.rvContrato;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // crea un adaptador con el listener y asigna al RecyclerView
        adapter = new ContratoAdapter(new ArrayList<>(), vm, this); // 'this' para el listener
        recyclerView.setAdapter(adapter);

        usuario = ApiClient.registrado(getContext());
        String token = usuario.getToken();

        vm.cargarContratos(token);

        vm.getContratos().observe(getViewLifecycleOwner(), contratoes -> {
            adapter.updateContratos(contratoes); // metodo en el adaptador para actualizar la lista
        });


        return root;
    }

    @Override
    public void onContratoClick(Contrato contrato) {
        Bundle args = new Bundle();
        args.putInt("id", contrato.getId());

        // uso NavController para navegar al fragmento de detalle
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_menu);
        navController.navigate(R.id.action_contratoFragment_to_detalleContrato, args);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

