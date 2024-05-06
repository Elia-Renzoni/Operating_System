package posteitaliane;

public class Main {
	public static void main(String ...args) {
		UfficioPostale ufficio = new UfficioPostale();
		Cliente[] c = new Cliente[16];
		Impiegato[] im = new Impiegato[3];
		
		for (int i = 0; i < c.length; i++) {
			c[i] = new Cliente(ufficio, i);
		}
		
		for (int i = 0; i < im.length; i++) {
			im[i] = new Impiegato(ufficio, i);
		}
		
		for (int i = 0; i < im.length; i++) {
			im[i].start();
		}
		
		for (int i = 0; i < c.length; i++) {
			c[i].start();
		}
		
		try {
			for (int i = 0; i < c.length; i++) {
				c[i].join();
			}
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		
		for (int i = 0; i < im.length; i++) {
			im[i].interrupt();
		}
		
		try {
			for (int i = 0; i < im.length; i++) {
				im[i].join();
			}
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		
		System.out.println("MO TERMINO PURE IO !!!");
	}
}
