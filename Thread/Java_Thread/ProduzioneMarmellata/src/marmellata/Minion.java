package marmellata;

import java.util.Random;

public class Minion extends Thread {
	private Laboratorio lab;
	private Random rnd;
	
	public Minion(final int i, final Laboratorio lab) {
		super("Minion " + i);
		this.lab = lab;
		this.rnd = new Random();
	}
	
	public void sospendimi() throws InterruptedException {
		this.lab.getCondition().await();
	}
	
	public void run() {
		int tempoProduzione = 20 + this.rnd.nextInt(20);
		while (this.lab.getElementiCamion() < 1000) {
			this.lab.richiediIngredienti(this);
			try {
				Thread.sleep(tempoProduzione);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Il Thread " + super.getName() + " ha prodotto un vasetto !");
			this.lab.caricaVasetto(this);
		}
		System.out.println("Il Thread " + super.getName() + " termina perchè il camion ha " + this.lab.getElementiCamion() + " vasetti !");
	}
	
	
}
