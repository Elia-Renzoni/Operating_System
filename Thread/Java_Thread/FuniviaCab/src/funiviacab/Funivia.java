package funiviacab;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Funivia {
	private int passeggeri;
	private ReentrantLock mutex;
	private Semaphore codaImbarchi;
	private Condition codaSbarchi;
	
	public Funivia() {
		this.passeggeri = 0;
		this.mutex = new ReentrantLock();
		this.codaImbarchi = new Semaphore(0, true);
		this.codaSbarchi = this.mutex.newCondition();
	}
	
	public void richiediImbarco(Passeggero p) {
		System.out.println("Il Thread " + p.getName() + " attende la cabina!!");
		
		this.mutex.lock();
		try {
			this.passeggeri++;
			System.out.println("Numero Passeggeri: " + this.passeggeri);
		} finally {
			this.mutex.unlock();
		}
		
		try {
			this.codaImbarchi.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Il Thread " + p.getName() + " sale nella cabina!!");
	}
	
	public void richiediSbarco(Passeggero p) {
		System.out.println("Il Thread " + p.getName() + " richiede lo sbarco!!");
		this.mutex.lock();
		try {
			try {
				this.codaSbarchi.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			this.mutex.unlock();
		}
		System.out.println("Il Thread " + p.getName() + " sbarca!!");
	}
	
	public void caricaPasseggeri(Cabina c) {
		System.out.println("Il Thread " + c.getName() + " carica nuovi passeggeri!!");
		this.mutex.lock();
		try {
			int i = 6;
			while ((i > 0) && (this.passeggeri > 0)) {
				i--;
				this.passeggeri--;
				this.codaImbarchi.release();
			}
		} finally {
			this.mutex.unlock();
		}
		System.out.println("Il Thread " + c.getName() + " parte!!");
	}
	
	public void scaricaPasseggeri(Cabina c) {
		System.out.println("Il Thread " + c.getName() + " scarica dei passeggeri!!");
		try {
			this.mutex.lock();
			this.codaSbarchi.signalAll();
		} finally {
			this.mutex.unlock();
		}
	}
}
