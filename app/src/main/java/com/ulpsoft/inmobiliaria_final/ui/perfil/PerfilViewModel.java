package com.ulpsoft.inmobiliaria_final.ui.perfil;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulpsoft.inmobiliaria_final.model.ImageUtil;
import com.ulpsoft.inmobiliaria_final.model.Propietario;
import com.ulpsoft.inmobiliaria_final.model.Usuario;
import com.ulpsoft.inmobiliaria_final.request.ApiClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.annotation.NonNull;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> propietario;
    private MutableLiveData<String> mensajeError = new MutableLiveData<>();
    private MutableLiveData<String> avatarPathLiveData = new MutableLiveData<>(); // Nuevo LiveData para el avatar
    private MutableLiveData<Boolean> isEditingMode = new MutableLiveData<>(false); // Estado de edición
    Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        propietario = new MutableLiveData<>();
    }

    public LiveData<Propietario> getPropietario() {
        return propietario;
    }

    public LiveData<String> getMensajeError() {
        return mensajeError;
    }

    public LiveData<Boolean> isEditingMode() {
        return isEditingMode; // Para observar el estado de edición
    }

    public LiveData<String> getAvatarPath() { // Método para obtener el avatar
        return avatarPathLiveData;
    }

    public void setEditingMode(boolean editingMode) {
        isEditingMode.setValue(editingMode);
    }

    public void obtenerPropietario() {
        Usuario usuario = ApiClient.registrado(getApplication());
        String token = usuario != null ? usuario.getToken() : null;

        ApiClient.getApiInmobiliaria().getPropietario(token)
                .enqueue(new Callback<Propietario>() {
                    @Override
                    public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Propietario prop = response.body();
                            // Asignar imagen por defecto si el avatar está vacío
                            if (prop.getAvatar() == null || prop.getAvatar().isEmpty()) {
                                prop.setAvatar("drawable/avatar.jpg"); // Ruta de la imagen por defecto
                            }
                            propietario.setValue(prop);
                        } else {
                            mensajeError.postValue("Error al obtener propietario: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Propietario> call, Throwable t) {
                        mensajeError.postValue("Fallo en la conexión: " + t.getMessage());
                    }
                });
    }

    public Usuario actualizarPropietario(Boolean isEditing, Propietario propietario) {
        Usuario usuario = ApiClient.registrado(getApplication());
        if (isEditing) {
            usuario.setNombreCompleto(propietario.getNombreCompleto());
            usuario.setAvatar(propietario.getAvatar());
            usuario.setEmail(propietario.getEmail());
            ApiClient.guardarUsuario(getApplication(), usuario);

            String token = usuario != null ? usuario.getToken() : null;

            Call<Void> call = ApiClient.getApiInmobiliaria().modificarPropietario(
                    token,
                    propietario.getNombre(),
                    propietario.getApellido(),
                    propietario.getTelefono(),
                    propietario.getEmail(),
                    propietario.getDocumento(),
                    propietario.getDireccion(),
                    propietario.getAvatar()
            );

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()) {
                        mensajeError.postValue("Error al actualizar: " + response.message());
                    }
                    else {
                        Toast.makeText(context, "Datos actualizados", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    mensajeError.postValue("Fallo en la conexión: " + t.getMessage());
                }
            });

        }
        return usuario;
    }

    public void handleImageResult(int requestCode, int resultCode, Intent data, int pickImageRequest) {
        if (requestCode == pickImageRequest && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                // Obtener el nombre del propietario para usar en el nombre del archivo
                Propietario currentPropietario = propietario.getValue();
                String email = currentPropietario != null ? currentPropietario.getEmail().trim().split("@")[0] : "usuario";

                // Obtener la fecha actual para el nombre del archivo
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                String fechaActual = sdf.format(new Date());

                // Crear un nuevo nombre de archivo
                String nuevoNombreArchivo = email + "_" + fechaActual + ".png";

                // Guardar la imagen con el nuevo nombre
                String avatarPath = ImageUtil.guardarImagenEnMemoria(getApplication(), imageUri, nuevoNombreArchivo);
                avatarPathLiveData.setValue(avatarPath); // Actualiza el LiveData del avatar

                if (currentPropietario != null) {
                    currentPropietario.setAvatar(avatarPath); // Actualiza la ruta del avatar
                    propietario.setValue(currentPropietario);
                }
            } catch (IOException e) {
                mensajeError.postValue("Error al guardar la imagen: " + e.getMessage());
            }
        }
    }

    public String getAvatarOrDefault() {
        Propietario currentPropietario = propietario.getValue();
        String defaultAvatar = "drawable/avatar.jpg"; // Ruta de la imagen por defecto
        return (currentPropietario != null && currentPropietario.getAvatar() != null && !currentPropietario.getAvatar().isEmpty())
                ? currentPropietario.getAvatar()
                : defaultAvatar;
    }
}


