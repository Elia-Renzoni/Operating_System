package Concurrent_Programming.Thread.Java_Thread.DiscoIO.scheduling;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Processo extends Thread {
    private Disco disco;
    private Random rand;
    private Semaphore mySem;
    private int richiestaCilindro;

    public Processo(final Disco disco, final int i) {
        super("Processo " + i);
        this.disco = disco;
        this.rand = new Random();
        this.mySem = new Semaphore(0);
    }

    public int getRichiestaCilindro() {
        return this.richiestaCilindro;
    }

    public void sospendimi() {
        try {
            this.mySem.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void liberami() {
        this.mySem.release();
    }

    public void run() {
        int contatore = 0;

        while (contatore != 4) {
            this.richiestaCilindro = this.rand.nextInt(1001);
            this.disco.richiestaCilidro(this);
            try {
                Thread.sleep(this.rand.nextInt(101) + 100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            contatore++;
        }
        System.out.println("Il Thread " + super.getName() + " termina!!");
    }    
}
