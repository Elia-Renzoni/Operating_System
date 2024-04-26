package gestione.fabbrica;

public class GestioneFabbricaDiCioccolato {
	public static void main(String ...args) {
		Fabbrica fab = new Fabbrica();
		UmpaLumpa[] ul = new UmpaLumpa[40];
		WillyWonka w = new WillyWonka(fab);
		
		w.start();
		for (int i = 0; i < ul.length; i++) {
			ul[i] = new UmpaLumpa(fab, i);
		}
		
		for (int i = 0; i < ul.length; i++) {
			ul[i].start();
		}
		
		try {
			for (int i = 0; i < ul.length; i++) {
				ul[i].join();
			}
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		
		w.interrupt();
		try {
			w.join();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		
		System.out.println("MO TERMINO PURE IO !!");
	}
}
