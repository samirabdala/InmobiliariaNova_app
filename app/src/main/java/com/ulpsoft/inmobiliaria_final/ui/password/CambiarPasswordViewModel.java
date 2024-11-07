package com.ulpsoft.inmobiliaria_final.ui.password;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulpsoft.inmobiliaria_final.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarPasswordViewModel extends ViewModel {
    private MutableLiveData<String> mensajeLiveData = new MutableLiveData<>();
    private MutableLiveData<Void> navigateEvent = new MutableLiveData<>();

    public LiveData<String> getMensaje() {
        return mensajeLiveData;
    }

    public LiveData<Void> getNavigateEvent() {
        return navigateEvent;
    }

    public void restablecerPass(String token) {
        if (token == null) {
            Log.d("Token inválido desde Link ", token);
            return;
        }

        ApiClient.InmobiliariaService apiService = ApiClient.getApiInmobiliaria();
        Call<Void> call = apiService.restablecerPass("Bearer " + token);
        Log.d("CambiarPasswordViewModel ", "Token: " + token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mensajeLiveData.postValue("Contraseña restablecida con éxito, revisa tu correo");
                } else {
                    mensajeLiveData.postValue("Error al restablecer la contraseña");
                    navigateEvent.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mensajeLiveData.postValue("Error en la conexión");
            }
        });
    }

    public void cambiarPassword(String token, String passwordActual, String nuevaPassword, String repitePassword) {
        if (token == null) {
            Log.d("Token inválido desde app ", token);
            return;
        }

        // Validaciones
        if (passwordActual.isEmpty() || nuevaPassword.isEmpty() || repitePassword.isEmpty()) {
            mensajeLiveData.postValue("Por favor, complete todos los campos.");
            return;
        }

        if (!nuevaPassword.equals(repitePassword)) {
            mensajeLiveData.postValue("Las nuevas contraseñas no coinciden.");
            return;
        }

        // Lógica para cambiar la contraseña
        ApiClient.InmobiliariaService apiService = ApiClient.getApiInmobiliaria();
        Call<Void> call = apiService.cambiarPass(token, nuevaPassword, passwordActual);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mensajeLiveData.postValue("Contraseña cambiada con éxito");
                    navigateEvent.postValue(null); // Lanza el evento de navegación
                } else {
                    mensajeLiveData.postValue("Revise su contraseña actual");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mensajeLiveData.postValue("Error en la conexión");
            }
        });
    }

    public void restablecerPass(Uri data) {
        if (data == null) {
            return;
        }
        String token = data.getQueryParameter("token");
        if (token != null) {
            restablecerPass(token);
        }
    }

    public String obtenerToken(Uri data, Intent intent) {
        if (data != null) {
            return data.getQueryParameter("token");
        } else {
            return intent.getStringExtra("token");
        }
    }
}
