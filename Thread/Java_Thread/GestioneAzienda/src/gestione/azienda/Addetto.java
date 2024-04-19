package gestione.azienda;

import java.util.Random;

public class Addetto extends Thread {
	private Gestionale gestionale;
	private Random rnd;
	
	public Addetto(Gestionale gestionale, int i) {
		super("Addetto " + i);
		this.gestionale = gestionale;
		this.rnd = new Random();
	}
	
	public void run() {
		int dormita = 100 + this.rnd.nextInt(201);
		int budget = 1 + this.rnd.nextInt(999);
		int id = this.rnd.nextInt();
		boolean loop = true;
		
		while (loop) {
			this.gestionale.inserisciCliente(this, new Cliente(budget, id));
			try {
				Thread.sleep(dormita);
			} catch (InterruptedException e) {
				loop = false;
				System.out.println(" "+ super.getName() + " ha ricevuto l'interrupt !!");
			}
		}
		System.out.println("" + super.getName() + " termina !");
	}
}
