package gestione.azienda;

public class GestioneAzienda {
	public static void main(String ...args) {
		Gestionale gestionale = new Gestionale();
		Venditore[] venditore = new Venditore[12];
		Addetto[] addetto = new Addetto[8];
		Controllore[] controllore = new Controllore[4];
		
		for (int i = 0; i < venditore.length; i++) {
			venditore[i] = new Venditore(gestionale, i);
		}
		
		for (int i = 0; i < addetto.length; i++) {
			addetto[i] = new Addetto(gestionale, i);
		}
		
		for (int i = 0; i < controllore.length; i++) {
			controllore[i] = new Controllore(gestionale, i);
		}
		
		for (int i = 0; i < addetto.length; i++) {
			addetto[i].start();
		}
		
		for (int i = 0; i < venditore.length; i++) {
			venditore[i].start();
		}
		
		for (int i = 0; i < controllore.length; i++) {
			controllore[i].start();
		}
		
		
		try {
			for (int i = 0; i < venditore.length; i++) {
				venditore[i].join();
			}
		} catch (InterruptedException ex) {
			System.err.println(ex);
		}
		
		for (int i = 0; i < addetto.length; i++) {
			addetto[i].interrupt();
		}
		for (int i = 0; i < addetto.length; i++) {
			try {
				addetto[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < controllore.length; i++) {
			controllore[i].interrupt();
		}
		for (int i = 0; i < controllore.length; i++) {
			try {
				controllore[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("MO TERMINO PURE IO !!");
	}
}
