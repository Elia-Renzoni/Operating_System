package Concurrent_Programming.Thread.Java_Thread.DiscoIO.scheduling;

public class GestoreDisco extends Thread {
    private Disco disco;

    public GestoreDisco(final Disco disco) {
        super("Gestore Disco");
        this.disco = disco;
    }
    
    public void run() {
        boolean isAlive = true;

        while (isAlive) {
            try {
                this.disco.serviRichiesta(this);
                Thread.sleep(35);
            } catch (InterruptedException ex) {
                isAlive = false;
                System.out.println("INTERRUPT RICEVUTO!!");
            }
        }
        System.out.println("Il Thread "  + super.getName() + " termina!!");
    }
}
