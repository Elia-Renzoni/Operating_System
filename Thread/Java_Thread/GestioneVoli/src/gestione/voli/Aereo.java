package gestione.voli;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Aereo extends Thread {
	private Aereoporto piste;
	private Random rand;
	private Semaphore mySem;
	private String richiesta;
	private int peso;
	
	public Aereo(final Aereoporto piste, final int i) {
		super("Aereo " + i);
		this.piste = piste;
		this.rand = new Random();
		this.mySem = new Semaphore(0);
	}
	
	public void sospendimi() {
		try {
			this.mySem.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void risvegliami() {
		this.mySem.release();
	}
	
	public String getRichiesta() {
		return this.richiesta;
	}
	
	public int getPeso() {
		return this.peso;
	}
	
	public void run() {
		this.richiesta = (this.rand.nextInt(2)) == 1? "atterraggio": "decollo";
		
		for (int i = 0; i < 2; i++) {
			this.peso = this.rand.nextInt(101) + 100;
			this.piste.richiediServizio(this);
			if (this.richiesta.equals("atterraggio")) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.piste.liberaPista(this);
			this.richiesta = (this.richiesta.equals("atterraggio")? "decollo": "atterraggio");
		}
		System.out.println("Il Thread " + super.getName() + " termina!!");
	}
}
