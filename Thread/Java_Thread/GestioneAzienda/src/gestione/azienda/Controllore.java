package gestione.azienda;

import java.util.Random;

public class Controllore extends Thread {
	private Gestionale gestionale;
	private Random rnd;
	
	public Controllore(Gestionale gestionale, int i) {
		super("Controllore " + i);
		this.gestionale = gestionale;
		this.rnd = new Random();
	}
	
	public void run() {
		int dormita = 100 + this.rnd.nextInt(401);
		boolean loop = true;
		
		while (loop) {
			this.gestionale.rimuoviCliente(this);
			try {
				Thread.sleep(dormita);
			} catch (InterruptedException e) {
				loop = false;
				System.out.println("" + super.getName() + " ha ricetuo l'interrupt !!");
			}
		}
		System.out.println("" + super.getName() + " termina !!");
	}
}
