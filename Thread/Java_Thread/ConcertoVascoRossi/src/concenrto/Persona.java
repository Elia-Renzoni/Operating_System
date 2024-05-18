package concenrto;

import java.util.Random;

public class Persona extends Thread {
	private Varco varco;
	private Random rand;
	private boolean zainetto;
	
	public Persona(final Varco varco, final int i, final boolean zainetto) {
		super("Persona " + i + " " + ((zainetto == true)?"Con zainetto":"Senza Zainetto"));
		this.varco = varco;
		this.zainetto = zainetto;
		this.rand = new Random();
	}
	
	public boolean getZainetto() {
		return this.zainetto;
	}
	
	public void run() {
		int timeToArrive = 0;
		if (this.zainetto) {
			timeToArrive = this.rand.nextInt(401) + 200;
		} else {
			timeToArrive = this.rand.nextInt(501) + 100;
		}
		
		try {
			Thread.sleep(timeToArrive);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.varco.richiediAccesso(this);
		
		System.out.println("Il Thread " + super.getName() + " termina!!");
	}
	
}
