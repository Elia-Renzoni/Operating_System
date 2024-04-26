package gestione.fabbrica;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Fabbrica {
	private LinkedList<UmpaLumpa> coda;
	private ReentrantLock mutex;
	private Semaphore forni;
	private Semaphore willyWonka;
	
	public Fabbrica() {
		this.coda = new LinkedList<>();
		this.mutex = new ReentrantLock();
		this.forni = new Semaphore(3);
		this.willyWonka = new Semaphore(0);
	}
	
	public void richiediForno(UmpaLumpa ul) {
		System.out.println("Il Thread " + ul.getName() + " ha richiesto un forno !");
		try {
			this.forni.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.mutex.lock();
		try {
			this.coda.add(ul);
			System.out.println("Il Thread " + ul.getName() + " è stato aggiunto in coda !");
		} finally {
			this.mutex.unlock();
		}
		
		System.out.println("Il Thread " + ul.getName() + " risveglia Willy Wonka !");
		this.willyWonka.release();
		ul.sospendimi();
	}
	
	public void liberaForno(UmpaLumpa ul) {
		System.out.println("Il Thread " + ul.getName() + " ha liberato il forno !");
	}
	
	public void gestisciRichieste(WillyWonka w) throws InterruptedException {
		this.willyWonka.acquire();
		System.out.println("Il Thread " + w.getName() + " si è risvegliato !!");
		
		this.mutex.lock();
		try {
			UmpaLumpa best = this.prendiMigliore();
			if (best == null) {
				best = this.coda.getLast();
			}
			this.coda.remove(best);
			System.out.println("Rimosso dalla coda il Thread " + best.getName());
			best.liberami();
		} finally {
			this.mutex.unlock();
		}
		
		this.forni.release();
	}
	
	private UmpaLumpa prendiMigliore() {
		return this.coda.stream()
				.min(Comparator.comparingInt(UmpaLumpa::getSemi))
				.orElse(null);
	}
}
