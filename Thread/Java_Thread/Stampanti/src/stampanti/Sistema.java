package stampanti;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Sistema {
	private ReentrantLock mutex;
	private Semaphore nuovaStampa;
	private LinkedList<Processo> coda;
	private Semaphore stampanti;
	
	public Sistema() {
		this.mutex = new ReentrantLock();
		this.nuovaStampa = new Semaphore(0);
		this.coda = new LinkedList<>();
		this.stampanti = new Semaphore(4, true);
	}
	
	public void richiediStampante(int numeroPagine, Processo p) {
		try {
			this.mutex.lock();
			System.out.println("Il Processo " + p.getName() + " con pagine " + numeroPagine + " ha chiesto un permesso");
			this.coda.add(p);
			System.out.println("Il Processo " + p.getName() + " è entrato in coda !!");
			this.nuovaStampa.release();
		} finally {
			this.mutex.unlock();
		}
		System.out.println("Il Processo " + p.getName() + " si sospende in coda !");
		p.sospendimi();
	}
	
	public void liberaStampante(Processo p) {
		this.stampanti.release();
		System.out.println("Il Processo " + p.getName() + " ha liberato una stampante");
	}
	
	public void gestisciStampanti() throws InterruptedException {
		Processo processoMigliore = null;
		this.nuovaStampa.acquire();
		System.out.println("Il Gestore delle Stampanti è ora attivo !");
		this.stampanti.acquire();
		try {
			this.mutex.lock();
			processoMigliore = this.getMigliorProcesso();
			System.out.println("Ora Affido la stampante al Processo " + processoMigliore.getName() + 
								" che dovrà stampare " + processoMigliore.getNumeroPagine() + " pagine !");
			processoMigliore.rilasciami();
		} finally {
			this.mutex.unlock();
		}
	}
	
	private Processo getMigliorProcesso() {
		Processo proc = null;
		this.coda.sort(Comparator.comparingInt(Processo::getNumeroPagine));
		proc = this.coda.getFirst();
		this.coda.remove(proc);
		return proc;
	}
}
