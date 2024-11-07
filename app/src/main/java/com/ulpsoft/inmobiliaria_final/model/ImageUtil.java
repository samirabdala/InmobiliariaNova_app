package com.ulpsoft.inmobiliaria_final.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUtil {

    private static final String TAG = "ImageUtil";

    // Convierte un Uri de imagen a un string que representa la ruta del archivo
    public static String guardarImagenEnMemoria(Context context, Uri imageUri, String fileName) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
        File directory = context.getFilesDir();
        File file = new File(directory, fileName);

        // Guardar la imagen en el almacenamiento interno
        try (OutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } finally {
            inputStream.close();
        }

        return file.getAbsolutePath(); // Retorna la ruta completa del archivo guardado
    }


    // Convierte un string (ruta de archivo) en un Bitmap
    public static Bitmap getBitmapFromFilePath(String filePath) {
        return BitmapFactory.decodeFile(filePath);
    }
}
