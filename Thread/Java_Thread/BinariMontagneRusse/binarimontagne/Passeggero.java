package Concurrent_Programming.Thread.Java_Thread.BinariMontagneRusse.binarimontagne;

import java.util.Random;

public class Passeggero extends Thread {
    private MontagneRusse montagne;
    private Random rand;

    public Passeggero(final MontagneRusse m, final int i) {
        super("Passeggero " + i);
        this.montagne = m;
        this.rand = new Random();
    }

    public void run() {
        try {
            Thread.sleep(this.rand.nextInt(1001) + 1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.montagne.board(this);
        this.montagne.unboard(this);
        System.out.println("Il Thread " + super.getName() + " termina!!");
    }
}
