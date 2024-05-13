package mqtt;

public class Subscriber extends Thread {
	private Broker broker;
	private String topic;
	
	public Subscriber(final String topic, final Broker broker, final int i) {
		super("Subscriber " + i);
		this.broker = broker;
		this.topic = topic;
	}
	
	public String getTopic() {
		return this.topic;
	}
	
	public void run() {
		boolean isAlive = true;
		
		while (isAlive) {
			try {
				this.broker.subscribe(this);
			} catch (InterruptedException ex) {
				isAlive = false;
				System.out.println("Interrupteee Riceeevuuuutoooh");
			}
		}
		
		System.out.println("il Thread " + super.getName() + " termina !!!");
	}
}
