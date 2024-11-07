package com.ulpsoft.inmobiliaria_final.ui.inquilino;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulpsoft.inmobiliaria_final.model.Contrato;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Contrato>> inquilinosLiveData = new MutableLiveData<>();
    private final ApiClient.InmobiliariaService api;
    private final Context context;

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getApiInmobiliaria();
        context = getApplication().getApplicationContext();
    }

    public LiveData<List<Contrato>> getInquilinos() {
        return inquilinosLiveData;
    }

    public void cargarInquilinos(String token) {
        Log.d("InquilinoViewModel", "Cargando inquilinos");
        Call<List<Contrato>> call = api.getListaContrato(token);
        Log.d("InquilinoViewModel", "Llamando a la API");
        call.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                Log.d("InquilinoViewModel", "Llamada de la API exitosa");
                Log.d("InquilinoViewModel", "Respuesta de la API antes del if: " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("InquilinoViewModel", "Respuesta de la API: " + response.body());
                    List<Contrato> contratosValidos = new ArrayList<>(response.body());

                    inquilinosLiveData.setValue(contratosValidos);
                } else {
                    Toast.makeText(context, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable t) {
                Toast.makeText(context, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ApiClient", "Error de red", t);
            }
        });
    }
    }
