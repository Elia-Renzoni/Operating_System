package expo;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Varco {
	private Condition normali;
	private Condition disabili;
	private ReentrantLock mutex;
	private Semaphore nuovoVisitatore;
	private int counterDisabili;
	private int counterNormali;
	
	public Varco() {
		this.mutex = new ReentrantLock();
		this.nuovoVisitatore = new Semaphore(0);
		this.normali = this.mutex.newCondition();
		this.disabili = this.mutex.newCondition();
	}
	
	public void richiediAccesso(Visitatore v) {	
		System.out.println("Il Thread " + v.getName() + " si mette in coda");
		this.mutex.lock();
		try {
			if (v.getTipo().equals("disabile")) {
				try {
					this.counterDisabili++;
					this.nuovoVisitatore.release();
					System.out.println("Il Thread " + v.getName() + " risveglia un controllore");
					this.disabili.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			} else {
				try {
					this.counterNormali++;
					this.nuovoVisitatore.release();
					System.out.println("Il Thread " + v.getName() + " risveglia un controllore");
					this.normali.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			this.mutex.unlock();
		}
	}
	
	public void controllaVisitatore(Controllore c) throws InterruptedException {
		this.nuovoVisitatore.acquire();
		
		System.out.println("Il Thread " + c.getName() + " si sveglia !!");
		
		this.mutex.lock();
		try {
			if (this.counterDisabili > 0) {
				System.out.println("Il Thread " + c.getName() + " controlla un disabile !!");
				this.counterDisabili--;
				this.disabili.signal();
			} else {
				System.out.println("Il Thread " + c.getName() + " controlla uno normale !!");
				this.counterNormali--;
				this.normali.signal();
			}
		} finally {
			this.mutex.unlock();
		}
	}
}
