package sensounico;

import java.util.Random;

public class Auto extends Thread {
	private Ponte ponte;
	private Random rnd;
	private int direzione;
	
	public Auto(final Ponte ponte, final int i) {
		super("Auto " + i);
		this.ponte = ponte;
		this.rnd = new Random();
	}
		
	public void run() {
		this.direzione = this.rnd.nextInt(1);
		this.ponte.entra(this.direzione, this);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			//e.printStackTrace();
			System.out.println("****");
		}
		this.ponte.esci(this);
		System.out.println("Il Thread " + super.getName() + " termina !");
	}
}
