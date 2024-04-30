package bagno;

import java.util.Random;

public class Donna extends Thread {
	private Bagno bagno;
	private Random rnd;
	
	public Donna(final Bagno bagno, final int i) {
		super("Donna " + i);
		this.bagno = bagno;
		this.rnd = new Random();
	}
	
	public void run() {
		int primaSleep = 100 + this.rnd.nextInt(1101);
		int secondaSleep = 100 + this.rnd.nextInt(300);
		
		try {
			Thread.sleep(primaSleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.bagno.accessoAlBagno(this);
		
		System.out.println("Il Thread " + super.getName() + " fa i bisogni !");
		
		try {
			Thread.sleep(secondaSleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.bagno.notificaUscita(this);
		
		System.out.println("Il Thread " + super.getName() + " termina !!");
	}
}
