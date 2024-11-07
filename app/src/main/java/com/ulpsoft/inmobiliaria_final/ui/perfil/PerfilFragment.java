package com.ulpsoft.inmobiliaria_final.ui.perfil;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ulpsoft.inmobiliaria_final.MenuViewModel;
import com.ulpsoft.inmobiliaria_final.R;
import com.ulpsoft.inmobiliaria_final.databinding.FragmentPerfilBinding;
import com.ulpsoft.inmobiliaria_final.model.Propietario;
import com.ulpsoft.inmobiliaria_final.ui.password.CambiarPassword;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;



public class PerfilFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private PerfilViewModel vm;
    private FragmentPerfilBinding binding;
    private MenuViewModel menuViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        vm = new ViewModelProvider(this).get(PerfilViewModel.class);
        menuViewModel = new ViewModelProvider(requireActivity()).get(MenuViewModel.class);

        vm.obtenerPropietario();
        vm.getPropietario().observe(getViewLifecycleOwner(), this::updateProfileFields);

        // Observa el avatar para actualizar la imagen
        vm.getAvatarPath().observe(getViewLifecycleOwner(), avatarPath -> {
            Glide.with(this)
                    .load(avatarPath)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(binding.imageView3);
        });

        binding.btCambiarPass.setOnClickListener(v -> cambiarPass());
        binding.btCambiarAvatar.setOnClickListener(v -> seleccionarImagen());
        binding.btCambiarAvatar.setVisibility(View.GONE);

        vm.getMensajeError().observe(getViewLifecycleOwner(), mensaje ->
                Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show()
        );


        binding.btEditar.setOnClickListener(v -> {

            boolean isEnabled = binding.etNombre.isEnabled();
            vm.setEditingMode(!isEnabled);  // El ViewModel maneja el estado de edición
            binding.etNombre.setEnabled(!isEnabled);
            binding.etApellido.setEnabled(!isEnabled);
            binding.etTelefono.setEnabled(!isEnabled);
            binding.etEmail.setEnabled(!isEnabled);
            binding.etDni.setEnabled(!isEnabled);
            binding.etDireccion.setEnabled(!isEnabled);

            binding.btEditar.setText(isEnabled ? "Editar perfil" : "Guardar");
            binding.btCambiarAvatar.setVisibility(!isEnabled ? View.VISIBLE : View.GONE);

            Propietario prop = new Propietario(
                    binding.etNombre.getText().toString(),
                    binding.etApellido.getText().toString(),
                    binding.etDni.getText().toString(),
                    binding.etTelefono.getText().toString(),
                    binding.etEmail.getText().toString(),
                    binding.etDireccion.getText().toString(),
                    vm.getAvatarOrDefault() // Obtiene la ruta del avatar actual
            );
            menuViewModel.actualizarUsuario(vm.actualizarPropietario(isEnabled,prop));

        });


        return root;

    }

    private void updateProfileFields(Propietario propietario) {
        binding.etNombre.setText(propietario.getNombre());
        binding.etApellido.setText(propietario.getApellido());
        binding.etTelefono.setText(propietario.getTelefono());
        binding.etEmail.setText(propietario.getEmail());
        binding.etDni.setText(String.valueOf(propietario.getDocumento()));
        binding.etDireccion.setText(propietario.getDireccion());

        // Se carga la imagen por defecto o la del propietario
        Glide.with(this)
                .load(vm.getAvatarOrDefault())
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(binding.imageView3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vm.handleImageResult(requestCode, resultCode, data, PICK_IMAGE_REQUEST);
    }



    private void cambiarPass() {
        Intent intent = new Intent(getContext(), CambiarPassword.class);
        intent.putExtra("token", ApiClient.registrado(getContext().getApplicationContext()).getToken());
        startActivity(intent);
    }

    private void seleccionarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
}
