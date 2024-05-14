package ponte;

public class Orango extends Thread {
	private Ponte ponte;
	
	public Orango(final Ponte ponte) {
		super("Orango");
		this.ponte = ponte;
	}
	
	public void run() {
		boolean isAlive = true;
		
		while (isAlive) {
			try {
				this.ponte.gestisciAttraversamento(this);
			} catch (InterruptedException e) {
				isAlive = false;
				System.out.println("Interrupt Ricevuto !!");
			}
		}
		
		System.out.println("Il Thread " + super.getName() + " termina !!");
	}
}
