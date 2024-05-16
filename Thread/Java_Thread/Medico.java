/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionehub;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author studente
 */
public class Medico extends Thread {
    private Hub hub;
    
    public Medico(final Hub hub) {
        super("Medico ");
        this.hub = hub;
    }
    
    public void run() {
        boolean isAlive = true;
        
        while (isAlive) {
            try {
                this.hub.somministra(this);
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                isAlive = false;
                System.out.println("INTERRUPT RICEVUTO");
            }
        }
    }
}
