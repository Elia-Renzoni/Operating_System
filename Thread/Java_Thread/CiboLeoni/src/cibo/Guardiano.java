package cibo;

import java.util.Random;

public class Guardiano extends Thread {
	private Capanna capanna;
	private Random rand;
	
	public Guardiano(final Capanna capanna) {
		super("Guardiano!!");
		this.capanna = capanna;
		this.rand = new Random();
	}
	
	public void run() {
		boolean isAlive = true;
		
		while (isAlive) {
			try {
				this.capanna.distribuisciPorzione(this, this.rand.nextInt(5) + 2);
			} catch (InterruptedException e) {
				isAlive = false;
				System.out.println("INTERRUPT RICEVUTO!!!");
			}
		}
		System.out.println("Il Thread " + super.getName() + " termina!!");
	}
}
