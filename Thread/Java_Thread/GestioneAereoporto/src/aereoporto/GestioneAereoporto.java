package aereoporto;

public class GestioneAereoporto {
	public static void main(String ...args) { 
		Aereoporto a = new Aereoporto();
		Microsoft[] m = new Microsoft[16];
		Hacker[] h = new Hacker[16];
		Navetta n = new Navetta("Navetta", a);
		
		for (int i = 0; i < m.length; i++) {
			h[i] = new Hacker(i, a);
			m[i] = new Microsoft(i, a);
		}
		
		n.start();
		for (int i = 0; i < m.length; i++) {
			h[i].start();
			m[i].start();
		}
		
		try {
			for (int i = 0; i < m.length; i++) {
				h[i].join();
				m[i].join();
			}
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		
		n.interrupt();
		try {
			n.join();
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		
		System.out.println("MO TERMINO PURE IO!");
	}
}
