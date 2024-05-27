package smp.scheduling;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;

public class Cpu extends Thread {
	private CodaScheduling coda;
	private Condition cond;
	
	public Cpu(final CodaScheduling coda, final int i) {
		super("CPU " + i);
		this.coda = coda;
	}
	
	public void sospendimi(Condition cond) {
		try {
			this.cond = cond;
			this.cond.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void liberami() {
		this.cond.signal();
	}
	
	public void run() {
		boolean isAlive = true;
		
		while (isAlive) {
			try {
				this.coda.pull(this);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				isAlive = false;
				System.out.println("INTERRRUPT RICEVUTO!!");
			}
		}
		System.out.println("Il Thread "  + super.getName() + " termina!!");
	}
}
