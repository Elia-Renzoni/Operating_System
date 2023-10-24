package threadExample;

public class PrintWords extends Thread {
	private final String str;
	
	public PrintWords(String str, String threadName) {
		super(threadName);
		this.str = str;
	}
	
	@Override
	public void run() {
		for (int index = 0; index < this.str.length(); index++) {
			System.out.print(this.str.charAt(index));
		}
		System.out.println("\n Thread " + this.getName() + "terminato");
	}
}
