package cibo;

import java.util.Random;

public class Leone extends Thread {
	private Capanna capanna;
	private Random rand;
	
	public Leone(final Capanna capanna, final int i) {
		super("Leone " + i);
		this.capanna = capanna;
		this.rand = new Random();
	}
	
	public void run() {
		int timeToEnter = this.rand.nextInt(300) + 1;
		int contatore = 0;
		
		while (contatore != 10) {
			try {
				Thread.sleep(timeToEnter);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.capanna.richiediDaMangiare(this);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			contatore++;
		}
		System.out.println("Il Thread " + super.getName() + " termina!!");
	}
}
