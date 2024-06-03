package funiviacab;

public class Cabina extends Thread {
	private Funivia funivia;
	
	public Cabina(final Funivia funivia) {
		super("Cabina");
		this.funivia = funivia;
	}
	
	public void run() {
		boolean isAlive = true;
		
		while (isAlive) {
			try {
				this.funivia.caricaPasseggeri(this);
				Thread.sleep(250);
				this.funivia.scaricaPasseggeri(this);
				Thread.sleep(250);
			} catch (InterruptedException ex) {
				isAlive = false;
				System.out.println("INTERRUPT RICEVUTO!!");
			}
		}
		System.out.println("Il Thread " + super.getName() + " termina!!!");
	}
}
