package gestionedisco;

public class GestoreDisco extends Thread {
    private ControllerDisco controller;
    private int id;
    
    public GestoreDisco(final ControllerDisco c, final int i) {
        super("Controller " + i);
        this.controller = c;
        this.id = i;
    }

    public int getID() {
        return this.id;
    }
    
    public void run() {
        boolean isAlive = true;
        while (isAlive) {
            try {
                this.controller.serviRichiesta(this);
            } catch (InterruptedException e) {
                isAlive = false;
                System.out.println("INTERRUPT RICEVUTO!!");
            }
        }
    }

}