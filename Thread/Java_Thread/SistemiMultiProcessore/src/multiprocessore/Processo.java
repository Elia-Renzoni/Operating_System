package multiprocessore;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Processo extends Thread {
	private CodaScheduling coda;
	private Random rnd;
	private Semaphore mySem;
	private int cpuBurst;
		
	public Processo(final CodaScheduling coda, final int i) {
		super("Processo " + i);
		this.coda = coda;
		this.rnd = new Random();
		this.mySem = new Semaphore(0);
	}
	
	public void sospendimi() {
		try {
			this.mySem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void liberami() {
		this.mySem.release();
	}
	
	public int getCPUBurst() {
		return this.cpuBurst;
	}
	
	public void run() {
		int contatore = 0;
		this.cpuBurst = 10 + this.rnd.nextInt(21);
		while (contatore != 5) {
			this.coda.entraInCoda(cpuBurst, this);
			contatore++;
		}
		System.out.println("Il Thread " + super.getName() + " termina !");
	}
}
