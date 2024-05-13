package expo;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Visitatore extends Thread {
	private Varco varco;
	private Random rand;
	private String tipoVisitatore;

	public Visitatore(final Varco varco, final int i, final String tipoVisitatore) {
		super("Visitatore " + tipoVisitatore + " " + i);
		this.varco = varco;
		this.rand = new Random();
		this.tipoVisitatore = tipoVisitatore;
	}
		
	public String getTipo() {
		return this.tipoVisitatore;
	}
	
	public void run() {
		int timeToSleep = 0;
		if (!(this.tipoVisitatore.equals("disabile"))) {
			timeToSleep = this.rand.nextInt(501) + 100;
		} else {
			timeToSleep = this.rand.nextInt(1001) + 100;
		}
		
		try {
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.varco.richiediAccesso(this);
		System.out.println("Il Thread " + super.getName() + " termina !!");
	}
}
