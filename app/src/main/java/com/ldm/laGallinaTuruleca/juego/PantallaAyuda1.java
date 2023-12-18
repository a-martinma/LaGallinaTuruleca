package com.ldm.laGallinaTuruleca.juego;
import java.util.List;
import com.ldm.laGallinaTuruleca.Juego;
import com.ldm.laGallinaTuruleca.Graficos;
import com.ldm.laGallinaTuruleca.Input.TouchEvent;
import com.ldm.laGallinaTuruleca.Pantalla;

public class PantallaAyuda1 extends Pantalla {
    public PantallaAyuda1(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 256 && event.y > 416 ) {
                    juego.setScreen(new PantallaAyuda2(juego));
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    return;
                }
                if (event.x < 64 && event.y > 416) {
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    juego.setScreen(new MainMenuScreen(juego));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();
        g.drawPixmap(Assets.fondo, 0, 0);
        g.drawPixmap(Assets.ayuda1, 64, 100);
        g.drawPixmap(Assets.botones, 256, 416, 0, 64, 64, 64);
        g.drawPixmap(Assets.botones, 0, 416, 64, 64, 64, 64);
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