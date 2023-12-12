package com.ldm.ejemplojuegopiratas.juego;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import com.ldm.ejemplojuegopiratas.FileIO;

public class Configuraciones {
    public static boolean sonidoHabilitado = true;
    public static int[] maxPuntuaciones = new int[] {70, 60, 40, 20, 10 };
    public static double[] tiempos = new double[] {00.00, 00.00, 00.00, 100.00, 00.00};

    public static void cargar(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    files.leerArchivo(".piratas")));
            sonidoHabilitado = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                maxPuntuaciones[i] = Integer.parseInt(in.readLine());
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

    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    files.escribirArchivo(".piratas")));
            out.write(Boolean.toString(sonidoHabilitado));
            out.write("\n");
            for (int i = 0; i < 5; i++) {
                out.write(Integer.toString(maxPuntuaciones[i]));
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
        for (int i = 0; i < 5; i++) {
            if (maxPuntuaciones[i] < score) {
                for (int j = 4; j > i; j--) {
                    maxPuntuaciones[j] = maxPuntuaciones[j - 1];
                    tiempos[j] = tiempos[j - 1];
                }
                maxPuntuaciones[i] = score;
                tiempos[i] = time;
                ordenarRanking(maxPuntuaciones,tiempos);
                break;
            }
        }
    }

    public static void ordenarRanking(int[] maxPuntuaciones, double[] tiempos) {
        for (int k = 0; k < 5; k++) {
            int n = 5-k;


            for (int i = 0; i < n - 1; i++) {
                int minIndex = i;
                for (int j = i + 1; j < n; j++) {
                    //Comparar por tiempos en caso de empate
                    if (maxPuntuaciones[j] > maxPuntuaciones[minIndex] || (maxPuntuaciones[j] == maxPuntuaciones[minIndex] && tiempos[j] < tiempos[minIndex])) {
                        minIndex = j;
                    }
                }

                // Intercambiar posiciones en ambos arrays
                int tempPuntuacion = maxPuntuaciones[minIndex];
                maxPuntuaciones[minIndex] = maxPuntuaciones[i];
                maxPuntuaciones[i] = tempPuntuacion;

                double tempTiempo = tiempos[minIndex];
                tiempos[minIndex] = tiempos[i];
                tiempos[i] = tempTiempo;
            }

        }
    }




}