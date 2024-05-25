package Concurrent_Programming.Thread.Java_Thread.ImbarcoPasseggeri.politica;

import java.util.Random;

public class Elefante extends Thread {
    private PuntoImbarco punto;
    private Random rand;

    public Elefante(final PuntoImbarco punto, final int i) {
        super("Elefante " + i);
        this.punto = punto;
        this.rand = new Random();
    }

    public void run() {
        boolean isAlive = true;
        while (isAlive) {
           try {
                this.punto.caricaPasseggeri(this); 
                Thread.sleep(500);
                this.punto.scaricaPasseggeri(this);
                Thread.sleep(200);
           } catch (InterruptedException ex) {
                isAlive = false;
                System.out.println("INTERRUPT RICEVUTO!!");
           }
        }
        System.out.println("Il Thread " + super.getName() + " termina!!");
    }
}
