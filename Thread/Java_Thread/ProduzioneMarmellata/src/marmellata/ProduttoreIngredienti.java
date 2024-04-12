package marmellata;

import java.util.Random;

public class ProduttoreIngredienti extends Thread {
	private Laboratorio lab;
	private Random rnd;
	private int frutta;
	private int zucchero;
	private int cadenza;
	
	public ProduttoreIngredienti(final Laboratorio lab) {
		super("Produttore");
		this.lab = lab;
		this.rnd = new Random();
		this.frutta = 0;
		this.zucchero = 0;
		this.cadenza = 800;
	}
	
	public void run() {
		boolean loop = true;
		while (loop) {
			this.frutta = 80 + this.rnd.nextInt(80);
			this.zucchero = 2000 + this.rnd.nextInt(2000);
			try {
				this.lab.depositaIngredienti(this.zucchero, this.frutta);
				Thread.sleep(cadenza);
			} catch (InterruptedException e) {
				loop = false;
				e.printStackTrace();
			}
		}
		System.out.println("Il Thread " + super.getName() + " Termina ora !");
	}
}
