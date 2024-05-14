package ponte;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Ponte {
	private int gialliContatore;
	private int verdiContatore;
	private ReentrantLock mutex;
	private Semaphore ponte;
	private Semaphore nuovoBabbuino;
	private Condition gialli;	// meglio i Semafori
	private Condition verdi;	// meglio i Semafori
	
	public Ponte() {
		this.gialliContatore = 0;
		this.verdiContatore = 0;
		this.mutex = new ReentrantLock();
		this.nuovoBabbuino = new Semaphore(0);
		this.ponte = new Semaphore(3, true);
		this.gialli = this.mutex.newCondition();
		this.verdi = this.mutex.newCondition();
	}
	
	public void richiediAccesso(Babbuino b) {
		System.out.println("Il Thread " + b.getName() + " fa richiesta d'accesso al ponte !");
	
		this.mutex.lock();
		try {
			if (b.getClan().equals("Gialli")) {
				this.gialliContatore++;
				System.out.println("Gialli : " + this.gialliContatore);
			} else {
				System.out.println("Verdi : " + this.verdiContatore);
				this.verdiContatore++;
			}
		} finally {
			this.mutex.unlock();
		}
		
		try {
			this.ponte.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.mutex.lock();
		try {
			if (b.getClan().equals("Gialli")) {
				try {
					this.nuovoBabbuino.release();
					this.gialli.await();
				} catch (InterruptedException e) {
					System.out.println("****");
				}
			} else {
				try {
					this.nuovoBabbuino.release();
					this.verdi.await();
				} catch (InterruptedException e) {
					System.out.println("*****");
				}
			}
		} finally {
			this.mutex.unlock();
		}

	}
	
	public void esci(Babbuino b) {
		System.out.println("Il Thread " + b.getName() + " esce dal ponte !!");
		
		this.mutex.lock();
		try {
			if (b.getClan().equals("Gialli")) {
				this.gialliContatore--;
			} else {
				this.verdiContatore--;
			}
		} finally {
			this.mutex.unlock();
		}
	}
	
	public void gestisciAttraversamento(Orango o) throws InterruptedException {
		this.nuovoBabbuino.acquire();
		
		System.out.println("l'Orango si sveglia !!");
		
		this.mutex.lock(); 
		try {
			if (this.gialliContatore > this.verdiContatore) {
				System.out.println("I Gialli sono in maggioranza, quindi l'orango gli risveglia per primi !!");
				this.gialli.signalAll();
				System.out.println("Ora libero i verdi !!");
				this.verdi.signalAll();
			} else if (this.gialliContatore < this.verdiContatore) {
				System.out.println("I Verdi sono in maggioranza, quindi l'orango gli risvelgia per primi !!");
				this.verdi.signalAll();
				System.out.println("Ora libero i gialli !!");
				this.gialli.signalAll();
			} else {
				System.out.println("I verdi e i gialli sono uguali, quindi l'orango risveglia prima i gialli");
				this.gialli.signalAll();
				System.out.println("Ora libero i verdi !!");
				this.verdi.signalAll();
			}
		} finally {
			this.mutex.unlock();
		}
		
		this.ponte.release();
	}
}
