package com.ulpsoft.inmobiliaria_final.request;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ulpsoft.inmobiliaria_final.model.Contrato;
import com.ulpsoft.inmobiliaria_final.model.Inmueble;
import com.ulpsoft.inmobiliaria_final.model.Inquilino;
import com.ulpsoft.inmobiliaria_final.model.LoginRequest;
import com.ulpsoft.inmobiliaria_final.model.Pago;
import com.ulpsoft.inmobiliaria_final.model.Propietario;
import com.ulpsoft.inmobiliaria_final.model.Tipo;
import com.ulpsoft.inmobiliaria_final.model.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.*;


public class ApiClient {
    private static final String datos_usuario = "usuario.dat";
    public static final String URL_BASE = "http://192.168.100.18:5181/api/";


    public static InmobiliariaService getApiInmobiliaria(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(InmobiliariaService.class);
    }



    public static boolean guardarUsuario(Context context, Usuario usuario) {
        File file = new File(context.getFilesDir(), datos_usuario);
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeObject(usuario);
            oos.flush();
            Log.d("Log", "API - Usuario guardado: " + usuario.getEmail());
            return true;

        } catch (IOException e) {
            Log.e("Log", "Error al guardar el usuario", e);
            return false;
        }
    }

    public static Usuario registrado(Context context) {
        File file = new File(context.getFilesDir(), datos_usuario);  // Ubicación del archivo de usuario
        if (!file.exists()) {
            Log.d("Log", "Api - No se encontró un archivo de usuario.");
            return null;  // Retorna null si no existe el archivo
        }

        try (
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis)) {

            Usuario usuario = (Usuario) ois.readObject();  // Deserializamos el objeto Usuario
            Log.d("Log", "Usuario recuperado: " + usuario.getEmail());
            return usuario;

        } catch (IOException | ClassNotFoundException e) {
            Log.e("Log", "Api - Error al recuperar el usuario", e);
            return null;
        }
    }


    public interface InmobiliariaService {

        @POST("auth/login")
        Call<Usuario> login(@Body LoginRequest loginRequest);

        @GET("auth/validar_token")
        Call<Propietario> tokenValido(@Header("Authorization") String token);

        @FormUrlEncoded
        @POST("auth/email")
        Call<Void> mailPass(@Field("email") String email);

        @GET("auth/restablecer_password")
        Call<Void> restablecerPass(@Header("Authorization") String token);

        @FormUrlEncoded
        @PATCH("auth/cambiar_password")
        Call<Void> cambiarPass(@Header("Authorization") String token, @Field("password") String password,@Field("passwordActual") String passwordActual);

        @GET("propietario")
        Call<Propietario> getPropietario(@Header("Authorization") String token);

        @FormUrlEncoded
        @PUT("propietario")
        Call<Void> modificarPropietario( @Header("Authorization") String token,@Field("nombre") String nombre, @Field("apellido") String apellido, @Field("telefono") String telefono, @Field("email") String email, @Field("documento") String documento, @Field("direccion") String direccion, @Field("avatar") String avatar);

        @GET("inmueble")
        Call<List<Inmueble>> getListaInmuebles(@Header("Authorization") String token);

        @GET("inmueble/{id}")
        Call<Inmueble> getInmueble(@Header("Authorization") String token, @Path("id") int id);

        @POST("inmueble/nuevo")
        Call<Inmueble> guardarInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);

        @FormUrlEncoded
        @PATCH("inmueble/CambiarEstado/{id}")
        Call<Void> cambiarEstado(@Header("Authorization") String token, @Path("id") int id, @Field("estado") boolean estado);

        @GET("inmueble/tipos")
        Call<ArrayList<Tipo>> obtenerTipos(@Header("Authorization") String token);

        @GET("contrato")
        Call<List<Contrato>> getListaContrato(@Header("Authorization") String token);

        @GET("contrato/{id}")
        Call<Contrato> getContrato(@Header("Authorization") String token, @Path("id") int id);

        @GET("pago/{idContrato}")
        Call<List<Pago>>getPago(@Header("Authorization") String token, @Path("idContrato") int idContrato);


        @GET("inquilino/{id}")
        Call<Map<String, Object>> getInquilinoById(@Header("Authorization") String token, @Path("id") int id);
    }




}
