package posteitaliane;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class UfficioPostale {
	private LinkedList<Cliente> coda;
	private ReentrantLock mutex;
	private Semaphore nuovoCliente;
	private Semaphore clienti;
	
	public UfficioPostale() {
		this.coda = new LinkedList<>();
		this.mutex = new ReentrantLock();
		this.nuovoCliente = new Semaphore(0);
		this.clienti = new Semaphore(3, true);
	}
	
	public void richiediServizio(Cliente c) {
		System.out.println("Il Thread " + c.getName() + " viene aggiunto alla coda !");
		this.mutex.lock();
		try {
			this.coda.add(c);
		} finally {
			this.mutex.unlock();
		}
		
		try {
			this.clienti.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Il Thread " + c.getName() + " risveglia un impiegato !!");
		this.nuovoCliente.release();
		c.sospendimi();
	}
	
	public void serviCliente(Impiegato i) throws InterruptedException {
		try {
			this.nuovoCliente.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Il Thread " + i.getName() + " si risveglia !!");
		
		this.mutex.lock();
		try {
			Cliente bestCliente;
			bestCliente = this.getClienteB();
			if (bestCliente == null) {
				bestCliente = this.getClienteE();
				if (bestCliente == null) {
					bestCliente = this.getClienteA();
					if (bestCliente == null) {
						bestCliente = this.getClienteP();
						if (bestCliente == null) {
							bestCliente = this.coda.getFirst();
						}
					}
				}
			}
			System.out.println("Il Thread " + i.getName() + " sta per servire il Thrad " + bestCliente.getName() + 
					" con priorità " + bestCliente.getTipoRichiesta());
			this.coda.remove(bestCliente);
			bestCliente.liberami();
		} finally {
			this.mutex.unlock();
		}
		
		Thread.sleep(50);
		this.clienti.release();
	}
	
	private Cliente getClienteP() {
		return this.coda.stream()
				.filter(n -> n.getTipoRichiesta() == 0)
				.findAny()
				.orElse(null);
	}
	
	private Cliente getClienteA() {
		return this.coda.stream()
				.filter(n -> n.getTipoRichiesta() == 1)
				.findAny()
				.orElse(null);
	}
	
	private Cliente getClienteB() {
		return this.coda.stream()
				.filter(n -> n.getTipoRichiesta() == 2)
				.findAny()
				.orElse(null);
	}
	
	private Cliente getClienteE() {
		return this.coda.stream()
				.filter(n -> n.getTipoRichiesta() == 3)
				.findAny()
				.orElse(null);
	}
}
