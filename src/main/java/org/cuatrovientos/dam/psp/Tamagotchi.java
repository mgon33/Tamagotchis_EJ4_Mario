package org.cuatrovientos.dam.psp;

public class Tamagotchi implements Runnable{
    private final String nombre;

    public Tamagotchi(String nombre){
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println("¡El tamagotchi " + nombre + " ha nacido!");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e){
            System.out.println("El tamagotchi " + nombre + " ha sido interrumpido");
        }

        System.out.println("La vida de " + nombre + " ha terminado. DEP");
    }
}
