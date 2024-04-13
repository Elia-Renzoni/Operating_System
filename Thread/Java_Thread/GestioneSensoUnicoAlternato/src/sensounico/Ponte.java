package sensounico;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Ponte {
	private int numeroAuto;
	private ReentrantLock mutex;
	private Semaphore auto;
	private Condition bloccoDirezione;
	private int direzione;
	
	public Ponte() {
		this.mutex = new ReentrantLock();
		this.auto = new Semaphore(3, true);
		this.bloccoDirezione = this.mutex.newCondition(); 
		this.direzione = 1;
	}
	
	public void entra(int direzioneThread, Auto auto) {
		// controllare col while
		while (direzioneThread != this.direzione) {
			try {
				this.bloccoDirezione.await();
				System.out.println("Il Threa si blocca perchè non ha la direzione giusta !!");
			} catch (InterruptedException e) {
				//e.printStackTrace();
				System.out.println("**");
			}
		}
		
		try {
			this.auto.acquire();
			try {
				this.mutex.lock();
				this.numeroAuto++;
				System.out.println("Numero auto : " + this.numeroAuto);
			} finally {
				this.mutex.unlock();
			}
		} catch (InterruptedException e) {
			//e.printStackTrace();
			System.out.println("*****");
		}
		System.out.println("Il Thread " + auto.getName() + " può entrare nel ponte");
	}
	
	public void esci(Auto auto) {
		try {
			this.mutex.lock();
			System.out.println("Il Thread " + auto.getName() + " esce dal ponte !!");
			this.numeroAuto--;
			System.out.println("Numero auto : " + this.numeroAuto);
		} finally {
			this.mutex.unlock();
		}
		
		this.auto.release();
		
		try {
			this.mutex.lock();
			if (this.numeroAuto == 0) {
				System.out.println("Cambio direzione!!");
				if (this.direzione == 0) {
					this.direzione = 1;
				} else {
					this.direzione = 0;
				}
				this.bloccoDirezione.signalAll();
			}
		} finally {
			this.mutex.unlock();
		}
	}	
}
