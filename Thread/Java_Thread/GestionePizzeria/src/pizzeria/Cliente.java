package pizzeria;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cliente extends Thread {
	private Pizzeria pizzeria;
	private Random rnd;
	private Semaphore mySem;
	private int numeroPizze;
	
	public Cliente(int i, Pizzeria pizzeria) {
		super("Cliente " + i);
		this.pizzeria = pizzeria;
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
	
	public int getNumeroPizze() {
		return this.numeroPizze;
	}
	
	public void run() {
		this.numeroPizze = 1 + this.rnd.nextInt(7);
		this.pizzeria.ordinaPizze(numeroPizze, this);
		System.out.println("Il Thread " + super.getName() + " termina l'esecuzione !");
	}	
}
