package com.ulpsoft.inmobiliaria_final.ui.login;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulpsoft.inmobiliaria_final.Menu;
import com.ulpsoft.inmobiliaria_final.model.LoginRequest;
import com.ulpsoft.inmobiliaria_final.model.Propietario;
import com.ulpsoft.inmobiliaria_final.model.Usuario;
import com.ulpsoft.inmobiliaria_final.ui.password.Recuperar_pass;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria();
    public Context context;
    private MutableLiveData<Void> navigateToRecuperarPass = new MutableLiveData<>();
    public LiveData<Void> getNavigateToRecuperarPass() {
        return navigateToRecuperarPass;
    }
    private final MutableLiveData<String> phoneNumber = new MutableLiveData<>("tel:2664536789");

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();

    }

//
//    public void recuperarPass() {
//        Toast.makeText(context, " recuperar", Toast.LENGTH_SHORT).show();
//        navigateToRecuperarPass.setValue(null);
//    }

    public void login(String email, String pass) {

        LoginRequest lg = new LoginRequest(email, pass);
        Log.d("Log", "LoginVm - Email: " + email + ", Password: " + pass);

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(context, "Debe rellenar todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Usuario> llamada = api.login(lg);
        llamada.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Log.d("Logd", "LoginVm - Código: " + response.code());
                Log.d("Logd", "LoginVm - Cuerpo: " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    ApiClient.guardarUsuario(context, usuario);
                    Log.d("Logd", "LoginVm - token: " + usuario.getToken());
                    Log.d("Logd", "LoginVm Usuario" + usuario.getToken());
                    Toast.makeText(context, "Bienvenido " + usuario.getNombreCompleto(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, Menu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable throwable) {
                Log.e("Logd", "LoginVm - Error de red", throwable);
                Toast.makeText(context, "Error de red: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void recuperarPass() {
        Intent intent = new Intent(context, Recuperar_pass.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public LiveData<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void realizarLlamada(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(phoneNumber.getValue()));
            activity.startActivity(intent);
        }
    }
    public void tokenValido() {
        Usuario usuario = ApiClient.registrado(context);
        if (usuario != null) {
            Call<Propietario> llamada = api.tokenValido(usuario.getToken());

            llamada.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful() && response.body().getApellido() != null) {
                        Log.d("LoginVm", "Token válido, redirigiendo...");
                        Log.d("LoginVm", response.body().getApellido());

                        Intent intent = new Intent(context, Menu.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Log.e("LoginVm", "Error de autenticación: " + response.code());
                        if (response.errorBody() != null) {
                            try {
                                Log.e("LoginVm", "Detalle del error: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable throwable) {
                    Log.e("LoginVm", "Error de red o de comunicación: " + throwable.getMessage());
                }
            });
        } else {
            Log.e("LoginVm", "El objeto Usuario es null");
        }
    }


}