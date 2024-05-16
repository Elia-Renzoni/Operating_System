/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionehub;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author studente
 */
public class Persona extends Thread {
    private Semaphore mySem;    
    private Random rand;
    private Hub hub;
    private String dose;
    private int eta;
    
    public Persona(final Hub hub, final int i, final String dose) {
        super("Persona " + i + " " + dose);
        this.mySem = new Semaphore(0);
        this.rand = new Random();
        this.hub = hub;
        this.dose = dose;
        this.eta = this.rand.nextInt(89) + 12;
    }
    
    public void sospendimi() {
        try {
            this.mySem.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void liberami() {
        this.mySem.release();
    }
    
    public String getDose() {
        return this.dose;
    }
    
    public int getEta() {
        return this.eta;
    }
    
    public void run() {
        int tempo = this.rand.nextInt(301);
        try {
            Thread.sleep(301);
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.hub.richiediVaccino(this);
        
        System.out.println("Il Thread " + super.getName() + " termina!!");
    }
}
