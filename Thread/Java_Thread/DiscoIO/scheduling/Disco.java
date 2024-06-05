package Concurrent_Programming.Thread.Java_Thread.DiscoIO.scheduling;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Disco {
    private int settore;
    private LinkedList<Processo> codaRichieste;
    private ReentrantLock mutex;
    private Semaphore nuovaRichiesta;

    public Disco() {
        this.settore = 0;
        this.codaRichieste = new LinkedList<>();
        this.mutex = new ReentrantLock();
        this.nuovaRichiesta = new Semaphore(0);
    }

    public void richiestaCilidro(Processo p) {
        System.out.println("Il Thread " + p.getName() + " entra in coda d'attesa!!");
        this.mutex.lock();
        try {
            this.codaRichieste.add(p);
        } finally {
            this.mutex.unlock();
        }

        System.out.println("Il Thread " + p.getName() + " risveglia il gestore!!");
        this.nuovaRichiesta.release();
        System.out.println("Il Thread " + p.getName()  + " si sospende!!");
        p.sospendimi();
    }

    public void serviRichiesta(GestoreDisco g) throws InterruptedException {
        this.nuovaRichiesta.acquire();
        System.out.println("Il Thread " + g.getName() + " si risveglia!!");

        this.mutex.lock();
        try {
            Processo best = getBest();
            this.codaRichieste.remove(best);
            best.liberami();
            System.out.println("Servito il Thread " + best.getName());
            this.settore = best.getRichiestaCilindro();      
            System.out.println("Settore : "  + this.settore);
        } finally {
            this.mutex.unlock();
        }

    }

    private Processo getBest() {
        Processo bestProcess = null;
        int minDistance = 1000;
        for(int i = 0; i < this.codaRichieste.size(); i ++){
             Processo current = this.codaRichieste.get(i);
             int currentDistance = Math.abs(current.getRichiestaCilindro() - this.settore);
             if(currentDistance < minDistance){
                 minDistance = currentDistance;
                 bestProcess = current;
             }
        }
        return bestProcess;
    }
    
}
