package multiprocessore;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class CodaScheduling {
	private LinkedList<Processo> coda;
	private Semaphore processi;	// sarebbe da togliere
	private Semaphore processori;
	private ReentrantLock mutex;
	
	public CodaScheduling() {
		this.coda = new LinkedList<>();
		this.processi = new Semaphore(4, true);
		this.processori = new Semaphore(0, true);
		this.mutex = new ReentrantLock();
	}
	
	public void entraInCoda(int cpuBurst, Processo p) {
		System.out.println("Il Thread " + p.getName() + " con cpu bust " + cpuBurst + " richiede di entrare in coda !");
		
		try {
			this.processi.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.mutex.lock();
		try {
			this.coda.add(p);
			System.out.println("Il Thread " + p.getName() + " con cpu bust " + p.getCPUBurst() + "è entrato in coda !!");
		} finally {
			this.mutex.unlock();
		}
		
		System.out.println("Il Thread " + p.getName() + " risveglia un processore !!");
		this.processori.release();
		p.sospendimi();
	}
	
	public void eseguiProcesso(Processore p) throws InterruptedException {
		this.processori.acquire();
		
		System.out.println("Il Thread " + p.getName() + " è stato liberato !");
		
		this.mutex.lock();
		try {
			Processo migliore = this.prendiMigliore();
			if (migliore == null) {
				migliore = this.coda.getFirst();
			}
			System.out.println("Il Thread " + p.getName() + " sta eseguendo il processo " + migliore.getName() + 
					           " con CPU burst " + migliore.getCPUBurst());
			this.coda.remove(migliore);
			migliore.liberami();
			
			Thread.sleep(migliore.getCPUBurst());
			
		} finally {
			this.mutex.unlock();
		}
		
		this.processi.release();
	}
	
	private Processo prendiMigliore() {
		return this.coda.stream()
				.min(Comparator.comparingInt(Processo::getCPUBurst))
				.orElse(null);
	}
}
