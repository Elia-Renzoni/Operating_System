package multiprocessore;

public class Processore extends Thread {
	private CodaScheduling coda;
	
	public Processore(final CodaScheduling coda, final int i) {
		super("Processore " + i);
		this.coda = coda;
	}
	
	public void run() {
		boolean isAlive = true;
		while (isAlive) {
			try {
				this.coda.eseguiProcesso(this);
			} catch (InterruptedException e) {
				System.out.println("INTERRUPT RICEVUTO per il Thread " + super.getName());
				isAlive = false;
			}
		}
		System.out.println("Il Thread " + super.getName() + " termina !!");
	}
}
