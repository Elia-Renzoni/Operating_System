package pizzeria;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Pizzeria {
	private ReentrantLock mutex;
	private Semaphore nuovoCliente;
	private LinkedList<Cliente> coda;
	
	public Pizzeria() {
		this.mutex = new ReentrantLock();
		this.nuovoCliente = new Semaphore(0);
		this.coda = new LinkedList<>();
	}
	
	public void ordinaPizze(int nPizze, Cliente c) {
		try {
			System.out.println("Il Thread " + c.getName() + " ha ordinato " + nPizze + " pizze");
			this.mutex.lock();
			this.coda.add(c);
			System.out.println("Il Thread " + c.getName() + " è entrato nella coda d'attesa");
			this.nuovoCliente.release();
		} finally {
			this.mutex.unlock();
		}
		
		System.out.println("Il Thread " + c.getName() + " si è sospeso dentro la coda !!");
		c.sospendimi();
	}
	
	public void serviCliente() throws InterruptedException {
		Cliente clienteMigliore = null;
		
		this.nuovoCliente.acquire();
		System.out.println("Il Pizzaiolo è pronto a servire !");
		
		try {
			this.mutex.lock();
			clienteMigliore = getClientePrioritario();
			clienteMigliore.liberami();
			System.out.println("Il Pizzaiolo ha scelto il Thread " + clienteMigliore.getName() + " con pizze " + clienteMigliore.getNumeroPizze());
		} finally {
			this.mutex.unlock();
		}
	}
	
	private Cliente getClientePrioritario() {
		Cliente cliente;
		this.coda.sort(Comparator.comparingInt(Cliente::getNumeroPizze).reversed());
		cliente = this.coda.getFirst();
		this.coda.remove(cliente);
		return cliente;
	}
}
