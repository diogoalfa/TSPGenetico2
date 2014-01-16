/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tspgenetico2;

import java.util.Map;
import java.util.Random;

/**
 *
 * @author Diogo
 */
public class Solucion {
    
    public String tour;
    public double costoTour;

    public String getTour() {
        return tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }

    public double getCostoTour() {
        return costoTour;
    }

    public void setCostoTour(double costoTour) {
        this.costoTour = costoTour;
    }
    
    public Solucion(Map<String, Ciudad> lista,String tour){
        this.tour=tour;
        this.costoTour=fitness(lista, this.tour);
    }
    
    public Solucion(Map<String, Ciudad> lista,String p,Random rnd){
        this.tour=newSolAleatoria(p, rnd);
        this.costoTour=fitness(lista,this.tour);
    }
    
    
    
     public static String newSolAleatoria(String p, Random rnd) {
        String temp = p;
        String newSol = "";
        while (temp.length() > 1) {
            int dado = rnd.nextInt(temp.length());
            char ch = temp.charAt(dado);
            newSol += ch;
            temp = temp.replaceAll(ch + "", "");
        }
        return newSol + temp;
    } 
    
   public static double fitness(Map<String, Ciudad> lista, String p) {
        double tour = 0.0;
        for (int i = 0; i < p.length() - 1; i++) {
            String inicio = p.charAt(i) + "";
            String fin = p.charAt(i + 1) + "";
            tour += lista.get(inicio)
                    .distanceTo(lista.get(fin));

        }

        String inicio = p.charAt(0) + "";
        String fin = p.charAt(p.length() - 1) + "";

        tour += lista.get(inicio)
                .distanceTo(lista.get(fin));

        return tour;
    }

    @Override
    public String toString() {
        return "Solucion{" + "tour=" + tour + ", costoTour=" + costoTour + '}';
    }
   
    
}
