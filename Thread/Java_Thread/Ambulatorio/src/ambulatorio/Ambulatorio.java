package ambulatorio;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Ambulatorio {
	private int numeroPazienti;
	private ReentrantLock mutex;
	private Semaphore pazienti;
	private Semaphore informatori;
	
	public Ambulatorio() {
		this.numeroPazienti = 0;
		this.mutex = new ReentrantLock();
		this.pazienti = new Semaphore(1, true);
		this.informatori = new Semaphore(1, true);
	}
	
	public void mettitiInCoda(Persona p) {
		System.out.println("Il Thread " + p.getName() + " cerca di ottenere la prestazione!!");
		if (p.getTipo().equals("Paziente")) {
			try {
				this.pazienti.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.informatori.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.mutex.lock();
		try {
			if (p.getTipo().equals("Paziente")) {
				this.numeroPazienti++;
			}
		} finally {
			this.mutex.unlock();
		}
		
		System.out.println("Il Thread " + p.getName() + " entra per la prestazione!!!");
	}
	
	public void ottieniPrestazioni(Persona p) {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Terminata la prestazione per il Thread " + p.getName());
	}
	
	public void esci(Persona p) {
		this.mutex.lock();
		try {
			if (this.numeroPazienti >= 3) {
				System.out.println("Il Prossimo ad essere servito sarà un informatore!!");
				this.informatori.release();
				this.numeroPazienti = 0;
			} else {
				System.out.println("Il Prossimo ad essere servito sarà un paziente!!");
				this.pazienti.release();
			}
		} finally {
			this.mutex.unlock();
		}
	}
}
