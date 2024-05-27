package smp.scheduling;

import java.util.Random;

public class Task extends Thread {
	private CodaScheduling coda;
	private Random rand;
	private String tipo; // 0 = interactive, 1 = batch, 2 = io
	
	public Task(final CodaScheduling coda, final int i, final String tipo) {
		super("Task " + tipo + " " + i);
		this.coda = coda;
		this.tipo = tipo;
		this.rand = new Random();
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public void run() {
		int contatore = 0;
		while (contatore != 5) {
			try {
				Thread.sleep(this.rand.nextInt(601) + 100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.coda.submit(this);
			contatore++;
		}
		System.out.println("Il Thread " + super.getName() + " termina!!");
	}
}
