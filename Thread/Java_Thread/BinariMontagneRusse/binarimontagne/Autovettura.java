package Concurrent_Programming.Thread.Java_Thread.BinariMontagneRusse.binarimontagne;

public class Autovettura extends Thread {
    private MontagneRusse montagne;

    public Autovettura(final MontagneRusse montagne) {
        super("Autovettura");
        this.montagne = montagne;
    }

    public void run() {
        boolean isAlive = true;

        while (isAlive) {
            try {
                this.montagne.load(this);
                this.montagne.go(this);
                this.montagne.unload(this);
            } catch (InterruptedException ex) {
                isAlive = false;
                System.out.println("INTERRUPT RICEVUTO!!");
            }
        }
        System.out.println("Il Thread " + super.getName() + " termina!!");
    }
}
