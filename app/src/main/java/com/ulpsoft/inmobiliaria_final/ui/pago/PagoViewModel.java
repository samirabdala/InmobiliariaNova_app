package com.ulpsoft.inmobiliaria_final.ui.pago;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulpsoft.inmobiliaria_final.model.Contrato;
import com.ulpsoft.inmobiliaria_final.model.Pago;
import com.ulpsoft.inmobiliaria_final.model.Usuario;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Pago>> pagosLd = new MutableLiveData<>();
    Context context;
    private final ApiClient.InmobiliariaService api;

    public PagoViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
        api = ApiClient.getApiInmobiliaria();

    }

    public LiveData<List<Pago>> getPagosLd() {
        return pagosLd;
    }

    public void cargarPagos(int idContrato) {
        Usuario usuario = ApiClient.registrado(context);
        String token = usuario.getToken();
        Call<List<Pago>> call = api.getPago(token , idContrato);
                call.enqueue(new Callback<List<Pago>>() {
                    @Override
                    public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                        if (response.isSuccessful()) {
                            List<Pago> pagos = response.body();
                            pagosLd.setValue(pagos);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Pago>> call, Throwable t) {
                        Toast.makeText(context, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ApiClient", "Error de red", t);

                    }
}
                );
    }
}

