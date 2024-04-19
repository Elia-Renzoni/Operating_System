package gestione.azienda;

import java.util.Random;

public class Venditore extends Thread {
	private Gestionale gestionale;
	private Random rnd;
	
	public Venditore(Gestionale gestionale, int i) {
		super("Venditore" + i);
		this.gestionale = gestionale;
		this.rnd = new Random();
	}
	
	public void run() {
		int dormita = 100 + this.rnd.nextInt(251);
		int clientiTrovati = 0;
		while (clientiTrovati != 12) {
			int esito = this.gestionale.trovaMax(this);
			if (esito != -1) {
				clientiTrovati++;
				System.out.println("Il " + super.getName() + " ha trovato un cliente !");
			} else {
				System.out.println("Il " + super.getName() + " non ha trovato clienti !");
			}
			try {
				Thread.sleep(dormita);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Il " + super.getName() + " ha trovato 12 clienti e quindi ora termina !!");
	}
	
}
