package gestione.fabbrica;

public class WillyWonka extends Thread {
	private Fabbrica fabbrica;
	
	public WillyWonka(final Fabbrica fabbrica) {
		super("Willy Wonka");
		this.fabbrica = fabbrica;
	}
	
	public void run() {
		boolean ciclo = true;
		while (ciclo) {
			try {
				this.fabbrica.gestisciRichieste(this);
			} catch (InterruptedException e) {
				System.out.println("RICEVUTO INTERRUPT!!");
				ciclo = false;
			}
		}
		System.out.println("Il Thread " + super.getName() + " termina !!");
	}
}
