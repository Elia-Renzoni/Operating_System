package strada.spazzaneve.auto;

import java.util.Random;

public class Auto extends Thread {
	private Strada strada;
	private String sensoMarcia;
	private Random rand;
	
	public Auto(final Strada strada, final int i) {
		super("Auto " + i);
		this.strada = strada;
		this.rand = new Random();
	}
	
	public void run() {
		int tempoArrivo = 200 + this.rand.nextInt(801);
		int sensoMarcia = this.rand.nextInt(2);
		
		if (sensoMarcia == 0) { 
			this.sensoMarcia = "Nord-Sud";
		} else {
			this.sensoMarcia = "Sud-Nord";
		}
		
		try {
			Thread.sleep(tempoArrivo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.strada.accessoStrada(this);
		System.out.println("Il Thread " + super.getName() + " con direzione " + this.sensoMarcia + " percorre la strada");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.strada.liberaStrada(this);
		System.out.println("Il Thread " + super.getName() + " con direzione " + this.sensoMarcia + " è uscito dalla strada !");
		
		System.out.println("Il Thread " + super.getName() + " con direzione " + this.sensoMarcia + " termina !!");
	}
}
