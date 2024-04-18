package stampanti; 

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Processo extends Thread {
	private Sistema sistema;
	private Random rnd;
	private int pid;
	private Semaphore proc;	// lo metto dentro ogni thread così è privato, quindi la coda implicita contiene solo il thread specifico
	private int numeroPagine;
	
	public Processo(final Sistema sistema, int i) {
		super("Processo " + i);
		this.sistema = sistema;
		this.rnd = new Random();
		this.pid = i;
		this.proc = new Semaphore(0);
	}
	
	public void run() {
		this.numeroPagine = 4 * this.pid + this.rnd.nextInt(4 * this.pid + 100);
		int numeroRichieste = 0;
		while (numeroRichieste != 5) {
			this.sistema.richiediStampante(numeroPagine, this);
			System.out.println("Ora il processo " + super.getName() + " attende finchè la stampa non sarà pronta");
			numeroRichieste++;
			try {
				Thread.sleep(numeroPagine * 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Il Processo " + super.getName() + " ha terminato di stampare la pagine !!");
			this.sistema.liberaStampante(this);
		}
		System.out.println("Il Processo " + super.getName() + " ha fatto 5 richieste quindi ora termina !");
	}
	
	public void sospendimi() {
		try {
			this.proc.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void rilasciami() {
		this.proc.release();
	}
	
	public int getNumeroPagine() {
		return this.numeroPagine;
	}
	
	
}
