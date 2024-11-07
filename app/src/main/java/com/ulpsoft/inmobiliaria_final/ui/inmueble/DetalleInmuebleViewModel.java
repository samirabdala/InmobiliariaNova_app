package com.ulpsoft.inmobiliaria_final.ui.inmueble;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulpsoft.inmobiliaria_final.model.Inmueble;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;

import retrofit2.Call;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();
    Context context;
    ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();

    public DetalleInmuebleViewModel(@NonNull Application application) {

        super(application);
        context = application.getApplicationContext();

    }

    public LiveData<Inmueble> getmInmueble() {
        return mInmueble;
    }


    public void setmInmueble(int id) {
        api.getInmueble(ApiClient.registrado(context).getToken(), id)
                .enqueue(new retrofit2.Callback<Inmueble>() {
                    @Override
                    public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                        if (response.isSuccessful()) {
                            Inmueble inmueble = response.body();
                            mInmueble.setValue(inmueble);

                        } else {
                            Toast.makeText(context, "Error al obtener inmueble: " + response.message(), Toast.LENGTH_SHORT).show();
                            Log.e("API_ERROR", "Error al obtener inmueble: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Inmueble> call, Throwable t) {
                        Toast.makeText(context, "Error en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public String getUrl() {
        Inmueble currentInmueble = mInmueble.getValue();
        String defaultAvatar = "drawable/avatar.jpg"; // Ruta de la imagen por defecto
        return (currentInmueble != null && currentInmueble.getImgUrl() != null && !currentInmueble.getImgUrl().isEmpty())
                ? currentInmueble.getImgUrl()
                : defaultAvatar;
    }

    public void cambiarEstado(int id, boolean estado) {
        Inmueble currentInmueble = mInmueble.getValue();
        if (currentInmueble != null) {
            currentInmueble.setEstado(estado);
            api.cambiarEstado(ApiClient.registrado(context).getToken(), id, estado)
                    .enqueue(new retrofit2.Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                mInmueble.setValue(currentInmueble);
                            } else {
                                Toast.makeText(context, "Error al cambiar estado: " + response.message(), Toast.LENGTH_SHORT).show();
                                Log.e("API_ERROR", "Error al cambiar estado: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(context, "Error en la conexion: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }




}