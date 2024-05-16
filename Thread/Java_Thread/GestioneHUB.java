/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestionehub;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author studente
 */
public class GestioneHUB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Hub hub = new Hub();
        Medico m = new Medico(hub);
        Persona[] p = new Persona[42];
    
        m.start();
        for (int i = 0; i < p.length; i++) {
            if (i < (p.length / 2))
                p[i] = new Persona(hub, i, "Prima");
            else 
                p[i] = new Persona(hub, i, "Seconda");
            p[i].start();
        }
        
        for (int i = 0; i < p.length; i++) {
            try {
                p[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(GestioneHUB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        m.interrupt();
        try {
            m.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(GestioneHUB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("MO TERMINO PURE IO !!");
    }
    
}
