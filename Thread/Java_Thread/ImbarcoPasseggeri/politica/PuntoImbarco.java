package Concurrent_Programming.Thread.Java_Thread.ImbarcoPasseggeri.politica;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class PuntoImbarco {
    private int numeroPasseggeri;
    private List<Passeggero> codaRichieste;
    private ReentrantLock mutex;
    private Semaphore nuovoPasseggero;
    private Semaphore richiesteSbarco;

    public PuntoImbarco() {
        this.numeroPasseggeri = 0;
        this.codaRichieste = new LinkedList<>();
        this.mutex = new ReentrantLock();
        this.nuovoPasseggero = new Semaphore(0, true);
        this.richiesteSbarco = new Semaphore(0);
    }

    public void richiediImbarco(Passeggero p) {
        System.out.println("Il Thread " + p.getName() + " si mette in coda!!");
        this.mutex.lock();
        try {
            this.codaRichieste.add(p);
            this.numeroPasseggeri++;
        } finally {
            this.mutex.unlock();
        }

        this.mutex.lock();
        try {
            if (this.numeroPasseggeri == 5) {
                this.nuovoPasseggero.release();
                System.out.println("Il Thread " + p.getName() + " sveglia un elefante!!");
                this.numeroPasseggeri = 0; 
            }
        } finally {
            this.mutex.unlock();
        }
        System.out.println("Il Thread " + p.getName() + " si sospende!!");
        p.sospendimi();
    }

    public void richiediSbarco(Passeggero p) {
        System.out.println("Il Thread " + p.getName() + " attende di essere sbarcato!!!");
        try {
            this.richiesteSbarco.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void caricaPasseggeri(Elefante e) throws InterruptedException {
        this.nuovoPasseggero.acquire();
        System.out.println("Il Thread " + e.getName() + " è pronto a caricare 5 passeggeri!!");

        this.mutex.lock();
        try {
            Passeggero p;
            for (int i = 0; i < 5; i++) {
                p = this.codaRichieste.getLast();
                this.codaRichieste.remove(p);
                p.risvegliami();
                System.out.println("Il Thread " + e.getName() + " carica " + p.getName());
            }
        } finally {
            this.mutex.unlock();
        }
    } 

    public void scaricaPasseggeri(Elefante e) throws InterruptedException {
        this.richiesteSbarco.release(5);
        System.out.println("Il Thread " + e.getName() + " scarica 5 passeggeri!!!");
    }
}
 