package com.ulpsoft.inmobiliaria_final;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulpsoft.inmobiliaria_final.model.Usuario;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;

public class MenuViewModel extends AndroidViewModel {
    private MutableLiveData<Usuario> usuarioLiveData;
    private MutableLiveData<String> mToken = new MutableLiveData<>();
    private MutableLiveData<Boolean> navigateToCambiarPass = new MutableLiveData<>();

    public MenuViewModel(@NonNull Application application) {
        super(application);
        usuarioLiveData = new MutableLiveData<>();
    }

    public LiveData<String> getTokenLiveData() {
        return mToken;
    }

    public LiveData<Usuario> getUsuario() {
        return usuarioLiveData;
    }

    public LiveData<Boolean> getNavigateToCambiarPass() {
        return navigateToCambiarPass;
    }

    public void cargarUsuario(Context context) {
        Usuario usuario = ApiClient.registrado(context); // Llama al método para recuperar el usuario
        if (usuario != null) {
            usuarioLiveData.setValue(usuario); // Actualiza el LiveData con el usuario recuperado
        } else {
            Log.d("MenuViewModel", "Usuario no encontrado");
        }
    }

    public void processDeepLink(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            String token = data.getQueryParameter("token");
            mToken.setValue(token);

            // Indicar que se debe navegar al fragmento CambiarPass
            navigateToCambiarPass.setValue(true);
        }
    }

    public void actualizarUsuario(Usuario usuario) {
        usuarioLiveData.setValue(usuario);
    }
}
