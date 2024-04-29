package riparazioni;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Auto extends Thread {
	private Officina officina;
	private Semaphore mySem;
	private boolean isPiantata;
	private Random rnd;
	
	public Auto(final Officina officina, final boolean danno, final int i) {
		super("Auto " + i);
		this.officina = officina;
		this.isPiantata = danno;
		this.mySem = new Semaphore(0);
		this.rnd = new Random();
	}
	
	public void sospendimi() {
		try {
			this.mySem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void risvegliami() {
		this.mySem.release();
	}
	
	public boolean getDanno() {
		return this.isPiantata;
	}
	
	public void run() {
		int attesa = 100 + this.rnd.nextInt(501);
		try {
			Thread.sleep(attesa);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.officina.richiediRiparazione(this);
		try {
			System.out.println(" " + super.getName() + " aspetta che la riparazione finisca");
			Thread.sleep(attesa);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(" " + super.getName() + " termina !");
	}
}
