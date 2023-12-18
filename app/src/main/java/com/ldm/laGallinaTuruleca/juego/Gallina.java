package com.ldm.laGallinaTuruleca.juego;

import java.util.ArrayList;
import java.util.List;

public class Gallina {
    public static final int ARRIBA = 0;
    public static final int IZQUIERDA= 1;
    public static final int ABAJO = 2;
    public static final int DERECHA = 3;

    public List<Pollitos> partes = new ArrayList<Pollitos>();
    public int direccion;

    public Gallina() {
        direccion = ARRIBA;
        partes.add(new Pollitos(5, 6));
        partes.add(new Pollitos(5, 7));
        partes.add(new Pollitos(5, 8));
    }

    public void girarIzquierda() {
        direccion += 1;
        if(direccion > DERECHA)
            direccion = ARRIBA;
    }

    public void girarDerecha() {
        direccion -= 1;
        if(direccion < ARRIBA)
            direccion = DERECHA;
    }

    public void comer() {
        Pollitos end = partes.get(partes.size()-1);
        partes.add(new Pollitos(end.x, end.y));
    }

    public void avance() {
        Pollitos pollito = partes.get(0);

        int len = partes.size() - 1;
        for(int i = len; i > 0; i--) {
            Pollitos antes = partes.get(i-1);
            Pollitos parte = partes.get(i);
            parte.x = antes.x;
            parte.y = antes.y;
        }

        if(direccion == ARRIBA)
            pollito.y -= 1;
        if(direccion == IZQUIERDA)
            pollito.x -= 1;
        if(direccion == ABAJO)
            pollito.y += 1;
        if(direccion == DERECHA)
            pollito.x += 1;

        if(pollito.x < 0)
            pollito.x = 9;
        if(pollito.x > 9)
            pollito.x = 0;
        if(pollito.y < 0)
            pollito.y = 12;
        if(pollito.y > 12)
            pollito.y = 0;
    }

    public boolean comprobarChoque() {
        int len = partes.size();
        Pollitos pollito = partes.get(0);
        for(int i = 1; i < len; i++) {
            Pollitos parte = partes.get(i);
            if(parte.x == pollito.x && parte.y == pollito.y)
                return true;
        }

        if ((pollito.x == 3 && pollito.y == 3) || (pollito.x == 6 && pollito.y == 9))
            return true;

        return false;
    }
}

