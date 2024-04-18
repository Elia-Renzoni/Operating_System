package pizzeria;

public class Pizzaiolo extends Thread {
	private Pizzeria pizzeria;
	private static final int timeToSleep = 500;
	
	public Pizzaiolo(Pizzeria pizzeria) {
		super("Pizzaiolo");
		this.pizzeria = pizzeria;
	}
	
	public void run() {
		boolean loop = true;
		while (loop) {
			try {
				this.pizzeria.serviCliente();
				Thread.sleep(timeToSleep);
				System.out.println("Il Pizzaiolo ha terminato di produrre le pizze per il cliente !");
			} catch (InterruptedException e) {
				loop = false;
				System.out.println("INTERRUPT!");
			}
		}
		System.out.println("Il Pizzaiolo termina l'esecuzione !!");
	}
}
