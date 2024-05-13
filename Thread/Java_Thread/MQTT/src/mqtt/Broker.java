package mqtt;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Broker {
	private LinkedList<Message> coda;
	private ReentrantLock mutex;
	private Condition publisher;
	private Condition risvegliaGestore;
	private Condition topicA;
	private Condition topicB;
	private Condition topicC;
	
	public Broker() {
		this.coda = new LinkedList<>();
		this.mutex = new ReentrantLock();
		this.publisher = this.mutex.newCondition();
		this.risvegliaGestore = this.mutex.newCondition();
		this.topicA = this.mutex.newCondition();
		this.topicB = this.mutex.newCondition();
		this.topicC = this.mutex.newCondition();
	}
	
	public void publish(Publisher pub) {
		this.mutex.lock();
		try {
			while (this.coda.size() == 10) {
				try {
					this.publisher.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			this.coda.add(pub.getMesage());
			System.out.println("Il Thread " + pub.getName() + " ha inserito un dato nel buffer !");
			this.risvegliaGestore.signal();
		} finally {
			this.mutex.unlock();
		}
	}
	
	public void dispatchMessage() throws InterruptedException {
		Message message;
		this.mutex.lock();
		try {
			while (this.coda.size() == 0) {
				this.risvegliaGestore.await();
			}
			System.out.println("Il Gestore prende un messaggio !!");
			message = this.coda.getFirst();
			System.out.println("Il Gestore ha preso il messaggio : " + message.getMessage() + " " + message.getTopic());
			this.coda.remove(message);
			this.publisher.signal();
			System.out.println("Il Gestore risveglia un publisher se stava dormendo");
		} finally {
			this.mutex.unlock();
		}
		
		
		this.mutex.lock();
		try {
			if (message.getTopic().equals("A")) {
				this.topicA.signalAll();
			} else if (message.getTopic().equals("B")) {
				this.topicB.signalAll();
			} else {
				this.topicC.signalAll();
			}
		} finally {
			this.mutex.unlock();
		}
	}
	
	public void subscribe(Subscriber sub) throws InterruptedException {
		
		if (sub.getTopic().equals("A")) {
			this.topicA.await();
		} else if (sub.getTopic().equals("B")) {
			this.topicB.await();
		} else {
			this.topicC.await();
		}
		
		System.out.println("Il Thread " + sub.getName() + " ha ricevuto il giusto messaggio dal Gestore !!");
	}
	

	
}
