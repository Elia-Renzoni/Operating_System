package cibo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Capanna {
	private int cibo;
	private ReentrantLock mutex;
	private Semaphore nuovoLeone;
	private Condition codaLeoni;
	
	public Capanna() {
		this.cibo = 0;
		this.mutex = new ReentrantLock();
		this.nuovoLeone = new Semaphore(0);
		this.codaLeoni = this.mutex.newCondition();
	}
	
	public void richiediDaMangiare(Leone l) {
		System.out.println("Il Thread " + l.getName() + " richiede del cibo !!!");
		
		this.mutex.lock();
		try {
			while (this.cibo == 0) {
				System.out.println("Il Thread " + l.getName() + " si sospende per assenza di cibo!!");
				this.nuovoLeone.release();
				try {
					this.codaLeoni.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			this.mutex.unlock();
		}
		
		this.mutex.lock();
		try {
			System.out.println("Il Thread " + l.getName() + " mangia una porzione di cibo!!");
			this.cibo--;
		} finally {
			this.mutex.unlock();
		}
	}
	
	public void distribuisciPorzione(Guardiano g, int porzione) throws InterruptedException {
		this.nuovoLeone.acquire();
		
		System.out.println("Il Thread " + g.getName() + " si risveglia!!");
		
		this.mutex.lock();
		try {
			System.out.println("Il Thread " + g.getName() + " aggiunge " + porzione + " porzioni");
			this.cibo = porzione;
			if (this.cibo != 0)  {
				this.codaLeoni.signal();
			}
		} finally {
			this.mutex.unlock();
		}
	}
	
}
