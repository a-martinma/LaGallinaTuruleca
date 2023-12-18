package com.ldm.laGallinaTuruleca.juego;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.ldm.laGallinaTuruleca.FileIO;

public class Configuraciones {
    public static boolean sonidoHabilitado = true;
    public static LinkedList<Integer> listaPuntuaciones = new LinkedList<>();
    public static LinkedList<Float> listaTiempos = new LinkedList<>();


    public static void cargar(FileIO files) {
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(
                    files.leerArchivo(".laGallina")));
            sonidoHabilitado = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                listaPuntuaciones.add(Integer.parseInt(in.readLine()));
                listaTiempos.add(Float.parseFloat(in.readLine()));
            }

        } catch (IOException e) {
            // :( Está bien aquí debería ir algo
        } catch (NumberFormatException e) {
            // :/ Nadie es perfecto
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    public static void crearListas(){
        if(listaPuntuaciones.size() == 0 && listaTiempos.size() == 0){
            LinkedList<Integer> puntos = new LinkedList<>(Arrays.asList(70, 60, 40, 20, 10));
            LinkedList<Float> tiempos = new LinkedList<>(Arrays.asList(52.24f, 46.78f, 31.12f, 32.39f, 08.92f));

            listaTiempos.addAll(tiempos);
            listaPuntuaciones.addAll(puntos);

        }
    }

    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    files.escribirArchivo(".laGallina")));
            out.write(Boolean.toString(sonidoHabilitado));
            out.write("\n");
            for (int i = 0; i < 5; i++) {
                out.write(Integer.toString(listaPuntuaciones.get(i)));
                out.write("\n");
                out.write(Float.toString(listaTiempos.get(i)));
                out.write("\n");
            }

        } catch (IOException e) {
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }
    }

    public static void addScore(int score, float time) {
        listaPuntuaciones.add(score);
        listaTiempos.add(time);
        ordenarRanking();
    }

    public static void ordenarRanking() {

        LinkedList<InfoPartida> listaInfo = new LinkedList<>();
        for (int i = 0; i < listaPuntuaciones.size(); i++) {
            listaInfo.add(new InfoPartida(listaPuntuaciones.get(i), listaTiempos.get(i)));
        }

        Collections.sort(listaInfo, new Comparator<InfoPartida>() {
            @Override
            public int compare(InfoPartida persona1, InfoPartida persona2) {
                // Ordenar por puntuación descendente
                int comparacionPuntuacion = Integer.compare(persona2.getPuntuacion(), persona1.getPuntuacion());
                if (comparacionPuntuacion != 0) {
                    return comparacionPuntuacion;
                }

                // En caso de empate, ordenar por tiempo ascendente
                return Float.compare(persona1.getTiempo(), persona2.getTiempo());
            }
        });

        //Se actualizan las listas quedando ordenadas
        for (int i = 0; i < listaInfo.size(); i++) {
            listaPuntuaciones.set(i, listaInfo.get(i).getPuntuacion());
            listaTiempos.set(i, listaInfo.get(i).getTiempo());
        }
    }

    private static class InfoPartida {
        private int puntuacion;
        private float tiempo;

        public InfoPartida(int puntuacion, float tiempo) {
            this.puntuacion = puntuacion;
            this.tiempo = tiempo;
        }

        public int getPuntuacion() {
            return puntuacion;
        }

        public float getTiempo() {
            return tiempo;
        }
    }

}