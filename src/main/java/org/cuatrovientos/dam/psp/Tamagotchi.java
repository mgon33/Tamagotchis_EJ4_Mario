package org.cuatrovientos.dam.psp;

public class Tamagotchi implements Runnable{
    private static final long INTERVALO_SUCIEDAD = 3000;
    private static final long TIEMPO_VIDA = 15000;
    private static final long TICK = 100;

    private final String nombre;
    private final long tiempoComer;
    private int suciedad = 0;
    private boolean vivo = true;
    private String accion;

    public Tamagotchi(String nombre, long tiempoComer){
        this.nombre = nombre;
        this.tiempoComer = tiempoComer;
    }

    @Override
    public void run() {
        System.out.println("¡El tamagotchi " + nombre + " ha nacido!(come en " + tiempoComer + " ms)");
        long horaNacimiento = System.currentTimeMillis();
        long ultimaSuciedad = horaNacimiento;

        while (vivo){
            try {
                Thread.sleep(TICK);

                if ((System.currentTimeMillis() - ultimaSuciedad) >= INTERVALO_SUCIEDAD){
                    suciedad++;
                    System.out.println(nombre + " suciedad " + suciedad);
                    ultimaSuciedad = System.currentTimeMillis();

                    if (suciedad == 5){
                        System.out.println(nombre + ": estoy muy sucio...");
                    } else if (suciedad == 10) {
                        System.out.println(nombre + ": muerto de suciedad...");
                        vivo = false;
                    }
                }

                if ((System.currentTimeMillis() - horaNacimiento) >= TIEMPO_VIDA){
                    System.out.println(nombre + ": muerto de viejo DEP");
                    vivo = false;
                }

                if ("comer".equals(accion) && vivo){
                    System.out.println(nombre + " empieza a comer...");
                    Thread.sleep(tiempoComer);
                    System.out.println(nombre + " ha terminado de comer...");
                    accion = null;
                }

            } catch (InterruptedException e){
                System.out.println(nombre + " ha sido interrumpido");
            }
        }
        System.out.println("La vida de " + nombre + " ha terminado. DEP");
    }

    public synchronized void comer() {
        if (accion != null) {
            System.out.println(nombre + " está ocupado (" + accion + "), no puede comer ahora");
        } else {
            accion = "comer";
            System.out.println("El cuidador le da de comer a " + nombre);
        }
    }
}
