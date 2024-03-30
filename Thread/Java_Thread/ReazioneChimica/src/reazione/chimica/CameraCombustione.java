package reazione.chimica;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class CameraCombustione {
	private int numeroElementiOssigeno;
	private int numeroElementiMetano;
	private Semaphore ossigeno;
	private Semaphore metano;
	private ReentrantLock mutex;
	
	public CameraCombustione() {
		this.ossigeno = new Semaphore(0, true);	// FIFO
		this.metano = new Semaphore(0, true);	// FIFO
		this.mutex = new ReentrantLock();
	}
	
	public void reagisci(final Thread t) {
		try {
			this.mutex.lock();
			System.out.println("Il Thread " + t.getName() + " è entrato nella camera di combustione !");
			
			if (t instanceof Ossigeno)
				this.numeroElementiOssigeno++;
			else if (t instanceof Metano)
				this.numeroElementiMetano++;
			
			// barriera
			if (this.numeroElementiOssigeno >= 2 && this.numeroElementiMetano >= 1) {
				System.out.println("Il Thread " + t.getName() + " ha creato la soluzione !");
				this.ossigeno.release(2);
				this.metano.release();
				this.numeroElementiOssigeno -= 2;
				this.numeroElementiMetano--;
			}
		} finally {
			this.mutex.unlock();
		}
		
		try {
			if (t instanceof Ossigeno) 
				this.ossigeno.acquire();
			else if (t instanceof Metano)
				this.metano.acquire();
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
	}
}
