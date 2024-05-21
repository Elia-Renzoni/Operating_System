package ambulatorio;

import java.util.Random;

public class Persona extends Thread {
	private Ambulatorio amb;
	private Random rand;
	private String tipo;
	
	public Persona(final Ambulatorio amb, final String tipo, final int i) {
		super(" " + tipo + " " + i);
		this.tipo = tipo;
		this.amb = amb;
		this.rand = new Random();
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public void run() {
		try {
			Thread.sleep(this.rand.nextInt(700) + 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.amb.mettitiInCoda(this);
		this.amb.ottieniPrestazioni(this);
		this.amb.esci(this);
		
		System.out.println("Il Thread " + super.getName() + " termina!!");
	}
}
