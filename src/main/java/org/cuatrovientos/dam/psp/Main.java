package org.cuatrovientos.dam.psp;

public class Main {
    public static void main(String[] args) {

        int numTamagotchis = 3;

        Tamagotchi[] listaTamagotchis = new Tamagotchi[numTamagotchis];
        Thread[] listaHilos = new Thread[numTamagotchis];

        for (int i = 0; i < numTamagotchis; i++) {
            listaTamagotchis[i] = new Tamagotchi("Tamagotchi " + (i + 1));

            listaHilos[i] = new Thread(listaTamagotchis[i]);
            listaHilos[i].start();
        }
    }
}
