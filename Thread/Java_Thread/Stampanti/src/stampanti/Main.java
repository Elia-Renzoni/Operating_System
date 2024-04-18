package stampanti;

public class Main {
	public static void main(String ...args) {
		Sistema sistema = new Sistema();
		GestoreStampanti gs = new GestoreStampanti(sistema);
		Processo[] p = new Processo[32];
		
		for (int i = 0; i < p.length; i++) {
			p[i] = new Processo(sistema, i);
		}
		
		gs.start();
		for (int i = 0; i < p.length; i++) {
			p[i].start();
		}
		try {
			for (int i = 0; i < p.length; i++) {
				p[i].join();
			}
		} catch (InterruptedException ex) {
			System.err.println(ex);
		}
		
		gs.interrupt();
		try {
			gs.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("MO TERMINO PURE IO !");
		
	}
}
