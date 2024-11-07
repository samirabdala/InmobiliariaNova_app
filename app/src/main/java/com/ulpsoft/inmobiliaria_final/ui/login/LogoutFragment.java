package com.ulpsoft.inmobiliaria_final.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulpsoft.inmobiliaria_final.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogoutFragment extends Fragment {
    private static final String datos_usuario = "usuario.dat";
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        context = getContext().getApplicationContext();
        // Llama al método que mostrará el diálogo de confirmación
        mostrarConfirmacionLogout();

        return view;
    }

    private void mostrarConfirmacionLogout() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro de que deseas cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> logout())
                .setNegativeButton("No", (dialog, which) ->{
                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                }).show();
}

    private void logout() {
        Context context = requireContext();
        File file = new File(context.getFilesDir(), datos_usuario);

        if (file.exists()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                // Escribe un contenido vacío en el archivo para dejarlo nulo
                fos.write(new byte[0]);
                Log.d("LogoutFragment", "Sesión cerrada y archivo vaciado");

                // Redirige al usuario a la pantalla de inicio de sesión
                Intent intent = new Intent(context, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                requireActivity().finish();  // Finaliza la actividad actual para que no quede en el stack
            } catch (IOException e) {
                Log.e("LogoutFragment", "Error al vaciar el archivo de sesión", e);
            }
        } else {
            Log.e("LogoutFragment", "El archivo de sesión no existe");
        }
    }


}

