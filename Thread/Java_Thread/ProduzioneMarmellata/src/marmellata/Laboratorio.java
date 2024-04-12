package marmellata;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Laboratorio {
	private int numeroVasettiCamion;
	private int numeroFrutta;
	private int numeroZucchero;
	
	private ReentrantLock mutex;
	private Condition noIngredienti;
	
	public Laboratorio() {
		this.numeroVasettiCamion = 0;
		this.numeroFrutta = 0;
		this.numeroZucchero = 0;
		this.mutex = new ReentrantLock();
		this.noIngredienti = mutex.newCondition();
	}
	
	public void richiediIngredienti(Minion t) {
		try {
			this.mutex.lock();
			System.out.println("Il Thread " + t.getName() + " controlla se ci sono ingredienti");
			if (!(this.numeroFrutta >= 8 && this.numeroZucchero >= 200)) {
				try {
					System.out.println("Il Thread " + t.getName() + " si sospende poichè non ci sono ingredienti");
					t.sospendimi();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Il Thread " + t.getName() + " ha ottenuto gli ingredienti richiesti !");
			this.numeroFrutta -= 8;
			this.numeroZucchero -= 200;
		} finally {
			this.mutex.unlock();
		}
	}
	
	public void caricaVasetto(Minion t) {
		try {
			this.mutex.lock();
			this.numeroVasettiCamion++;
			System.out.println("Il Thread " + t.getName() + " ha depositato un vasetto nel camion !");
		} finally {
			this.mutex.unlock();
		}
	}
	
	public void depositaIngredienti(int zucchero, int frutta) throws InterruptedException {
		try {
			this.mutex.lock();
			this.numeroFrutta += frutta;
			this.numeroZucchero += zucchero;
			System.out.println("Il Thread Produttore di Ingredienti ha aggiunto ingredienti !");
			this.noIngredienti.signalAll();
			System.out.println("Il Thread Produttore di Ingredienti ha liberato i minions !");
		} finally {
			this.mutex.unlock();
		}
	}
	
	public int getElementiCamion() {
		try {
			this.mutex.lock();
			return this.numeroVasettiCamion;
		} finally {
			this.mutex.unlock();
		}
	}
	
	public Condition getCondition() {
		return this.noIngredienti;
	}
}
