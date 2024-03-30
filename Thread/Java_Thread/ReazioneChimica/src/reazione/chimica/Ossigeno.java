package reazione.chimica;

import java.util.Random;

public class Ossigeno extends Thread {
	private Random rnd;
	private CameraCombustione c;
	
	public Ossigeno(final CameraCombustione camera, final String tName) {
		super(tName);
		this.c = camera;
		this.rnd = new Random();
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
