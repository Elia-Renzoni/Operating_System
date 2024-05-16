/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionehub;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author studente
 */
public class Hub {
    private int primeDosi;
    private LinkedList<Persona> dosi;
    private ReentrantLock mutex;
    private Semaphore nuovaPersona;
    
    
    public Hub() {
        this.primeDosi = 0;
        this.dosi = new LinkedList<>();
        this.mutex = new ReentrantLock();
        this.nuovaPersona = new Semaphore(0);
    }
    
    public void richiediVaccino(Persona p) {
        System.out.println("Il Thread " + p.getName() + " si mette in coda !");
        
        this.mutex.lock();
        try {
            if (p.getDose().equals("Prima")) {
                this.primeDosi++;
            }
            
            this.dosi.add(p);
        } finally {
            this.mutex.unlock();
        }
        
        this.nuovaPersona.release();
        System.out.println("Il Thread " + p.getName() + " si sospende!!");
        p.sospendimi();
    }
    
    public void somministra(Medico m) throws InterruptedException {
        Persona best = null;
        this.nuovaPersona.acquire();
        
        System.out.println("Il Medico si risveglia !!");
        
        this.mutex.lock();
        try {
           best = getBest();
           if (best != null) {
               this.dosi.remove(best);
               System.out.println("Medico ha somministrato il vaccino a " + best);
           } else {
               if (this.primeDosi == 4) {
                   best = getSecondaDose();
                   if (best != null) {
                       this.dosi.remove(best);
                       System.out.println("Medico ha somministrato il vaccno a " + best);
                       this.primeDosi = 0;
                   } else {
                       best = getPrimaDose();
                       this.dosi.remove(best);
                       System.out.println("Medico ha somministrato il vaccino a " + best);
                   } 
               } else {
                   best = getPrimaDose();
                   this.dosi.remove(best);
                   System.out.println("Medico ha somministrato il vaccino a " + best);
               }
           }
           best.liberami();
        } finally {
            this.mutex.unlock();
        }
    }
    
    private Persona getBest() {
        return this.dosi.stream()
                .max(Comparator.comparing(Persona::getEta))
                .get();
    }
    
    private Persona getSecondaDose() {
        return this.dosi.stream()
                .filter(n -> n.getDose().equals("Seconda"))
                .findAny()
                .orElse(null);
    }
    
    private Persona getPrimaDose() {
        return this.dosi.stream()
                .filter(n -> n.getDose().equals("Prima"))
                .findAny()
                .orElse(null);
    }
}
