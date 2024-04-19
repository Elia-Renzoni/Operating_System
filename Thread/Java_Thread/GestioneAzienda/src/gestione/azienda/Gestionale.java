package gestione.azienda;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class Gestionale {
	private int nVenditori;
	private int nAddetti;
	private int nControllori;
	private LinkedList<Cliente> coda;
	private ReentrantLock mutexVenditori;
	private ReentrantLock mutexAddetti;
	private ReentrantLock mutexControllori;
	
	public Gestionale() {
		this.coda = new LinkedList<>();
		this.mutexAddetti = new ReentrantLock();
		this.mutexControllori = new ReentrantLock();
		this.mutexVenditori = new ReentrantLock();
	}
	
	public void inserisciCliente(Addetto a, Cliente c) {
		System.out.println("Il Thread " + a.getName() + " sta cercando di aggiungere un cliente !");
		try {
			this.mutexAddetti.lock();
			if (this.nVenditori >= 1)
				this.mutexVenditori.lock();
			if (this.nControllori >= 1) {
				this.mutexControllori.lock();
			}
			this.nAddetti++;
			this.coda.add(c);
			System.out.println("Il Thread " + a.getName() + " ha aggiunto il cliente "
					           + c.getId() + " con budget " + c.getBudget());
			this.nAddetti--;
		} finally {
			if (this.nVenditori >= 1)
				this.mutexVenditori.unlock();
			if (this.nControllori >= 1)
				this.mutexControllori.unlock();
			this.mutexAddetti.unlock();
		}
		
	}
	
	public int trovaMax(Venditore v) {
		int toReturn = 1;
		System.out.println("" + v.getName() + " sta cercando di trovare un cliente !");
		try {
			this.mutexVenditori.lock();
			if (this.nAddetti >= 1)
				this.mutexAddetti.lock();
			if (this.nControllori >= 1)
				this.mutexControllori.lock();
			this.nVenditori++;
			Cliente max = this.coda.stream()
					.max(Comparator.comparingInt(Cliente::getBudget))
					.orElse(null);
			if (max == null)
				toReturn = -1;
			else 
				System.out.println("Il Thread " + v.getName() + " ha trovato il cliente " + max.getId() + 
						           " con budget " + max.getBudget());
			this.nVenditori--;
		} finally {
			if (this.nAddetti >= 1)
				this.mutexAddetti.unlock();
			if (this.nControllori >= 1)
				this.mutexControllori.unlock();
			this.mutexVenditori.unlock();
		}
		
		return toReturn;
	}
	
	public void rimuoviCliente(Controllore c) {
		Cliente cliente = null;
		System.out.println("Il Thread " + c.getName() + " sta cercando di eliminare un cliente !!");
		try {
			this.mutexControllori.lock();
			if (this.nAddetti >= 1) 
				this.mutexAddetti.lock();
			if (this.nVenditori >= 1)
				this.mutexVenditori.lock();
			this.nControllori++;
			
			cliente = this.coda.getFirst();
			this.coda.remove(cliente);
			
			System.out.println("Il Thread " + c.getName() + " ha eliminato il cliente " + cliente.getId() + 
					           " con buget " + cliente.getBudget());
			this.nControllori--;
		} finally {
			if (this.nAddetti >= 1)
				this.mutexAddetti.unlock();
			if (this.nVenditori >= 1) 
				this.mutexVenditori.unlock();
			this.mutexControllori.unlock();
		}
	}


}
