package Concurrent_Programming.Thread.Java_Thread.DiscoIO.scheduling;


public class Main {
    public static void main(String ...args) {
        Disco disco = new Disco();
        GestoreDisco gd = new GestoreDisco(disco);
        Processo[] p = new Processo[16];

        gd.start();

        for (int i = 0; i < p.length; i++) {
            p[i] = new Processo(disco, i);
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

        gd.interrupt();
        try {
            gd.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("MO TERMINO PURE IO!!");


    }
    
}
