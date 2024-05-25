package Concurrent_Programming.Thread.Java_Thread.ImbarcoPasseggeri.politica;

public class Main {
    public static void main(String... args) {
        PuntoImbarco punto = new PuntoImbarco();
        Passeggero[] p = new Passeggero[55];
        Elefante[] e = new Elefante[5];

        for (int i = 0; i < e.length; i++) {
            e[i] = new Elefante(punto, i);
            e[i].start();
        }

        for (int i = 0; i < p.length; i++) {
            p[i] = new Passeggero(punto, i);
            p[i].start();
        }

        for (int i = 0; i < p.length; i++) {
            try {
                p[i].join();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        
        for (int i = 0; i < e.length; i++) {
            e[i].interrupt();
        }

        for (int i = 0; i < e.length; i++) {
            try {
                e[i].join();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        System.out.println("MO TERMINO PURE IO!!!");
    }
}
