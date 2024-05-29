package gestione.voli;

public class Gestore extends Thread {
	private Aereoporto piste;
	
	public Gestore(final Aereoporto piste) {
		super("Gestore");
		this.piste = piste;
	}
	
	public void run() {
		boolean isAlive = true;
		
		while (isAlive) {
			try {
				this.piste.gestisciAerei(this);
			} catch (InterruptedException e) {
				isAlive = false;
				System.out.println("INTERRUPT RICEVUTO!!");
			}
		}
		System.out.println("Il Thread " + super.getName() + " termina!!");
	}
}
