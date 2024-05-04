package strada.spazzaneve.auto;

public class Main {
	public static void main(String ...args) {
		Strada strada = new Strada();
		Spazzaneve spazzaneve = new Spazzaneve(strada);
		Auto[] auto = new Auto[32];
		
		for (int i = 0; i < auto.length; i++) {
			auto[i] = new Auto(strada, i);
			auto[i].start();
		}
		
		spazzaneve.start();
		
		try {
			for (int i = 0; i < auto.length; i++) {
				auto[i].join();
			}
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		
		spazzaneve.interrupt();
		try {
			spazzaneve.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Mo termino pure io !!");
	}
}
