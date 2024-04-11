package aereoporto;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Aereoporto {
	private int contatoreMicrosoft;
	private int contatoreHacker;
	private Semaphore microsoft;
	private Semaphore hacker;
	private Semaphore navetta;
	private ReentrantLock mutex;
	
	public Aereoporto() {
		this.microsoft = new Semaphore(0, true);
		this.hacker = new Semaphore(0, true);
		this.navetta = new Semaphore(0);
		this.mutex = new ReentrantLock();
	}
	
	public void richiediImbarco(Thread t) {
		try {
			this.mutex.lock();
			if (t instanceof Microsoft) {
				this.contatoreMicrosoft++;
				System.out.println("Il Thread " + t.getName() + " ha richiesto l'embargo");
			} else if (t instanceof Hacker) {
				System.out.println("Il Thread " + t.getName() + " ha richiesto l'embargo");
				this.contatoreHacker++;
			}
		} finally {
			this.mutex.unlock();
		}
		
		// posiziono l'acquire dopo l'unlock perchè andrei per forza in deadlock se facessi al contrario
		if (t instanceof Microsoft) {
			try {
				System.out.println("Il Thread " + t.getName() + " è in coda !");
				this.microsoft.acquire();	// posiziono i thread Microsoft in coda
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (t instanceof Hacker) {
			try {
				System.out.println("Ïl Thread " + t.getName() + " è in coda !");
				this.hacker.acquire();	// posiziono i thread Hacker in coda
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void caricaPersone() throws InterruptedException {
		try {
			this.mutex.lock();
			if (this.contatoreMicrosoft >= 4) { 
				System.out.println("Salgono 4 dipendenti Microsoft nella navetta !");
				this.microsoft.release(4);
				this.contatoreMicrosoft -= 4;
				this.navetta.release();
			} else if (this.contatoreHacker >= 4) {
				System.out.println("Salgono 4 Hacker nella navetta !");
				this.hacker.release(4);
				this.contatoreHacker -= 4;
				this.navetta.release();
			} else if (this.contatoreHacker == 2 && this.contatoreMicrosoft == 2) {
				System.out.println("Salgono 2 Hacker e due dipendenti Microsoft nella navetta !");
				this.microsoft.release(2);
				this.hacker.release(2);
				this.contatoreHacker -= 2;
				this.contatoreMicrosoft -= 2;
				this.navetta.release();
			}
		} finally {
			this.mutex.unlock();
		}
	}
}
