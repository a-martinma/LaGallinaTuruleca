package com.ldm.ejemplojuegopiratas.juego;

import com.ldm.ejemplojuegopiratas.Graficos;

public class TimerAnimation {
    private int x;
    private int screenWidth;


    public TimerAnimation(int x, int screenWidth) {
        this.x = x;
        this.screenWidth = screenWidth;
    }

    public void drawText(Graficos g, long elapsedTime, int screenHeight) {
        long minutes = (elapsedTime / 1000) / 60;
        long seconds = (elapsedTime / 1000) % 60;
        long milliseconds = elapsedTime % 1000;

        String timeString = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);

        int len = timeString.length();
        int x = this.x - 60;
        int y = screenHeight + 388;

        for (int i = 0; i < len; i++) {
            char character = timeString.charAt(i);

            if (character == ':') {
                x += 10;
                continue;
            }

            int srcX = (character - '0') * 20;
            int srcWidth = 20;

            g.drawPixmap(Assets.numeros, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }
}
