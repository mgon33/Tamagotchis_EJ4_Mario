package org.cuatrovientos.dam.psp;

public class Tamagotchi implements Runnable{
    private static final long INTERVALO_SUCIEDAD = 1000;
    private static final long TIEMPO_VIDA = 15000;

    private final String nombre;
    private int suciedad = 0;
    private boolean vivo = true;

    public Tamagotchi(String nombre){
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println("¡El tamagotchi " + nombre + " ha nacido!");
        long horaNacimiento = System.currentTimeMillis();

        while (vivo){
            try {
                Thread.sleep(INTERVALO_SUCIEDAD);
                suciedad++;
                System.out.println("Tamagotchi " + nombre + " suciedad " + suciedad);

                if (suciedad == 5){
                    System.out.println(nombre + ": estoy muy sucio...");
                } else if (suciedad == 10) {
                    System.out.println(nombre + ": muerto de suciedad...");
                    vivo = false;
                }

                if ((System.currentTimeMillis() - horaNacimiento) >= TIEMPO_VIDA){
                    System.out.println(nombre + ": muerto de viejo DEP");
                    vivo = false;
                }

            } catch (InterruptedException e){
                System.out.println(nombre + " ha sido interrumpido");
            }
        }
        System.out.println("La vida de " + nombre + " ha terminado. DEP");
    }
}
