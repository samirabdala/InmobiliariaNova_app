package com.ulpsoft.inmobiliaria_final.ui.password;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulpsoft.inmobiliaria_final.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recuperar_passViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> ld1 = new MutableLiveData<>();
    private MutableLiveData<String> ld2 = new MutableLiveData<>();
    private MutableLiveData<Boolean> navigateBack = new MutableLiveData<>(false);
    public LiveData<Boolean> getNavigateBack() {
        return navigateBack;
    }
    public LiveData<String> getLd1() {
        return ld1;
    }

    public LiveData<String> getLd2() {
        return ld2;
    }

    public Recuperar_passViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }

    public boolean validarEmails(String email, String emailRepetido) {
        return email.equals(emailRepetido);
    }

    public void recuperarPass(String email, String emailRepetido) {
        if (email.isEmpty() || emailRepetido.isEmpty()) {
            Toast.makeText(context, "Debe rellenar todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!validarEmails(email, emailRepetido)) {
            Toast.makeText(context, "Los correos electrónicos no coinciden.", Toast.LENGTH_SHORT).show();
            return;
        }



        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();

        Call<Void> llamada = api.mailPass(email);
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("ApiClient", "Respuesta: " + response.code());
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Se ha enviado un correo electrónico para restablecer la contraseña", Toast.LENGTH_SHORT).show();
                    ld1.setValue("");
                    ld2.setValue("");
                    navigateBack.setValue(null); // Indica que debe regresar
                } else {
                    Toast.makeText(context, "Revise su correo electrónico", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Log.e("ApiClient", "Error de red", throwable);
                Toast.makeText(context, "Error en la red:", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
