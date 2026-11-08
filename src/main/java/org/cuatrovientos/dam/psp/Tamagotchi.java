package org.cuatrovientos.dam.psp;

public class Tamagotchi implements Runnable{
    private static final long INTERVALO_SUCIEDAD = 20000;
    private static final long TIEMPO_VIDA = 300000;
    private static final long TICK = 100;
    private static final long TIEMPO_BANO = 5000;

    private final String nombre;
    private final long tiempoComer;
    private int suciedad = 0;
    private boolean vivo = true;
    private String accion;
    private int resultadoSuma;

    public Tamagotchi(String nombre, long tiempoComer){
        this.nombre = nombre;
        this.tiempoComer = tiempoComer;
    }

    @Override
    public void run() {
        System.out.println("¡El tamagotchi " + nombre + " ha nacido! (come en " + tiempoComer + " ms)");
        long horaNacimiento = System.currentTimeMillis();
        long ultimaSuciedad = horaNacimiento;

        while (estaVivo()){
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

                if ("limpiar".equals(accion) && vivo){
                    System.out.println(nombre + " empieza a bañarse...");
                    Thread.sleep(TIEMPO_BANO);
                    suciedad = 0;
                    ultimaSuciedad = System.currentTimeMillis();
                    System.out.println(nombre + " está limpio (suciedad 0)");
                    accion = null;
                }

            } catch (InterruptedException e){
                System.out.println(nombre + " ha sido interrumpido");
            }
        }
        System.out.println("La vida de " + nombre + " ha terminado. DEP");
    }

    public synchronized void comer() {
        if (!vivo) {
            System.out.println(nombre + " está muerto, no puede comer");
        } else if (accion != null) {
            System.out.println(nombre + " está ocupado (" + accion + "), no puede comer ahora");
        } else {
            accion = "comer";
            System.out.println("El cuidador le da de comer a " + nombre);
        }
    }

    public synchronized void limpiar() {
        if (!vivo) {
            System.out.println(nombre + " está muerto, no puede bañarse");
        } else if (accion != null) {
            System.out.println(nombre + " está ocupado (" + accion + "), no puede bañarse ahora");
        } else {
            accion = "limpiar";
            System.out.println("El cuidador baña a " + nombre);
        }
    }

    public synchronized void jugar() {
        if (!vivo) {
            System.out.println(nombre + " está muerto, no puede jugar");
        } else if (accion != null) {
            System.out.println(nombre + " está ocupado (" + accion + "), no puede jugar ahora");
        } else {
            accion = "jugar";
            System.out.println("El cuidador juega con " + nombre);
            nuevaSuma();
        }
    }

    private void nuevaSuma() {
        int a = (int) (Math.random() * 8) + 1;
        int b = (int) (Math.random() * (9 - a)) + 1;
        resultadoSuma = a + b;
        System.out.println(nombre + " pregunta: ¿Cuánto es " + a + " + " + b + "?");
    }

    public synchronized void responder(int respuesta) {
        if (!"jugar".equals(accion)) {
            System.out.println(nombre + " no está jugando");
            return;
        }

        if (respuesta == resultadoSuma) {
            System.out.println(nombre + ": ¡Correcto! ¡Qué bien me lo he pasado!");
            accion = null;
        } else {
            System.out.println(nombre + ": ¡Incorrecto! Sigo jugando");
            nuevaSuma();
        }
    }

    public synchronized boolean estaJugando() {
        return vivo && "jugar".equals(accion);
    }

    public synchronized boolean estaVivo() {
        return vivo;
    }

    public synchronized String getEstado() {
        if (!vivo) {
            return nombre + " | MUERTO";
        }
        String haciendo = (accion == null) ? "ocioso" : "haciendo: " + accion;
        return nombre + " | vivo | suciedad " + suciedad + " | " + haciendo;
    }

    public synchronized void matar() {
        if (!vivo) {
            System.out.println(nombre + " ya está muerto");
        } else if (accion != null) {
            System.out.println(nombre + " está ocupado (" + accion + "), no atiende a la destrucción");
        } else {
            vivo = false;
            System.out.println("El cuidador ha matado a " + nombre);
        }
    }
}