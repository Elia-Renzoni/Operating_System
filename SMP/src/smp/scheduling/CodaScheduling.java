package smp.scheduling;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class CodaScheduling {
	private int numeroBatch;
	private int numeroInteractive;
	private int numeroIO;
	private LinkedList<Cpu> codaCPU;
	private ReentrantLock mutex;
	private Semaphore taskInteractive;
	private Semaphore taskIO;
	private Semaphore taskBatch;
	
	public CodaScheduling() {
		this.numeroBatch = 0;
		this.numeroInteractive = 0;
		this.numeroIO = 0;
		this.codaCPU = new LinkedList<>();
		this.mutex = new ReentrantLock();
		this.taskInteractive = new Semaphore(0, true);
		this.taskBatch = new Semaphore(0, true);
		this.taskIO = new Semaphore(0, true);
	}
	
	public void submit(Task t) {
		System.out.println("Il Thread " + t.getName() + " si presenta nella coda di scheduling!!");
		this.mutex.lock();
		try {
			if (t.getTipo().equals("interactive")) {
				this.numeroInteractive++;
			} else if (t.getTipo().equals("batch")) {
				this.numeroBatch++;
			} else {
				this.numeroIO++;
			}
			
			// poll elimina anche l'oggetto
			Cpu c = this.codaCPU.pollLast();
			if (c != null)
				c.liberami();
		} finally {
			this.mutex.unlock();
		}
				
		if (t.getTipo().equals("interactive")) {
			try {
				System.out.println("Il Thread " + t.getName() + " si sospende in coda!!");
				this.taskInteractive.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (t.getTipo().equals("batch")) {
			try {
				System.out.println("Il Thread " + t.getName() + " si sospende in coda!!");
				this.taskBatch.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				System.out.println("Il Thread " + t.getName() + " si sospende in coda!!");
				this.taskIO.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void pull(Cpu c) throws InterruptedException {			
		this.mutex.lock();
		try {
			
			if ((this.numeroBatch + this.numeroInteractive + this.numeroIO) == 0) {
				this.codaCPU.add(c);
				c.sospendimi(this.mutex.newCondition());
				System.out.println("Il Thread " + c.getName() + " si addormenta!!");
			}
			
			if (this.numeroInteractive > 0) {
				System.out.println("Il Thread " + c.getName() + " esegue un task interactive!!");
				this.taskInteractive.release();
				this.numeroInteractive--;
			} else if (this.numeroIO > 0) {
				System.out.println("Il Thread " + c.getName() + " esegue un task io!!");
				this.taskIO.release();
				this.numeroIO--;
			} else {
				System.out.println("Il Thread " + c.getName() + " esegue un task batch!!");
				this.taskBatch.release();
				this.numeroBatch--;
			}
		} finally {
			this.mutex.unlock();
		}
	}
}
