package expo;

public class Main {
	public static void main(String... args) {
		Varco v = new Varco();
		Visitatore[] visi = new Visitatore[25];
		Controllore[] c = new Controllore[3];
		
		for (int i = 0; i < visi.length; i++) {
			if (i < 7)
				visi[i] = new Visitatore(v, i, "disabile");
			else 
				visi[i] = new Visitatore(v, i, "normale");
			visi[i].start();
		}
		
		for (int i = 0; i < c.length; i++) {
			c[i] = new Controllore(v, i);
			c[i].start();
		}
		
		for (int i = 0; i < visi.length; i++) {
			try {
				visi[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < c.length; i++) {
			c[i].interrupt();
		}
		
		for (int i = 0; i < c.length; i++) {
			try {
				c[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("MO TERMINO PURE IO !!");
	}
}
