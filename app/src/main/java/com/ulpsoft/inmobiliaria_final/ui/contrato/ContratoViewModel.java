package com.ulpsoft.inmobiliaria_final.ui.contrato;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.ulpsoft.inmobiliaria_final.R;
import com.ulpsoft.inmobiliaria_final.model.Contrato;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Contrato>> contratosLiveData = new MutableLiveData<>();
    private final ApiClient.InmobiliariaService api;
    private final Context context;

    public ContratoViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getApiInmobiliaria();
        context = getApplication().getApplicationContext();
    }

    public LiveData<List<Contrato>> getContratos() {
        return contratosLiveData;
    }


    public void cargarContratos(String token) {
        Log.d("ContratoViewModel", "Cargando inquilinos");
        Call<List<Contrato>> call = api.getListaContrato(token);
        Log.d("ContratoViewModel", "Llamando a la API");
        call.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<Contrato> contratosValidos = new ArrayList<>(response.body());
                    contratosLiveData.setValue(contratosValidos);
                } else {
                    Toast.makeText(context, "No hay contratos activos", Toast.LENGTH_SHORT).show();
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
