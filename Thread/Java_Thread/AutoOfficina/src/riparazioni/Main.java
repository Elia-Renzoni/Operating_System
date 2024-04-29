package riparazioni;

public class Main {
	public static void main(String ...args) {
		Officina officina = new Officina();
		Meccanico[] m = new Meccanico[5];
		Auto[] a = new Auto[60];
		
		for (int i = 0; i < m.length; i++) {
			m[i] = new Meccanico(officina, i);
		}
		
		for (int i = 0; i < a.length; i++) {
			if (i < 45) {
				a[i] = new Auto(officina, false, i);
			} else {
				a[i] = new Auto(officina, true, i);
			}
		}
		
		for (int i = 0; i < m.length; i++) {
			m[i].start();
		}
		
		for (int i = 0; i < a.length; i++) {
			a[i].start();
		}
		
		try {
			 for (int i = 0; i < a.length; i++) {
				 a[i].join();
			 }
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		
		for (int i = 0; i < m.length; i++) {
			m[i].interrupt();
		}
		
		try {
			for (int i = 0; i < m.length; i++) {
				m[i].join();
			}
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		
		System.out.println("MO TERMINO PURE IO !!!");
	}
}
