package gestione.voli;

public class Main {
	public static void main(String ...args) {
		Aereoporto aereoporto = new Aereoporto();
		Aereo[] a = new Aereo[22];
		Gestore g = new Gestore(aereoporto);
		
		g.start();
		
		for (int i = 0; i < a.length; i++) {
			a[i] = new Aereo(aereoporto, i);
			a[i].start();
		}
		
		for (int i = 0; i < a.length; i++) {
			try {
				a[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		g.interrupt();
		
		try {
			g.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("MO TERMINO PURE IO!!");
	}
}
