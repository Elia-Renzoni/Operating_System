package posteitaliane;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cliente extends Thread {
	private int tipoRichiesta;
	private Random rand;
	private Semaphore mySem;
	private UfficioPostale ufficio;
	
	public Cliente(final UfficioPostale ufficio, final int i) {
		super("Cliente " + i);
		this.rand = new Random();
		this.mySem = new Semaphore(0);
		this.ufficio = ufficio;
	}
	
	public void sospendimi() {
		try {
			this.mySem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void liberami() {
		this.mySem.release();
	}
	
	public int getTipoRichiesta() {
		return this.tipoRichiesta;
	}
	
	public void run() {
		this.tipoRichiesta = this.rand.nextInt(4);
		int richieste = 0;
		
		while (richieste != 5) {
			this.ufficio.richiediServizio(this);
			richieste++;
		}
		
		System.out.println("Il Thread " + super.getName() + " termina !!");
	}	
}
