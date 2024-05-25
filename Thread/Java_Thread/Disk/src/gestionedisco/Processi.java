package gestionedisco;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Processi extends Thread {
    private ControllerDisco controller;
    private Random rand;
    private int richiesta;
    private Semaphore mySeM;
    
    public Processi(final ControllerDisco c, final int i) {
        super("Processo" + i); 
        this.controller = c; 
        rand = new Random(); 
        this.mySeM = new Semaphore(0);
    }

    public void sospendimi() {
        try {
            this.mySeM.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void liberami() {
        this.mySeM.release();
    }

    public int richiesta() {
        return this.richiesta;
    }

    public void run() {
        int contatore = 0;
        while (contatore != 4) {
            this.richiesta = this.rand.nextInt(1000);
            this.controller.richiestaCilindro(this);
            contatore++;
        }
        System.out.println("Il Thread  " + super.getName() + " ha terminato la propria esecuzione!!");;
    }
}
