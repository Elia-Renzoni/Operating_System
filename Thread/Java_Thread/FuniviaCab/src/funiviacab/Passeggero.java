package funiviacab;

import java.util.Random;

public class Passeggero extends Thread {
	private Funivia funivia;
	private Random rand;
	
	public Passeggero(final Funivia funivia, final int i) {
		super("Passeggero " + i);
		this.funivia = funivia;
		this.rand = new Random();
	}
	
	public void run() {
		try {
			Thread.sleep(this.rand.nextInt(2501));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.funivia.richiediImbarco(this);
		this.funivia.richiediSbarco(this);
		System.out.println("Il Thread " + super.getName() + " termina!!");
	}
}
