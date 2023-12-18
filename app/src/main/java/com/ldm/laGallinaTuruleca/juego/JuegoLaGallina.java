package com.ldm.laGallinaTuruleca.juego;

import com.ldm.laGallinaTuruleca.Pantalla;
import com.ldm.laGallinaTuruleca.androidimpl.AndroidJuego;

public class JuegoLaGallina extends AndroidJuego {
    @Override
    public Pantalla getStartScreen() {
        return new LoadingScreen(this);
    }
}
