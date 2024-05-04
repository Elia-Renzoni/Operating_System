package strada.spazzaneve.auto;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Strada {
	private boolean blocco;
	private ReentrantLock mutex;
	private Semaphore strada;
	private Condition auto;
	
	public Strada() {
		this.blocco = false;
		this.mutex = new ReentrantLock();
		this.strada = new Semaphore(1, true);
		this.auto = this.mutex.newCondition();
	}
	
	public void accessoStrada(Thread t) {
		System.out.println("Il Thread " + t.getName() + " arriva a ridosso della Strada !");
		
		this.mutex.lock();
		try {
			if (t instanceof Auto) {
				this.blocco = true;
				while (blocco) {
					try {
						this.auto.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} finally {
			this.mutex.unlock();
		}
		
		System.out.println("Il Thread " + t.getName() + " prende un permesso !!");
		try {
			this.strada.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void liberaStrada(Thread t) {
		this.strada.release();
		
		
		this.mutex.lock();
		try {
			if (t instanceof Spazzaneve) {
				this.blocco = false;
				this.auto.signal();
			}
		} finally {
			this.mutex.unlock();
		}
	}
}
