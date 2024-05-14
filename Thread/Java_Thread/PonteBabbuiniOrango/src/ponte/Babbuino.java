package ponte;

import java.util.Random;

public class Babbuino extends Thread {
	private Ponte ponte;
	private Random rand;
	private String tipo;
	
	public Babbuino(final Ponte ponte, final int i, final String tipo) {
		super("Babbuino " + tipo + " " + i);
		this.ponte = ponte;
		this.tipo = tipo;
		this.rand = new Random();
	}
	
	public String getClan() {
		return this.tipo;
	}
	
	public void run() {
		int tempo = this.rand.nextInt(1001);
		
		try {
			Thread.sleep(tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.ponte.richiediAccesso(this);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.ponte.esci(this);
		
		System.out.println("Il Thread " + super.getName() + " termina !!");
	}
}
