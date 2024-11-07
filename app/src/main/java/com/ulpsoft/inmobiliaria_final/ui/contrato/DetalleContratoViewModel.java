package com.ulpsoft.inmobiliaria_final.ui.contrato;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulpsoft.inmobiliaria_final.model.Contrato;
import com.ulpsoft.inmobiliaria_final.model.Inmueble;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> mContrato = new MutableLiveData<>();
    private Context context;
    private ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Contrato> getContrato() {
        return mContrato;
    }

    public void setContrato(int id) {
        api.getContrato(ApiClient.registrado(context).getToken(), id)
                .enqueue(new Callback<Contrato>() {
                    @Override
                    public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                        if (response.isSuccessful()) {
                            Contrato contrato = response.body();
                            mContrato.setValue(contrato);
                        } else {
                            Toast.makeText(context, "Error al obtener contrato: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Contrato> call, Throwable t) {
                        Toast.makeText(context, "Error en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public String getUrl(Inmueble inmueble) {
        String defaultAvatar = "drawable/avatar.jpg"; // Ruta de la imagen por defecto
        return (inmueble != null && inmueble.getImgUrl() != null && !inmueble.getImgUrl().isEmpty()) ? inmueble.getImgUrl() : defaultAvatar;
    }
}
