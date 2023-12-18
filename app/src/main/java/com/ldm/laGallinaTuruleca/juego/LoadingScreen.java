package com.ldm.laGallinaTuruleca.juego;

import com.ldm.laGallinaTuruleca.Juego;
import com.ldm.laGallinaTuruleca.Graficos;
import com.ldm.laGallinaTuruleca.Pantalla;
import com.ldm.laGallinaTuruleca.Graficos.PixmapFormat;

public class LoadingScreen extends Pantalla{
    public LoadingScreen(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        Graficos g = juego.getGraphics();
        Assets.fondo = g.newPixmap("fondo.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.menuprincipal = g.newPixmap("menuprincipal.png", PixmapFormat.ARGB4444);
        Assets.botones = g.newPixmap("botones.png", PixmapFormat.ARGB4444);
        Assets.ayuda1 = g.newPixmap("ayuda1.png", PixmapFormat.ARGB4444);
        Assets.ayuda2 = g.newPixmap("ayuda2.png", PixmapFormat.ARGB4444);
        Assets.ayuda3 = g.newPixmap("ayuda3.png", PixmapFormat.ARGB4444);
        Assets.ayuda4 = g.newPixmap("ayuda4.png", PixmapFormat.ARGB4444);
        Assets.ayudaRankingBoton = g.newPixmap("ayudaRankingBoton.png", PixmapFormat.ARGB4444);
        Assets.ayudaRanking = g.newPixmap("ayudaRanking.png", PixmapFormat.ARGB4444);
        Assets.numeros = g.newPixmap("numeros.png", PixmapFormat.ARGB4444);
        Assets.letras = g.newPixmap("letras.png", PixmapFormat.ARGB4444);
        Assets.muro = g.newPixmap("muro.png", PixmapFormat.ARGB4444);
        Assets.preparado = g.newPixmap("preparado.png", PixmapFormat.ARGB4444);
        Assets.menupausa = g.newPixmap("menupausa.png", PixmapFormat.ARGB4444);
        Assets.ranking = g.newPixmap("ranking.png", PixmapFormat.ARGB4444);
        Assets.columnas = g.newPixmap("columnas.png", PixmapFormat.ARGB4444);
        Assets.finjuego = g.newPixmap("finjuego.png", PixmapFormat.ARGB4444);
        Assets.gallinaarriba = g.newPixmap("gallinaarriba.png", PixmapFormat.ARGB4444);
        Assets.gallinaizquierda = g.newPixmap("gallinaizquierda.png", PixmapFormat.ARGB4444);
        Assets.gallinaabajo = g.newPixmap("gallinaabajo.png", PixmapFormat.ARGB4444);
        Assets.gallinaderecha = g.newPixmap("gallinaderecha.png", PixmapFormat.ARGB4444);
        Assets.pollito = g.newPixmap("pollito.png", PixmapFormat.ARGB4444);
        Assets.alimento1 = g.newPixmap("maiz.png", PixmapFormat.ARGB4444);
        Assets.alimento2 = g.newPixmap("trigo.png", PixmapFormat.ARGB4444);
        Assets.alimento3 = g.newPixmap("patata.png", PixmapFormat.ARGB4444);
        Assets.puntostiempogameplay = g.newPixmap("puntostiempogameplay.png", PixmapFormat.ARGB4444);
        Assets.resumen1 = g.newPixmap("resumen1.png", PixmapFormat.ARGB4444);
        Assets.resumen2 = g.newPixmap("resumen2.png", PixmapFormat.ARGB4444);
        Assets.resumen3 = g.newPixmap("resumen3.png", PixmapFormat.ARGB4444);
        Assets.resumen4 = g.newPixmap("resumen4.png", PixmapFormat.ARGB4444);
        Assets.resumen5 = g.newPixmap("resumen5.png", PixmapFormat.ARGB4444);
        Assets.pulsar = juego.getAudio().nuevoSonido("pulsar.ogg");
        Assets.comer = juego.getAudio().nuevoSonido("ataque.ogg");
        Assets.derrota = juego.getAudio().nuevoSonido("derrota.ogg");
        Assets.musicaGameplay = juego.getAudio().nuevaMusica("MusicaGameplay.ogg");
        Assets.musicaMenu = juego.getAudio().nuevaMusica("MusicaMenu.ogg");

        Configuraciones.cargar(juego.getFileIO());
        juego.setScreen(new MainMenuScreen(juego));

        if(Configuraciones.listaPuntuaciones.size() == 0 && Configuraciones.listaTiempos.size() == 0)
            Configuraciones.crearListas();
    }

    @Override
    public void present(float deltaTime) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}