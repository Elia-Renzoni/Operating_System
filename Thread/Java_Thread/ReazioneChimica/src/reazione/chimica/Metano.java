package reazione.chimica;

import java.util.Random;

public class Metano extends Thread {
	private Random rnd;
	private CameraCombustione c;
	
	public Metano(final CameraCombustione camera, final String tName) {
		super(tName);
		this.rnd = new Random();
		this.c = camera;
	}
	
	public void run() {
		final int tempoRandom = this.rnd.nextInt(600);
		try {
			Thread.sleep(tempoRandom);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		this.c.reagisci(this);
		System.out.println("Il Thread di nome " + super.getName() + " ha terminato la sua esecuzione !!");
	}
}
