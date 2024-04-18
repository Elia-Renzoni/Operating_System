package pizzeria;

public class GestionePizzeria {
	public static void main(String ...args) {
		Pizzeria pizzeria = new Pizzeria();
		Cliente[] cliente = new Cliente[12];
		Pizzaiolo pizzaiolo = new Pizzaiolo(pizzeria);
		
		for (int i = 0; i < cliente.length; i++) {
			cliente[i] = new Cliente(i, pizzeria);
		}
		
		pizzaiolo.start();
		for (int i = 0; i < cliente.length; i++) {
			cliente[i].start();
		}
		
		try {
			for (int i = 0; i < cliente.length; i++) {
				cliente[i].join();
			}
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}

		pizzaiolo.interrupt();
		try {
			pizzaiolo.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("MO TERMINO PURE IO !!");
		
	}
}
