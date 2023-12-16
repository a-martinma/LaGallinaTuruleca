package com.ldm.ejemplojuegopiratas.juego;
import java.util.List;
import com.ldm.ejemplojuegopiratas.Juego;
import com.ldm.ejemplojuegopiratas.Graficos;
import com.ldm.ejemplojuegopiratas.Input.TouchEvent;
import com.ldm.ejemplojuegopiratas.Pantalla;

public class Ranking extends Pantalla {
    String lineas[] = new String[5];

    public Ranking(Juego juego) {
        super(juego);
        for (int i = 0; i < 5; i++) {
            String tiempoFormateado = String.format("%.2f", Configuraciones.listaTiempos.get(i));
            lineas[i] = "" + (i + 1) + ". " + Configuraciones.listaPuntuaciones.get(i) + " - " + tiempoFormateado + "s";
        }
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
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
        g.drawPixmap(Assets.ranking, 88, 20);
        g.drawPixmap(Assets.columnas, 0, 90);

        int y = 125;
        for (int i = 0; i < 5; i++) {
            dibujarTexto(g, lineas[i], 20, y);
            y += 50;
        }

        g.drawPixmap(Assets.botones, 0, 416, 64, 64, 64, 64);
    }

    public void dibujarTexto(Graficos g, String linea, int x, int y) {
        int len = linea.length();
        for (int i = 0; i < len; i++) {
            char character = linea.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
                g.drawPixmap(Assets.numeros, x, y, srcX, 0, srcWidth, 32);
                x += srcWidth;
            } else if (character == '-') {
                srcX = 20;
                srcWidth = 20;
                g.drawPixmap(Assets.letras, x, y, srcX, 0, srcWidth, 32);
                x += srcWidth;
            } else if (character == 's') {
                srcX = 0;
                srcWidth = 20;
                g.drawPixmap(Assets.letras, x, y, srcX, 0, srcWidth, 32);
                x += srcWidth;
            }else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
                g.drawPixmap(Assets.numeros, x, y, srcX, 0, srcWidth, 32);
                x += srcWidth;
            }
        }
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

