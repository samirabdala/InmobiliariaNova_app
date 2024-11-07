package com.ulpsoft.inmobiliaria_final.ui.inmueble;

import android.app.Application;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ulpsoft.inmobiliaria_final.R;
import com.ulpsoft.inmobiliaria_final.model.Inmueble;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;
import java.util.List;
import android.content.Context;

import retrofit2.*;

public class InmuebleViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Inmueble>> inmueblesLiveData = new MutableLiveData<>();
    private final ApiClient.InmobiliariaService api;
    private final Context context;

    public InmuebleViewModel(@NonNull Application aplication) {
        super(aplication);
        api = ApiClient.getApiInmobiliaria(); // Inicializa el servicio API
        context = getApplication().getApplicationContext();
    }

    public LiveData<List<Inmueble>> getInmuebles() {
        return inmueblesLiveData;
    }

    public void getImagenUrl(Inmueble inmueble, ImageView iv) {
        String imgUrl = inmueble.getImgUrl(); // Obtengo la URL de la imagen del inmueble

        if (imgUrl != null && !imgUrl.isEmpty()) {
            Glide.with(context)
                    .load(inmueble.getImgUrl())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(16)))

                    .placeholder(R.drawable.nova_gris)
                    .error(R.drawable.nova_gris)
                    .into(iv);


        } else {
            Glide.with(context)
                    .load("drawable/nova_gris")
                    .placeholder(R.drawable.nova_gris)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(50)))

                    .error(R.drawable.nova_gris)
                    .into(iv);
        }
}
    public void cargarInmuebles(String token) {
        Call<List<Inmueble>> call = api.getListaInmuebles(token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    inmueblesLiveData.setValue(response.body());
                } else {

                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                Toast.makeText(context, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ApiClient", "Error de red", t);
            }
        });
    }
}
