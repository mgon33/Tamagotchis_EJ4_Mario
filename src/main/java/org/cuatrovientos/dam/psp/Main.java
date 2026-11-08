package org.cuatrovientos.dam.psp;

import java.util.Scanner;

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

        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println();
            System.out.println("===== MENÚ DEL CUIDADOR =====");
            System.out.println("1. Dar de comer");
            System.out.println("2. Bañar");
            System.out.println("3. Jugar");
            System.out.println("4. Ver estado de todos");
            System.out.println("5. Matar");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();

            if (opcion == 4) {
                for (Tamagotchi t : listaTamagotchis) {
                    System.out.println(t.getEstado());
                }
            } else if (opcion >= 1 && opcion <= 5) {
                System.out.print("¿A qué tamagotchi? (1-" + numTamagotchis + "): ");
                int numero = sc.nextInt();

                if (numero < 1 || numero > numTamagotchis) {
                    System.out.println("Ese tamagotchi no existe");
                    continue;
                }

                Tamagotchi t = listaTamagotchis[numero - 1];

                switch (opcion) {
                    case 1:
                        t.comer();
                        break;
                    case 2:
                        t.limpiar();
                        break;
                    case 3:
                        t.jugar();
                        while (t.estaJugando()) {
                            int respuesta = sc.nextInt();
                            t.responder(respuesta);
                        }
                        break;
                    case 5:
                        t.matar();
                        break;
                }
            } else if (opcion != 0) {
                System.out.println("Opción no válida");
            }
        }

        System.out.println("El cuidador se despide. Esperando a que terminen los tamagotchis...");
        for (Thread h : listaHilos) {
            h.join();
        }
        System.out.println("Fin del programa");
    }
}