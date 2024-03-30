package reazione.chimica;

public class ReazioneChimica {
	public static void main(String[] args) {
		CameraCombustione c = new CameraCombustione();
		Ossigeno[] o = new Ossigeno[40];
		Metano[] m = new Metano[20];
		
		for (int i = 0; i < o.length; i++) {
			if (i < 20) {
				o[i] = new Ossigeno(c, "Ossigeno " + i);
				m[i] = new Metano(c, "Metano " + i);
			} else if (i >= 20) { 
				o[i] = new Ossigeno(c, "Ossigeno " + i);
			}
		}
		
		for (int i = 0; i < o.length; i++) {
			if (i < 20) {
				o[i].start();
				m[i].start();
			} else if (i >= 20) {
				o[i].start();
			}
		}
		
		try {
			for (int i = 0; i < o.length; i++) {
				if (i < 20) {
					o[i].join();
					m[i].join();
				} else if (i >= 20) {
					o[i].join();
				}
			}
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		
		System.out.println("Mo termino pure io !");
	}
}
