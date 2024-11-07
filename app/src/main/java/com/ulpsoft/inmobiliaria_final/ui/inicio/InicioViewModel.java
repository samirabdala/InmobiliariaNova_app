package com.ulpsoft.inmobiliaria_final.ui.inicio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

public class InicioViewModel extends ViewModel {

    // Modelo para almacenar los puntos en el mapa
    public static class MapaActual {
        private final LatLng pringles1000;


        public MapaActual(LatLng sanLuis) {
            this.pringles1000 = sanLuis;

        }

        public LatLng getPringles1000() {
            return pringles1000;
        }


    }

    // LiveData que almacenará la información del mapa
    private MutableLiveData<MapaActual> mapaActual;

    public InicioViewModel() {
        // Inicializa los puntos del mapa con coordenadas predeterminadas
        mapaActual = new MutableLiveData<>();
        mapaActual.setValue(new MapaActual(
                new LatLng(-33.302835, -66.337399)
        ));
    }

    public LiveData<MapaActual> getMapaActual() {
        return mapaActual;
    }
}
