package com.ldm.laGallinaTuruleca.juego;

import java.util.Random;

public class Mundo {
    static final int MUNDO_ANCHO = 10;
    static final int MUNDO_ALTO = 13;
    static final int INCREMENTO_PUNTUACION = 10;
    static final float TICK_INICIAL = 0.5f;
    static final float TICK_DECREMENTO = 0.05f;

    public Gallina gallina;
    public Comida comida;
    public boolean finalJuego = false;
    public int puntuacion = 0;
    boolean campos[][] = new boolean[MUNDO_ANCHO][MUNDO_ALTO];
    Random random = new Random();
    float tiempoTick = 0;
    static float tick = TICK_INICIAL;

    public Mundo() {
        gallina = new Gallina();
        colocarComida();
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

    private void colocarComida() {
        for (int x = 0; x < MUNDO_ANCHO; x++) {
            for (int y = 0; y < MUNDO_ALTO; y++) {
                campos[x][y] = false;
            }
        }

        campos[3][3] = true;
        campos[6][9] = true;

        int len = gallina.partes.size();
        for (int i = 0; i < len; i++) {
            Pollitos parte = gallina.partes.get(i);
            campos[parte.x][parte.y] = true;
        }

        int comidaX = random.nextInt(MUNDO_ANCHO);
        int comidaY = random.nextInt(MUNDO_ALTO);
        while (true) {
            if (campos[comidaX][comidaY] == false)
                break;
            comidaX += 1;
            if (comidaX >= MUNDO_ANCHO) {
                comidaX = 0;
                comidaY += 1;
                if (comidaY >= MUNDO_ALTO) {
                    comidaY = 0;
                }
            }
        }
        comida = new Comida(comidaX, comidaY, random.nextInt(3));
    }

    public void update(float deltaTime) {
        if (finalJuego)
            return;

        tiempoTick += deltaTime;

        while (tiempoTick > tick) {
            tiempoTick -= tick;
            gallina.avance();
            if (gallina.comprobarChoque()) {
                finalJuego = true;
                return;
            }

            Pollitos head = gallina.partes.get(0);
            if (head.x == comida.x && head.y == comida.y) {
                puntuacion += INCREMENTO_PUNTUACION;
                gallina.comer();
                if (gallina.partes.size() == MUNDO_ANCHO * MUNDO_ALTO) {
                    finalJuego = true;
                    return;
                } else {
                    colocarComida();
                }

                if (puntuacion % 100 == 0 && tick - TICK_DECREMENTO > 0) {
                    tick -= TICK_DECREMENTO;
                }
            }
        }
    }
}

