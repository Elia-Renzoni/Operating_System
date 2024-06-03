package funiviacab;

public class Main {
	public static void main(String ...args) {
		Funivia f = new Funivia();
		Cabina c = new Cabina(f);
		Passeggero[] p = new Passeggero[24];
		
		for (int i = 0; i < p.length; i++) {
			p[i] = new Passeggero(f, i);
			p[i].start();
		}
		
		c.start();
		
		for (int i = 0; i < p.length; i++) {
			try {
				p[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		c.interrupt();
		try {
			c.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("MO TERMINO PURE IO!!");
	}
}
