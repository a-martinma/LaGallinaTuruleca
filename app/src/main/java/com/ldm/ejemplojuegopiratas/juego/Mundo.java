package com.ldm.ejemplojuegopiratas.juego;

import java.util.Random;

public class Mundo {
    static final int MUNDO_ANCHO = 10;
    static final int MUNDO_ALTO = 13;
    static final int INCREMENTO_PUNTUACION = 10;
    static final float TICK_INICIAL = 0.5f;
    static final float TICK_DECREMENTO = 0.05f;

    public JollyRoger jollyroger;
    public Botin botin;
    public boolean finalJuego = false;
    public int puntuacion = 0;

    boolean campos[][] = new boolean[MUNDO_ANCHO][MUNDO_ALTO];
    Random random = new Random();
    float tiempoTick = 0;
    static float tick = TICK_INICIAL;

    public Mundo() {
        jollyroger = new JollyRoger();
        colocarBotin();
        colocarMuros();
    }

    private void colocarMuros() {
        int x1 = 3;
        int y1 = 3;
        int x2 = 6;
        int y2 = 9;
        campos[x1][y1] = true;
        campos[x2][y2] = true;
    }

    private void colocarBotin() {
        for (int x = 0; x < MUNDO_ANCHO; x++) {
            for (int y = 0; y < MUNDO_ALTO; y++) {
                campos[x][y] = false;
            }
        }

        campos[3][3] = true;
        campos[6][9] = true;

        int len = jollyroger.partes.size();
        for (int i = 0; i < len; i++) {
            Tripulacion parte = jollyroger.partes.get(i);
            campos[parte.x][parte.y] = true;
        }

        int botinX = random.nextInt(MUNDO_ANCHO);
        int botinY = random.nextInt(MUNDO_ALTO);
        while (true) {
            if (campos[botinX][botinY] == false)
                break;
            botinX += 1;
            if (botinX >= MUNDO_ANCHO) {
                botinX = 0;
                botinY += 1;
                if (botinY >= MUNDO_ALTO) {
                    botinY = 0;
                }
            }
        }
        botin = new Botin(botinX, botinY, random.nextInt(3));
    }

    public void update(float deltaTime) {
        if (finalJuego)

            return;

        tiempoTick += deltaTime;

        while (tiempoTick > tick) {
            tiempoTick -= tick;
            jollyroger.avance();
            if (jollyroger.comprobarChoque()) {
                finalJuego = true;
                return;
            }

            Tripulacion head = jollyroger.partes.get(0);
            if (head.x == botin.x && head.y == botin.y) {
                puntuacion += INCREMENTO_PUNTUACION;
                jollyroger.abordaje();
                if (jollyroger.partes.size() == MUNDO_ANCHO * MUNDO_ALTO) {
                    finalJuego = true;
                    return;
                } else {
                    colocarBotin();
                }

                if (puntuacion % 100 == 0 && tick - TICK_DECREMENTO > 0) {
                    tick -= TICK_DECREMENTO;
                }
            }
        }
    }
}

