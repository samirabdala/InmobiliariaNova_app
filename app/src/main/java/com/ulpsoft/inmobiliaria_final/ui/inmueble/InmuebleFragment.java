package com.ulpsoft.inmobiliaria_final.ui.inmueble;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ulpsoft.inmobiliaria_final.R;
import com.ulpsoft.inmobiliaria_final.databinding.FragmentInmuebleBinding;
import com.ulpsoft.inmobiliaria_final.model.Inmueble;
import com.ulpsoft.inmobiliaria_final.model.Usuario;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;

import java.util.ArrayList;

public class InmuebleFragment extends Fragment implements InmuebleAdapter.OnInmuebleClickListener {

    private FragmentInmuebleBinding binding;
    private InmuebleViewModel ivm;
    private RecyclerView recyclerView;
    private InmuebleAdapter adapter;
    private Usuario usuario;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /// Inicializa el ViewModel y el RecyclerView
        ivm = new ViewModelProvider(this).get(InmuebleViewModel.class);
        recyclerView = binding.rvInmueble;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crea un adaptador con el listener y asígnalo al RecyclerView
        adapter = new InmuebleAdapter(new ArrayList<>(), ivm, this); // 'this' para el listener
        recyclerView.setAdapter(adapter);

        usuario = ApiClient.registrado(getContext());
        String token = usuario.getToken();

        // Carga los inmuebles desde el ViewModel
        ivm.cargarInmuebles(token);

        // Observa los cambios en la lista de inmuebles
        ivm.getInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            adapter.updateInmuebles(inmuebles); // metodo en el adaptador para actualizar la lista
        });

        binding.btNuevo.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_nav_inmueble_to_nuevoInmueble);
        });

        return root;
    }

    @Override
    public void onInmuebleClick(Inmueble inmueble) {
        Bundle args = new Bundle();
        args.putInt("id", inmueble.getId());

        // Usando NavController para navegar al fragmento de detalle
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_menu);
        navController.navigate(R.id.action_nav_inmueble_to_detalleInmueble, args);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
