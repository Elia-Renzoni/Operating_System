package threadExample;

public class User {
	public static void main(String[] args) {
		PrintWords th1 = new PrintWords("Fano", "thFano");
		PrintWords th2 = new PrintWords("Pesaro", "thPesaro");
		PrintWords th3 = new PrintWords("Urbino", "thUrbino");
		
		th1.start();
		th2.start();
		th3.start();
		
		System.out.println("Processo Main terminato");
	}
}
