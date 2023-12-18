package com.ldm.laGallinaTuruleca.juego;

import java.util.LinkedList;
import java.util.List;
import android.graphics.Color;

import com.ldm.laGallinaTuruleca.Juego;
import com.ldm.laGallinaTuruleca.Graficos;
import com.ldm.laGallinaTuruleca.Input.TouchEvent;
import com.ldm.laGallinaTuruleca.Pixmap;
import com.ldm.laGallinaTuruleca.Pantalla;

public class PantallaJuego extends Pantalla {
    enum EstadoJuego {
        Preparado,
        Ejecutandose,
        Pausado,
        FinJuego
    }

    EstadoJuego estado = EstadoJuego.Preparado;
    Mundo mundo;
    int antiguaPuntuacion = 0;
    String puntuacion = "0";
    long tiempoInicio;
    long tiempoFinal;
    float tiempoTotal;
    Cronometro cronometro;
    LinkedList<Float> listaPausas = new LinkedList<>();
    long tiempoInicioPausa;
    long tiempoFinalPausa;
    float tiempoTotalPausa;
    float tiempoReal;
    boolean vieneDePausa = false;


    public PantallaJuego(Juego juego) {
        super(juego);
        mundo = new Mundo();
        this.cronometro = new Cronometro();

    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        if(estado == EstadoJuego.Preparado)
            updateReady(touchEvents);
        if(estado == EstadoJuego.Ejecutandose) {
            updateRunning(touchEvents, deltaTime);
        }
        if(estado == EstadoJuego.Pausado)
            updatePaused(touchEvents);
        if(estado == EstadoJuego.FinJuego)
            updateGameOver(touchEvents);

    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if(touchEvents.size() > 0) {
            this.tiempoInicio = System.currentTimeMillis();
            estado = EstadoJuego.Ejecutandose;
        }
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        if(Configuraciones.sonidoHabilitado) {
            Assets.musicaMenu.stop();
            Assets.musicaGameplay.play();
            Assets.musicaGameplay.setLooping(true);
        }

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x < 64 && event.y < 64) {
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    this.tiempoInicioPausa = System.currentTimeMillis();
                    estado = EstadoJuego.Pausado;
                    return;
                }
            }
            if(event.type == TouchEvent.TOUCH_DOWN) {
                if(event.x < 64 && event.y > 416) {
                    mundo.gallina.girarIzquierda();
                }
                if(event.x > 256 && event.y > 416) {
                    mundo.gallina.girarDerecha();
                }
            }
        }

        mundo.update(deltaTime);
        if(mundo.finalJuego) {
            this.tiempoFinal = System.currentTimeMillis();
            this.tiempoTotal = (this.tiempoFinal - this.tiempoInicio) / 1000f;

            if(Configuraciones.sonidoHabilitado)
                Assets.derrota.play(1);
            estado = EstadoJuego.FinJuego;
        }
        if(antiguaPuntuacion != mundo.puntuacion) {
            antiguaPuntuacion = mundo.puntuacion;
            puntuacion = "" + antiguaPuntuacion;
            if(Configuraciones.sonidoHabilitado)
                Assets.comer.play(1);
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 80 && event.x <= 240) {
                    if(event.y > 100 && event.y <= 148) {
                        if(Configuraciones.sonidoHabilitado)
                            Assets.pulsar.play(1);
                        this.tiempoFinalPausa = System.currentTimeMillis();
                        this.tiempoTotalPausa = this.tiempoFinalPausa - this.tiempoInicioPausa;
                        listaPausas.add(this.tiempoTotalPausa);
                        this.vieneDePausa = true;
                        estado = EstadoJuego.Ejecutandose;

                        return;
                    }
                    if(event.y > 148 && event.y < 196) {
                        if(Configuraciones.sonidoHabilitado)
                            Assets.pulsar.play(1);
                        Assets.musicaGameplay.stop();
                        juego.setScreen(new MainMenuScreen(juego));
                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        if(Configuraciones.sonidoHabilitado) {
            Assets.musicaGameplay.stop();
            Assets.musicaMenu.play();
            Assets.musicaMenu.setLooping(true);
        }
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 128 && event.x <= 192 &&
                        event.y >= 200 && event.y <= 264) {
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
        drawWorld(mundo);
        if(estado == EstadoJuego.Preparado)
            drawReadyUI();
        if(estado == EstadoJuego.Ejecutandose){
            drawRunningUI();

            //En este pequeño bloque se controla que se muestre bien el tiempo en el caso de que haya habido pausas
            float tiempoPausas = 0;
            for(Float pausa : listaPausas)
                tiempoPausas += pausa;
            long act = (System.currentTimeMillis() - tiempoInicio - (long) tiempoPausas);

            //Se muestra el tiempo actual en el cronometro
            cronometro.imprimirCronometro(g, act);
        }
        if(estado == EstadoJuego.Pausado)
            drawPausedUI();
        if(estado == EstadoJuego.FinJuego)
            drawGameOverUI();
    }

    private void drawWorld(Mundo mundo) {
        Graficos g = juego.getGraphics();
        Gallina gallina = mundo.gallina;
        Pollitos head = gallina.partes.get(0);
        Comida comida = mundo.comida;

        Pixmap stainPixmap = null;
        if(comida.tipo== Comida.TIPO_1)
            stainPixmap = Assets.alimento1;
        if(comida.tipo == Comida.TIPO_2)
            stainPixmap = Assets.alimento2;
        if(comida.tipo == Comida.TIPO_3)
            stainPixmap = Assets.alimento3;
        int x = comida.x * 32;
        int y = comida.y * 32;
        g.drawPixmap(stainPixmap, x, y);

        g.drawPixmap(Assets.muro, 3 * 32, 3 * 32);
        g.drawPixmap(Assets.muro, 6 * 32, 9 * 32);

        int len = gallina.partes.size();
        for(int i = 1; i < len; i++) {
            Pollitos part = gallina.partes.get(i);
            x = part.x * 32;
            y = part.y * 32;
            g.drawPixmap(Assets.pollito, x, y);
        }

        Pixmap headPixmap = null;
        if(gallina.direccion == Gallina.ARRIBA)
            headPixmap = Assets.gallinaarriba;
        if(gallina.direccion == Gallina.IZQUIERDA)
            headPixmap = Assets.gallinaizquierda;
        if(gallina.direccion == Gallina.ABAJO)
            headPixmap = Assets.gallinaabajo;
        if(gallina.direccion == Gallina.DERECHA)
            headPixmap = Assets.gallinaderecha;
        x = head.x * 32 + 16;
        y = head.y * 32 + 16;
        g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
    }

    private void drawReadyUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.preparado, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawRunningUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.botones, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.botones, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.botones, 256, 416, 0, 64, 64, 64);
        g.drawPixmap(Assets.puntostiempogameplay, 0, 425);
        drawText(g, puntuacion, g.getWidth() / 2 - puntuacion.length()*20 / 2 + 12, g.getHeight() - 42);
    }

    private void drawPausedUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.menupausa, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.finjuego, 62, 100);
        g.drawPixmap(Assets.botones, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        drawText(g, puntuacion, 265, 420);
        this.tiempoReal = this.tiempoTotal;
        for(Float pausa : listaPausas)
            tiempoReal = tiempoReal - pausa/1000;
        String tiempoFormateado = String.format("%.2f", tiempoReal);

        //Elección de assets a la hora de mostrar el resumen de la partida
        if(tiempoReal < 10.0f) {
            drawText(g, tiempoFormateado, 236, 447);
            g.drawPixmap(Assets.resumen1, 0, 420);
        }
        else if (tiempoReal > 10.0f && tiempoReal < 100.0f){
            if(Integer.parseInt(puntuacion)>=100){
                drawText(g, tiempoFormateado, 242, 447);
                g.drawPixmap(Assets.resumen2, 0, 420);
            }else{
                drawText(g, tiempoFormateado, 242, 447);
                g.drawPixmap(Assets.resumen4, 0, 420);
            }
        }else{
            if(Integer.parseInt(puntuacion)>=100) {
                drawText(g, tiempoFormateado, 242, 447);
                g.drawPixmap(Assets.resumen3, 0, 420);
            }else{
                drawText(g, tiempoFormateado, 242, 447);
                g.drawPixmap(Assets.resumen5, 0, 420);
            }
        }
    }

    public void drawText(Graficos g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numeros, x - 78, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }


    @Override
    public void pause() {
        if(estado == EstadoJuego.Ejecutandose)
            estado = EstadoJuego.Pausado;

        this.tiempoReal = this.tiempoTotal;

        for(Float pausa : listaPausas)
            tiempoReal = tiempoReal - pausa/1000;

        if(mundo.finalJuego) {
            Configuraciones.addScore(mundo.puntuacion, tiempoReal);

            Configuraciones.save(juego.getFileIO());
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}