package aereoporto;

import java.util.Random;

public class Microsoft extends Thread {
	private Aereoporto areoporto;
	private Random rnd;
	private int imbarchiOttenuti;
	
	public Microsoft(final int i, final Aereoporto a) {
		super("Microsoft " + i);
		this.imbarchiOttenuti = 0;
		this.areoporto = a;
		this.rnd = new Random();
	}
	
	public void run() {
		int dormita = 100 + this.rnd.nextInt(400);
		try {
			while (this.imbarchiOttenuti < 4) {
				Thread.sleep(dormita);
				this.areoporto.richiediImbarco(this);
				System.out.println("Il Thread " + super.getName() + " ha ottenuto un imbarco !");
				this.imbarchiOttenuti++;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Il Thread " + super.getName() + " ha raggiunto il massimo di imbarchi");
	}
}
