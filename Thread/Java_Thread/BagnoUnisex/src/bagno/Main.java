package bagno;

public class Main {
	public static void main(String ...args) {
		Bagno bagno = new Bagno();
		Uomo[] uomo = new Uomo[12];
		Donna[] donna = new Donna[12];
		
		for (int i = 0; i < uomo.length; i++) {
			uomo[i] = new Uomo(bagno, i);
			donna[i] = new Donna(bagno, i);
		}
		
		for (int i = 0; i < uomo.length; i++) {
			uomo[i].start();
			donna[i].start();
		}
		
		try {
			for (int i = 0; i < uomo.length; i++) {
				uomo[i].join();
				donna[i].join();
			}
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		
		System.out.println("MO TERMINO PURE IO !!");
	}
	
	
	
	
}
