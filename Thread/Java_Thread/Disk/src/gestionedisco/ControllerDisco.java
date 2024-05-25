package gestionedisco;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ControllerDisco {
    private int primoDisco;
    private int secondoDisco;
    private List<Processi> coda;
    private ReentrantLock mutex;
    private Semaphore nuovaRichiesta;
    private Condition x;

    public ControllerDisco() {
        this.coda = new LinkedList<>();
        this.mutex = new ReentrantLock();
        this.nuovaRichiesta = new Semaphore(0);
        this.x = this.mutex.newCondition();
    }

    public void richiestaCilindro(Processi p) {
        System.out.println("Il Thread " + p.getName() + " si mette in coda!");
        this.mutex.lock();
        try {
            this.coda.add(p);
        } finally {
            this.mutex.unlock();
        } 

        this.nuovaRichiesta.release();
        p.sospendimi();
    }

    public void serviRichiesta(GestoreDisco g) throws InterruptedException {
        this.nuovaRichiesta.acquire();
        System.out.println("Il Gestore " + g.getName() + " si risveglia!!");

        this.mutex.lock();
        try {
            Processi best = this.getBest();
            // se la richiesta è giusta.
            // allora evadi, altrimenti no.
        
        } finally {
            this.mutex.unlock();
        }
    }

    private Processi getBest() {
        return this.coda.stream()
            .min(Comparator.comparing(Processi::richiesta))
            .get();
    }
    
}
