package bagno;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bagno {
	private int numeroPosti;
	private int sesso;	// 0 = unisex, 1 = uomo, 2 = donna
	private ReentrantLock mutex;
	private Semaphore richieste;
	private Condition uomo;
	private Condition donna;
	
	public Bagno() {
		this.mutex = new ReentrantLock();
		this.richieste = new Semaphore(3, true);
		this.uomo = this.mutex.newCondition();
		this.donna = this.mutex.newCondition();
	}
	
	public void accessoAlBagno(Thread t) {
		System.out.println("Il Thread " + t.getName() + " vuole accedere al bagno !");
		
		this.mutex.lock();
		try {
			this.numeroPosti++;
			if (this.numeroPosti == 1) {
				if (t instanceof Uomo) {
					System.out.println("Il Thread " + t.getName() + " ha cambiato sesso in uomo!");
					this.sesso = 1;
				} else if (t instanceof Donna) {
					System.out.println("Il Thread " + t.getName() + " ha cambiato sesso in donna !!");
					this.sesso = 2;
				}
			}
			
			if (this.sesso == 1 && t instanceof Donna) {
				try {
					System.out.println("Il Thread " + t.getName() + " si sospende poichè donna invece che uomo !");
					this.donna.await();
					this.numeroPosti--;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (this.sesso == 2 && t instanceof Uomo) {
				try {
					System.out.println("Il Thread " + t.getName() + " si sospende poichè uomo invece che donna !");
					this.uomo.await();
					this.numeroPosti--;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			this.mutex.unlock();
		}
		
		try {
			this.richieste.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void notificaUscita(Thread t) {
		this.mutex.lock();
		try {
			if (this.numeroPosti >= 3) {
				if (t instanceof Uomo) {
					this.sesso = 2;
					System.out.println("Il Thread " + t.getName() + " ha cambiato sesso in donna !");
					this.donna.signalAll();
				} else if (t instanceof Donna) {
					this.sesso = 1;
					System.out.println("Il Thread " + t.getName() + " ha cambiato sesso in uomo !");
					this.uomo.signalAll();
				}
				this.numeroPosti = 0;
			}
		} finally {
			this.mutex.unlock();
		}
		this.richieste.release();
	}
}
