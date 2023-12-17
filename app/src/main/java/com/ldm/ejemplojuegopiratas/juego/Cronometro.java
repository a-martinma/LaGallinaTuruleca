package com.ldm.ejemplojuegopiratas.juego;

import com.ldm.ejemplojuegopiratas.Graficos;

public class Cronometro {

    public Cronometro() {
    }

    public void drawText(Graficos g, long tiempoActual) {

        long segundos = (tiempoActual / 1000);
        long milisegundos = tiempoActual % 1000;

        //Se guarda el tiempo recibido en una cadena separando segundos y milisegundos por ":"
        String timeString = String.format("%02d:%03d", segundos, milisegundos);

        int len = timeString.length();

        //Posicion en la pantalla donde dibujar el cronómetro
        int x = 130;
        int y = 438;

        for (int i = 0; i < len; i++) {
            char numChar = timeString.charAt(i);

            //Aqui se crea un espacio para separar segundos de milisegundos
            if (numChar == ':') {
                x += 10;
                continue; //Salta a la siguiente iteración
            }

            int srcX = Character.getNumericValue(numChar) * 20; //Se obtiene el valor numérico del char y se calcula la posicion de ese número en el asset
            int srcWidth = 20; //Ancho de cada número

            g.drawPixmap(Assets.numeros, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth; //Se dibuja el siguiente número justo a la derecha
        }
    }
}
