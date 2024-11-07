package com.ulpsoft.inmobiliaria_final.ui.inquilino;

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
import com.ulpsoft.inmobiliaria_final.databinding.FragmentInquilinoBinding;
import com.ulpsoft.inmobiliaria_final.model.Contrato;
import com.ulpsoft.inmobiliaria_final.model.Inquilino;
import com.ulpsoft.inmobiliaria_final.model.Inmueble;
import com.ulpsoft.inmobiliaria_final.model.Usuario;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;
import java.util.ArrayList;


public class InquilinoFragment extends Fragment implements InquilinoAdapter.OnInquilinoClickListener {
    private FragmentInquilinoBinding binding;
    private InquilinoViewModel ivm;
    private RecyclerView recyclerView;
    private InquilinoAdapter adapter;
    private Usuario usuario;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInquilinoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ivm = new ViewModelProvider(this).get(InquilinoViewModel.class);
        recyclerView = binding.rvInquilino;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new InquilinoAdapter(new ArrayList<>(), ivm, this);
        recyclerView.setAdapter(adapter);

        usuario = ApiClient.registrado(getContext());
        String token = usuario.getToken();

        ivm.cargarInquilinos(token);

        ivm.getInquilinos().observe(getViewLifecycleOwner(), contratos -> {
            adapter.updateInquilinos(contratos); // Actualiza la lista con contratos
        });

        return root;
    }

    @Override
    public void onInquilinoClick(Contrato contrato, Inquilino inquilino, Inmueble inmueble) {
        Bundle args = new Bundle();
        args.putInt("id", contrato.getId());
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_menu);
       navController.navigate(R.id.nav_detallle_inquilino, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
