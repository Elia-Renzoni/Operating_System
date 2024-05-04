package strada.spazzaneve.auto;

import java.util.Random;

public class Spazzaneve extends Thread {
	private Strada strada;
	
	public Spazzaneve(final Strada strada) {
		super("Spazzaneve");
		this.strada = strada;
	}
	
	public void run() {
		boolean isAlive = true;
	
		while (isAlive) {
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				isAlive = false;
				System.out.println(e);
			}
			
			this.strada.accessoStrada(this);
			
			System.out.println("Il Thread " + super.getName() + " percorre la strada !");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				isAlive = false;
				e.printStackTrace();
			}
			
			this.strada.liberaStrada(this);
		}
		
		System.out.println("Il Thread " + super.getName() + " termina !!");
	}
}
