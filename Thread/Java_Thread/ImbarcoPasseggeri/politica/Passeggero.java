package Concurrent_Programming.Thread.Java_Thread.ImbarcoPasseggeri.politica;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Passeggero extends Thread {
    private PuntoImbarco punto;
    private Random rand;
    private Semaphore mySem;

    public Passeggero(final PuntoImbarco punto, final int i) {
        super("Passeggero " + i);
        this.punto = punto;
        this.rand = new Random();
        this.mySem = new Semaphore(0);
    }

    public void sospendimi() {
        try {
            this.mySem.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void risvegliami() {
        this.mySem.release();
    }

    public void run() {
        try {
            Thread.sleep(this.rand.nextInt(2501));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.punto.richiediImbarco(this);
        this.punto.richiediSbarco(this);

        System.out.println("Il Thread " + super.getName() + " termina!!");
    }
}
