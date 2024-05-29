package gestione.voli;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Aereoporto {
	private LinkedList<Aereo> codaAtterraggio;
	private LinkedList<Aereo> codaDecollo;
	private ReentrantLock mutex;
	private Semaphore nuovoAereo;
	private Semaphore capacita;
	
	public Aereoporto() {
		this.codaAtterraggio = new LinkedList<>();
		this.codaDecollo = new LinkedList<>();
		this.mutex = new ReentrantLock();
		this.nuovoAereo = new Semaphore(0);
		this.capacita = new Semaphore(2);
	}
	
	public void richiediServizio(Aereo a) {
		System.out.println("Il Thread " + a.getName() + " con richiesta di " + a.getRichiesta() + " si mette in coda!!");
		
		this.mutex.lock();
		try {
			if (a.getRichiesta().equals("atterraggio")) {
				this.codaAtterraggio.add(a);
			} else {
				this.codaDecollo.add(a);
			}
		} finally {
			this.mutex.unlock();
		}
		
		System.out.println("Il Thread " + a.getName() + " con richiesta di " + a.getRichiesta() + " risveglia il gestore!!");
		this.nuovoAereo.release();
		System.out.println("Il Thread " + a.getName() + " con richiesta di " + a.getRichiesta() +" si sospende!!");
		a.sospendimi();
	}
	
	public void liberaPista(Aereo a) {
		System.out.println("Il Thread " + a.getName() + " con richiesta " + a.getRichiesta() + " libera la pista!!");
		this.capacita.release();
	}
	
	public void gestisciAerei(Gestore g) throws InterruptedException {
		this.nuovoAereo.acquire();
		System.out.println("Il Gestore si risveglia!!");
		
		this.capacita.acquire();
		
		this.mutex.lock();
		try {
			if (!(this.codaAtterraggio.isEmpty())) {
				Aereo best = this.getBest(this.codaAtterraggio);
				this.codaAtterraggio.remove(best);
				best.risvegliami();
				System.out.println("Il Gestore serve " + best.getName() + " con richiesta " + best.getRichiesta());
			} else {
				Aereo best = this.getBest(this.codaDecollo);
				this.codaDecollo.remove(best);
				best.risvegliami();
				System.out.println("Il Gestore serve " + best.getName() + " con richiesta " + best.getRichiesta());
			}
		} finally {
			this.mutex.unlock();
		}
	}
	
	private Aereo getBest(LinkedList<Aereo> coda) {
		return coda.stream()
				.max(Comparator.comparing(Aereo::getPeso))
				.get();
	}

}
