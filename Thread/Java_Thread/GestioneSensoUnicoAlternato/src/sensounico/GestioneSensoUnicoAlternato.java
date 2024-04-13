package sensounico;

public class GestioneSensoUnicoAlternato {
	public static void main(String ...args) {
		Ponte ponte = new Ponte();
		Auto[] auto = new Auto[10];
		
		for (int i = 0; i < auto.length; i++) {
			auto[i] = new Auto(ponte, i);
			auto[i].start();
		}
				
		for (int i = 0; i < auto.length; i++) {
			try {
				auto[i].join();
			} catch (InterruptedException e) {
				//e.printStackTrace();
				System.out.println("*****");
			}
		}
		
		System.out.println("MO TERMINO PURE IO !");
	}
}
