package mqtt;

import java.util.Random;

public class Publisher extends Thread {
	private Broker broker;
	private Random rand;
	private Message message;
	
	public Publisher(final Broker broker, final int i) {
		super("Publisher " + i);
		this.broker = broker;
		this.rand = new Random();
	}
	
	public Message getMesage() {
		return this.message;
	}
	
	public void run() {
		int contatorePubblicazioni = 0;
		int timeToSleep = this.rand.nextInt(351);
		String topic = null;
		int messageValue = 0;
		int randomS;
		
		while (contatorePubblicazioni != 10) {
			messageValue++;
			randomS = this.rand.nextInt(2);
			
			if (randomS == 0) {
				topic = "A";
			} else if (randomS == 1) {
				topic = "B";
			} else {
				topic = "C";
			}
			
			this.message = new Message(topic, messageValue);
			try {
				Thread.sleep(timeToSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.broker.publish(this);
			contatorePubblicazioni++;
		}
	}
}
