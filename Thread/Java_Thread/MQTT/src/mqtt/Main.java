package mqtt;

public class Main {
	public static void main(String ...args) {
		Broker broker = new Broker();
		Gestore gestore = new Gestore(broker);
		Publisher[] pub = new Publisher[3];
		Subscriber[] sub = new Subscriber[30];
		
		for (int i = 0; i < pub.length; i++) {
			pub[i] = new Publisher(broker, i);
		}
		
		for (int i = 0; i < sub.length; i++) {
			if (i < 10) {
				sub[i] = new Subscriber("A", broker, i);
			} else if (i >= 10 && i < 20) {
				sub[i] = new Subscriber("B", broker, i);
			} else if (i >= 20 && i < 30) {
				sub[i] = new Subscriber("C", broker, i);
			}
		}
		
		gestore.start();
		for (int i = 0; i < pub.length; i++) {
			pub[i].start();
		}
		
		for (int i = 0; i < sub.length; i++) {
			sub[i].start();
		}
		
		try {
			for (int i = 0; i < pub.length; i++) {
				pub[i].join();
			}	
		} catch (InterruptedException e) {
			System.out.println(e);
		}
			
		for (int i = 0; i < sub.length; i++) {
			sub[i].interrupt();
		}
		
		
		for (int i = 0; i < sub.length; i++) {
			try {
				sub[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		gestore.interrupt();
		
		try {
			gestore.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("MO TERMINO PURE IO !!");
		
	}
}
