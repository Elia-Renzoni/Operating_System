package marmellata;

public class ProduzioneMarmellata {
	public static void main(String ...args) {
		Laboratorio lab = new Laboratorio();
		ProduttoreIngredienti produttore = new ProduttoreIngredienti(lab);
		Minion[] m = new Minion[46];
		
		for (int i = 0; i < m.length; i++) {
			m[i] = new Minion(i, lab);
		}
				
		for (int i = 0; i < m.length; i++) {
			m[i].start();
		}
		
		produttore.start();
		
		try {
			for (int i = 0; i < m.length; i++) {
				m[i].join();
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		
		produttore.interrupt();
		try {
			produttore.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("MO TERMINO PURE IO  !");
	}
}
