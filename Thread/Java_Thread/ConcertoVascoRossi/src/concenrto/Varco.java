package concenrto;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Varco {
	private int personeSenzaZainetto;
	private ReentrantLock mutex;
	private Semaphore nuovaPersona;
	private Semaphore codaPersone;
	private Condition personeConZainetto;
	
	public Varco() {
		this.personeSenzaZainetto = 0;
		this.mutex = new ReentrantLock();
		this.nuovaPersona = new Semaphore(0, true);
		this.codaPersone = new Semaphore(4, true);
		this.personeConZainetto = this.mutex.newCondition();
	}
	
	public void richiediAccesso(Persona p) {
		System.out.println("Il Thread " + p.getName() + " si presenta al varco !!");
		
		this.mutex.lock();
		try {
			if (p.getZainetto()) {
				System.out.println("Il Thread " + p.getName() + " si mette in fondo alla coda!!");
				try {
					this.personeConZainetto.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				this.personeSenzaZainetto++;
			}
		} finally {
			this.mutex.unlock();
		}
		
		System.out.println("Il Thread " + p.getName() + " si mette in coda!!");
		try {
			this.codaPersone.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Il Thread " + p.getName() + " risveglia un controllore!!");
		this.nuovaPersona.release();
	}
	
	public void controllaPersona(Controllore c) throws InterruptedException {
		this.nuovaPersona.acquire();
		System.out.println("Il Controllore " + c.getName() + " si sveglia !!");
		
		this.mutex.lock();
		try {
			this.personeSenzaZainetto--;
			if (this.personeSenzaZainetto <= 0) {
				System.out.println("Ora si possono servire le persone con lo zainetto !!");
				this.personeConZainetto.signalAll();
			}
			System.out.println("Il Thread " + c.getName() + " serve una persona !!");
		} finally {
			this.mutex.unlock();
		}
		
		this.codaPersone.release();
	}
}
