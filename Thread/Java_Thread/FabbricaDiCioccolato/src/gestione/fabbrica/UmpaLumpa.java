package gestione.fabbrica;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class UmpaLumpa extends Thread {
	private Fabbrica fabbrica;
	private Random rnd;
	private Semaphore mySem;
	private int semi;
	
	public UmpaLumpa(final Fabbrica fabbrica, final int i) {
		super("Umpa Lumpa " + i);
		this.fabbrica = fabbrica;
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
	
	public void run() {
		this.semi = 10 + this.rnd.nextInt(11);
		this.fabbrica.richiediForno(this);
		try {
			Thread.sleep(semi);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.fabbrica.liberaForno(this);
		System.out.println("Il Thread " + super.getName() + " termina !!");
	}
	
	public int getSemi() {
		return this.semi;
	}
}
