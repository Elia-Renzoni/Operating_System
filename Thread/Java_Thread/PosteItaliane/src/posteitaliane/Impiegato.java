package posteitaliane;

public class Impiegato extends Thread {
	private UfficioPostale ufficio;
	
	public Impiegato(final UfficioPostale ufficio, final int i) {
		super("Impiegato " + i);
		this.ufficio = ufficio;
	}
	
	public void run() {
		boolean isAlive = true;
		
		while (isAlive) {
			try {
				this.ufficio.serviCliente(this);
			} catch (InterruptedException e) {
				isAlive = false;
				System.out.println("Interrupt Ricevuto !!");
			}
		}
		
		System.out.println("Il Thread " + super.getName() + " termina !!");
	}
}
