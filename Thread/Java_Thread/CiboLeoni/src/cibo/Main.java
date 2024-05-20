package cibo;

public class Main {
	public static void main(String ...args) {
		Capanna c = new Capanna();
		Guardiano g = new Guardiano(c);
		Leone[] l = new Leone[20];
		
		g.start();
		for (int i = 0; i < l.length; i++) {
			l[i] = new Leone(c, i);
			l[i].start();
		}
		
		for (int i = 0; i < l.length; i++) {
			try {
				l[i].join();
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
