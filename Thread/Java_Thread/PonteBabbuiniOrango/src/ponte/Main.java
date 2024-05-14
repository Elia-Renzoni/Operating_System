package ponte;

import java.util.Random;

public class Main {
	public static void main(String... args) {
		Ponte p = new Ponte();
		Orango o = new Orango(p);
		Babbuino[] b = new Babbuino[40];
		Random rand = new Random();
		
		
		o.start();
		for (int i = 0; i < b.length; i++) {
			int v = rand.nextInt(100);
			if (v <= 60) { 
				b[i] = new Babbuino(p, i, "Verdi");
			} else {
				b[i] = new Babbuino(p, i, "Gialli");
			}
			b[i].start();
		}
		
		for (int i = 0; i < b.length; i++) {
			try {
				b[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		o.interrupt();
		try {
			o.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("MO TERMINO PURE IO !!");
		
	}
}
