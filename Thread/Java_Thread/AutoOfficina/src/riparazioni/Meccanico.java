package riparazioni;

public class Meccanico extends Thread {
	private Officina officina;
	
	public Meccanico(final Officina officina, final int i) {
		super("Meccanico " + i);
		this.officina = officina;
	}
	
	public void run() {
		int attesa = 500;
		boolean isAlive = true;
		
		while (isAlive) {
			try {
				this.officina.riparaAuto(this);
			} catch (InterruptedException e) {
				System.out.println("Il Thread " + super.getName() + " ha ricevuto l'interrupt !");
				isAlive = false;
			}
		}
		System.out.println("Il Thread " + super.getName() + " Termina !!");
	}
}
