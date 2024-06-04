package Concurrent_Programming.Thread.Java_Thread.BinariMontagneRusse.binarimontagne;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MontagneRusse {
    private int numeroPasseggeri;
    private boolean attesa;
    private ReentrantLock mutex;
    private Semaphore codaRichieste;
    private Condition codaRilasci;
    private Condition turnoAttesa;

    public MontagneRusse() {
        this.numeroPasseggeri = 0;
        this.attesa = false;
        this.mutex = new ReentrantLock();
        this.codaRichieste = new Semaphore(0, true);
        this.codaRilasci = this.mutex.newCondition();
        this.turnoAttesa = this.mutex.newCondition();
    }

    public void board(Passeggero p) {
        System.out.println("Il Thread " + p.getName() + " entra nel parco!!");

        this.mutex.lock();
        try {
            this.numeroPasseggeri++;
            if (this.numeroPasseggeri >= 8) {
                this.attesa = true;
                this.turnoAttesa.signal();
            }
        } finally {
            this.mutex.unlock();
        }

        System.out.println("Il Thread " + p.getName() + " si mette in coda!!!");
        try {
            this.codaRichieste.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } 

    public void unboard(Passeggero p) {
        System.out.println("Il Thread " + p.getName() + " fa richiesta di unboard!!");
        this.mutex.lock();
        try {
            try {
                this.codaRilasci.await();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            this.mutex.unlock();
        }

        System.out.println("Il Thread " + p.getName() + " ha ottenuto la richiesta di unboard!!");
    }

    public void load(Autovettura a) {
        this.mutex.lock();
        try {
            while (!(this.attesa)) {
                try {
                    this.turnoAttesa.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } finally {
            this.mutex.unlock();
        }

        System.out.println("Il Thread " + a.getName() + " carica 8 passeggeri!!");
        this.codaRichieste.release(8);
    }

    public void go(Autovettura a) throws InterruptedException {
        System.out.println("Il Thread " + a.getName() + " è in viaggio!!");
        Thread.sleep(300);
    }

    public void unload(Autovettura a) {
        System.out.println("Il Thread " + a.getName() + " fa scendere i passeggeri!!!");
        this.mutex.lock();
        try {
            this.numeroPasseggeri -= 8;
            this.codaRilasci.signalAll();
        } finally {
            this.mutex.unlock();
        }
    }    
} 