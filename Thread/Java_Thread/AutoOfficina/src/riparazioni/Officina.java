package riparazioni;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Officina {
	private int inPausa;
	private int autoRiparate;
	private LinkedList <Auto> coda;
	private Semaphore nuovaAuto;
	private Semaphore autoDaRiparare;
	private ReentrantLock mutex;
	private Condition pausa;
	
	public Officina() {
		this.coda = new LinkedList<>();
		this.nuovaAuto = new Semaphore(0, true);
		this.autoDaRiparare = new Semaphore(5, true);
		this.mutex = new ReentrantLock();
		this.pausa = this.mutex.newCondition();
	}
	
	public void richiediRiparazione(Auto a) {
		System.out.println("Il Thread " + a.getName() + " richiede una riparazione !");
		try {
			this.autoDaRiparare.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.mutex.lock();
		try {
			System.out.println("Il Thread " + a.getName() + " si è aggiunto in coda !!");
			this.coda.add(a);
		} finally {
			this.mutex.unlock();
		}
		
		System.out.println("Il Thread " + a.getName() + " risveglia un meccanico !");
		this.nuovaAuto.release();
		a.sospendimi();
	}
	
	public void riparaAuto(Meccanico m) throws InterruptedException {
		this.nuovaAuto.acquire();
		
		this.mutex.lock();
		try {
			Auto a = this.getAutoMigliore();
			if (a == null) {
				a = this.coda.getFirst();
			}
			System.out.println("Viene Servito Il Thread " + a.getName() + " con danno " + a.getDanno());
			this.coda.remove(a);
			this.autoRiparate++;
			System.out.println("Auto riparate : " + this.autoRiparate);

			a.risvegliami();
			
		} finally {
			this.mutex.unlock();
		}
				
		Thread.sleep(200);
		
		this.mutex.lock();
		try {
			if (this.autoRiparate >= 6) {
				this.inPausa++;
				if (this.inPausa == 5) {
					System.out.println("Il Thread " + m.getName() + " fa la pausa caffè !!");
					Thread.sleep(500);
					this.pausa.signalAll();
					this.autoRiparate = 0;
					this.inPausa = 0;
				} else {
					System.out.println("Il Thread " + m.getName() + " fa la pausa caffè !!");
					this.pausa.await();
				}
			}
		} finally {
			this.mutex.unlock();
		}
		
		this.autoDaRiparare.release();
	}
	
	public Auto getAutoMigliore() {
		return this.coda.stream()
				.filter(n -> n.getDanno() == true)
				.findAny()
				.orElse(null);
	}
}
