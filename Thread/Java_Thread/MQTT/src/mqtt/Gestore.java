package mqtt;

public class Gestore extends Thread {
	private Broker broker;
	
	public Gestore(final Broker broker) {
		super("Gestore");
		this.broker = broker;
	}
	
	public void run() {
		boolean isAlive = true;
		
		while (isAlive) {
			try {
				this.broker.dispatchMessage();
			} catch (InterruptedException e) {
				isAlive = false;
				e.printStackTrace();
			}
			
		}
		System.out.println("Il Thread Gestore termina !!");
	}
}
