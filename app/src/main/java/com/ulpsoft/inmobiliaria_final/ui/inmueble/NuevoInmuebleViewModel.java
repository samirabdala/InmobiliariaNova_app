package com.ulpsoft.inmobiliaria_final.ui.inmueble;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulpsoft.inmobiliaria_final.model.*;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoInmuebleViewModel extends AndroidViewModel {
    private ApiClient.InmobiliariaService api;
    private MutableLiveData<Inmueble> mInmueble;
    private MutableLiveData<ArrayList<Tipo>> mTipo;
    private MutableLiveData<ArrayList<String>> mUsos; // LiveData para los usos

    private Context context;
    private MutableLiveData<String> mImg = new MutableLiveData<>(); // LiveData para el avatar

    public NuevoInmuebleViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getApiInmobiliaria();
        mInmueble = new MutableLiveData<>();
        mTipo = new MutableLiveData<>();
        mUsos = new MutableLiveData<>();

        context = application.getApplicationContext();
    }

    public LiveData<Inmueble> getmInmueble() {
        return mInmueble;
    }

    public LiveData<ArrayList<Tipo>> getmTipo() {
        return mTipo;
    }

    public LiveData<String> getAvatarPath() {
        return mImg;
    }
    public LiveData<ArrayList<String>> getmUsos() {
        return mUsos;
    }


    public void guardarInmueble(Inmueble inmueble) {

        String token = ApiClient.registrado(context).getToken();
        api.guardarInmueble(token, inmueble).enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    mInmueble.setValue(response.body());
                    Toast.makeText(context, "Inmueble guardado", Toast.LENGTH_SHORT).show();
                    resetCampos(); // Restablece los campos tras un guardado exitoso


                } else {
                    Toast.makeText(context, "Error al cargar inmueble", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Error al cargar inmueble: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Toast.makeText(context, "Error al cargar inmueble", Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Error al cargar inmueble: " + t.getMessage());
            }
        });
    }

    public void handleImageResult(int requestCode, int resultCode, Intent data, int pickImageRequest) {
        if (requestCode == pickImageRequest && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            Usuario usuario = ApiClient.registrado(context);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                String fechaActual = sdf.format(new Date());
                String nuevoNombreArchivo = "inmueble_" + usuario.getEmail().trim().split("@")[0] + "_" + fechaActual + ".png";
                String avatarPath = ImageUtil.guardarImagenEnMemoria(getApplication(), imageUri, nuevoNombreArchivo);
                mImg.setValue(avatarPath);
            } catch (IOException e) {
                Toast.makeText(context, "Error al guardar la imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void obtenerTipos() {
        String token = ApiClient.registrado(context).getToken();
        api.obtenerTipos(token).enqueue(new Callback<ArrayList<Tipo>>() {
            @Override
            public void onResponse(Call<ArrayList<Tipo>> call, Response<ArrayList<Tipo>> response) {
                if (response.isSuccessful()) {
                    mTipo.setValue(response.body());
                } else {
                    Toast.makeText(context, "Error al obtener tipos", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Error al obtener tipos: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Tipo>> call, Throwable t) {
                Toast.makeText(context, "Error al obtener tipos", Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Error al obtener tipos: " + t.getMessage());
            }
        });
    }
    public void resetCampos() {
        mInmueble.setValue(new Inmueble()); // Reinicia el ld del inmueble
        mImg.setValue(""); // Restablece el ld de la imagen
    }

    public void obtenerUsos() {
        ArrayList<String> usos = new ArrayList<>();
        usos.add("Residencial");
        usos.add("Comercial");
        mUsos.setValue(usos);
    }

    public int selectedUso(int selectedItemPosition) {
        Log.d("Log", "selectedItemPosition: " + selectedItemPosition);
        Log.d("Log", "selectedItemPosition - 1 : " + (selectedItemPosition - 1));
       if (selectedItemPosition == 0) {
           return 1;
       } else {
           return 2;
       }
    }

}

