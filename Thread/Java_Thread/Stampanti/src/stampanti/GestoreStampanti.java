package stampanti;

public class GestoreStampanti extends Thread {
	private Sistema sistema;
	
	public GestoreStampanti(Sistema sistema) {
		super("Gestore Stampanti");
		this.sistema = sistema;
	}
	
	public void run() {
		boolean loop = true;
		while (loop) {
			try {
				this.sistema.gestisciStampanti();
			} catch (InterruptedException e) {
				System.out.println("INTERRUPT !");
				loop = false;
			}
		}
		System.out.println("Il Thread " + super.getName() + " termina !");
	}
}
