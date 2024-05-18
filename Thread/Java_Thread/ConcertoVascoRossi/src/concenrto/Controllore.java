package concenrto;

public class Controllore extends Thread {
	private Varco varco;
	
	public Controllore(final Varco varco, final int i) {
		super("Controllore " + i);
		this.varco = varco;
	}
	
	public void run() {
		boolean isAlive = true;
		
		while (isAlive) {
			try {
				this.varco.controllaPersona(this);
				Thread.sleep(400);
			} catch (InterruptedException ex) {
				System.out.println("Interrupt Ricevuto !!");
				isAlive = false;
			}
		}
		System.out.println("Il Thread " + super.getName() + " termina!!");
	}
}
