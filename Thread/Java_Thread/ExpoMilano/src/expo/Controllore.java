package expo;

import java.util.Random;

public class Controllore extends Thread {
	private Varco varco;
	
	public Controllore(final Varco varco, final int i) {
		super("Controllore " + i);
		this.varco = varco;
	}
	
	public void run() {
		boolean isAlive = true;
		
		while (isAlive) {
			try {
				this.varco.controllaVisitatore(this);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				isAlive = false;
				System.out.println("INTERRUPT RICEVUTO !!");
			}
		}
		
		System.out.println("Il Thread " + super.getName() + " termina !!");
	}
}
