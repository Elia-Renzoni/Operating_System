package concenrto;

public class Main {
	public static void main(String ...args) {
		Varco v = new Varco();
		Persona[] p = new Persona[60];
		Controllore[] c = new Controllore[4];
		
		
		for (int i = 0; i < c.length; i++) {
			c[i] = new Controllore(v, i);
			c[i].start();
		}
		
		for (int i = 0; i < p.length; i++) {
			if (i < 12) {
				p[i] = new Persona(v, i, true);
			} else {
				p[i] = new Persona(v, i, false);
			}
			p[i].start();
		}
		
		for (int i = 0; i < p.length; i++) {
			try {
				p[i].join();
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
		
		System.out.println("MO TERMINO PURE IO !!!");
	}
}
