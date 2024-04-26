package multiprocessore;

public class Main {
	public static void main(String ...args) {
		CodaScheduling coda = new CodaScheduling();
		Processo[] p = new Processo[36];
		Processore[] pr = new Processore[4];
		
		for (int i = 0; i < p.length; i++) {
			p[i] = new Processo(coda, i);
		}
		
		for (int i = 0; i < pr.length; i++) {
			pr[i] = new Processore(coda, i);
		}
		
		for (int i = 0; i < pr.length; i++) {
			pr[i].start();
		}
		
		for (int i = 0; i < p.length; i++) {
			p[i].start();
		}
		
		try {
			for (int i = 0; i < p.length; i++) {
				p[i].join();
			}
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		
		for (int i = 0; i < pr.length; i++) {
			pr[i].interrupt();
		}
		
		try {
			for (int i = 0; i < pr.length; i++) {
				pr[i].join();			
			}
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
	}
}
