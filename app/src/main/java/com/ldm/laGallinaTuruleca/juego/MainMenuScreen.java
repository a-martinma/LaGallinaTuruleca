package com.ldm.laGallinaTuruleca.juego;
import java.util.List;
import com.ldm.laGallinaTuruleca.Juego;
import com.ldm.laGallinaTuruleca.Graficos;
import com.ldm.laGallinaTuruleca.Input.TouchEvent;
import com.ldm.laGallinaTuruleca.Pantalla;

public class MainMenuScreen extends Pantalla {
    public MainMenuScreen(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        if(Configuraciones.sonidoHabilitado) {
            if(!Assets.musicaMenu.isPlaying()) {
                Assets.musicaMenu.play();
                Assets.musicaMenu.setLooping(true);
            }
        }
        Graficos g = juego.getGraphics();
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(inBounds(event, 0, g.getHeight() - 64, 64, 64)) {
                    Configuraciones.sonidoHabilitado = !Configuraciones.sonidoHabilitado;

                    if(Assets.musicaMenu.isPlaying())
                        Assets.musicaMenu.stop();
                    else
                        Assets.musicaMenu.play();

                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                }
                if(inBounds(event, 64, 220, 192, 42) ) {
                    juego.setScreen(new PantallaJuego(juego));
                    if(Configuraciones.sonidoHabilitado)
                    {Assets.pulsar.play(1);
                    }

                    return;
                }
                if(inBounds(event, 64, 220 + 42, 192, 42) ) {
                    juego.setScreen(new Ranking(juego));
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    return;
                }
                if(inBounds(event, 64, 220 + 84, 192, 42) ) {
                    juego.setScreen(new PantallaAyuda1(juego));
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    return;
                }
            }
        }
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.fondo, 0, 0);
        g.drawPixmap(Assets.logo, 32, 20);
        g.drawPixmap(Assets.menuprincipal, 64, 220);
        if(Configuraciones.sonidoHabilitado)
            g.drawPixmap(Assets.botones, 0, 416, 0, 0, 64, 64);
        else
            g.drawPixmap(Assets.botones, 0, 416, 64, 0, 64, 64);
    }

    @Override
    public void pause() {
        Configuraciones.save(juego.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}

