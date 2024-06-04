package Concurrent_Programming.Thread.Java_Thread.BinariMontagneRusse.binarimontagne;

public class Main {
    public static void main(String ...args) {
        MontagneRusse montagne = new MontagneRusse();
        Passeggero[] p = new Passeggero[32];
        Autovettura a = new Autovettura(montagne);
        a.start();

        for (int i = 0; i < p.length; i++) {
            p[i] = new Passeggero(montagne, i);
            p[i].start();
        }

        for (int i = 0; i < p.length; i++) {
            try {
                p[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        a.interrupt();
        try {
            a.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("MO TERMINO PURE IO!!");

    }
    
}
