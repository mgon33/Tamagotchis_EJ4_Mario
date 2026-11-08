package org.cuatrovientos.dam.psp;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int numTamagotchis = 3;

        Tamagotchi[] listaTamagotchis = new Tamagotchi[numTamagotchis];
        Thread[] listaHilos = new Thread[numTamagotchis];

        for (int i = 0; i < numTamagotchis; i++) {
            int tiempoComer = (int) (Math.random() * 4001) + 1000;

            listaTamagotchis[i] = new Tamagotchi("Tamagotchi " + (i + 1), tiempoComer);

            listaHilos[i] = new Thread(listaTamagotchis[i]);
            listaHilos[i].start();
        }

        Thread.sleep(2000);
        for (Tamagotchi t : listaTamagotchis) {
            t.comer();
        }

        Thread.sleep(6000);
        for (Tamagotchi t : listaTamagotchis) {
            t.limpiar();
        }
    }
}