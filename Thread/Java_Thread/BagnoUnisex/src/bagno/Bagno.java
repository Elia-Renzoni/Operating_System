package bagno;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bagno {
	private int contatore;
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
			if (this.sesso == 0) {
				this.sesso = t instanceof Uomo? 1: 2;
			}
			
			int mySesso = t instanceof Uomo? 1 : 2;
			while (this.sesso != mySesso && this.sesso != 0) {
				if (t instanceof Uomo) {
					try {
						this.uomo.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else if (t instanceof Donna) {
					try {
						this.donna.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			if (this.sesso == 0) {
				this.sesso = t instanceof Uomo? 1 : 2;
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
			this.contatore++;
			if (this.contatore >= 3) {
				this.sesso = 0;
				if (t instanceof Uomo) {
					this.donna.signalAll();
				} else if (t instanceof Donna) {
					this.uomo.signalAll();
				}
				this.contatore = 0;
			}
		} finally {
			this.mutex.unlock();
		}
		this.richieste.release();
	}
}
