package aereoporto;

import java.util.Random;

public class Hacker extends Thread {
	private Aereoporto aereoporto;
	private Random rnd;
	private int imbarchiOttenuti;
	
	public Hacker(final int i, final Aereoporto a) {
		super("Hacker " + i);
		this.imbarchiOttenuti = 0;
		this.aereoporto = a;
		this.rnd = new Random();
	}
	
	public void run() {
		int dormita = 100 + this.rnd.nextInt(400);
		try {
			while (this.imbarchiOttenuti < 4) {
				Thread.sleep(dormita);
				this.aereoporto.richiediImbarco(this);
				System.out.println("Il Thread " + super.getName() + " ha ottenuto un imbarco ");
				this.imbarchiOttenuti++;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Il Thread " + super.getName() + " ha raggiunto il massimo di imbarchi");
	}
}
