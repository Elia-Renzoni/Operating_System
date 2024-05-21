package ambulatorio;

public class Main {
	public static void main(String ...args) {
		Ambulatorio a = new Ambulatorio();
		Persona[] p = new Persona[15];
		
		for (int i = 0; i < p.length; i++) {
			if (i < 3) {
				p[i] = new Persona(a, "Informatore", i);
			} else {
				p[i] = new Persona(a, "Paziente", i);
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
		
		System.out.println("MO TERMINO PURE IO!!!");
	}
}
