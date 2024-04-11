package aereoporto;

import java.util.Random;

public class Navetta extends Thread {
	private Aereoporto aereoporto;
	private Random rnd;
	
	public Navetta(final String name, final Aereoporto a) {
		super(name);
		this.aereoporto = a;
		this.rnd = new Random();
	}
	
	public void run() {
		boolean loop = true;
		int tempoArrivo = 25;
		while (loop) {
			try {
				this.aereoporto.caricaPersone();
				Thread.sleep(tempoArrivo);
			} catch (InterruptedException e) {
				loop = false;
				e.printStackTrace();
			}
		}
		
	}
}
