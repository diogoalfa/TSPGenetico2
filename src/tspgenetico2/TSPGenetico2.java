/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tspgenetico2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Diogo
 */
public class TSPGenetico2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //-------------------Todas las Ciudades que debo visitar ---------------------------------------
        String p = "0123456789abcdefghijklmnopqrstuvwxyz";
        // String p ="abcdefghi";
        System.out.println("COMBINACION : " + p);
        Random rnd = new Random(111);

        Map<String, Ciudad> lista = new HashMap<String, Ciudad>();
        for (int i = 0; i < p.length(); i++) {
            String id = p.charAt(i) + "";

            lista.put(id,
                    new Ciudad(id, rnd.nextInt(100), rnd.nextInt(100)));
        }
        
        List<Solucion> poblacion=new ArrayList();
        List<Solucion> hijas=new ArrayList();
        Solucion hija1;
        Solucion hija2;
        int cantidad=20;
        int largoSolucion=p.length();
       // llenarPoblacionEstocastico(lista, poblacion, p, rnd, cantidad);
        llenarConGradienteAscedente(lista, poblacion, p, rnd, cantidad);
        //imprimirPoblacion(lista, poblacion);
        System.out.println("MEJOR SOLUCION POBLACION : "+mejorDeLaPoblacion(lista, poblacion));
        List<Solucion> nuevaPoblacion=new ArrayList();
        ordenarAscedentePoblacion(lista, poblacion);
        System.out.println("Primero poblacion :"+poblacion.get(0));
        for (int i = 0; i < 300; i++) {
            if (nuevaPoblacion.isEmpty()==true) {
                for (int j = 0; j < 50; j++) {
                    Solucion hab1 = poblacion.get(rnd.nextInt(cantidad/2));
                    Solucion hab2 = poblacion.get(rnd.nextInt(cantidad/2));
                    
                    // System.out.println(" Habitante 1 :" + hab1);
                    // System.out.println(" Habitante 2 :" + hab2);
                    if (rnd.nextInt(100) <= 80) {
                        hijas = cruce(lista,hab1.getTour(), hab2.getTour(), rnd.nextInt(largoSolucion));
                    }
                    //System.out.println("Hijas sin mutar :"+hijas);
                    hija1 = hijas.get(0);
                    hija2 = hijas.get(1);
                    // System.out.println("M solucion :"+mejorSolucion);

                    // System.out.println("MEJOR de las Hijas : ["+mejorSolucion+"] - FITNESS : "+mejorFitness);
                    if (rnd.nextInt(100) <= 15) {
                        hija1 = mutacion(lista,hijas.get(0), rnd);

                    }
                    if (rnd.nextInt(100) <= 8) {
                        hija2 = mutacion(lista,hijas.get(1), rnd);
                    }
                    // System.out.println("Hija mutada 1 : "+hija1+" | Fitness : "+fitness(lista, hija1));System.out.println("Hija mutada 2 : "+hija2+" | Fitness : "+fitness(lista, hija2));
                    // hijas.clear();
                    hijas.set(0, hija1);
                    hijas.set(1, hija2);
                    

                    nuevaPoblacion.add(hijas.get(0));
                    nuevaPoblacion.add(hijas.get(1));
                    
                }
            }
            else {
                ordenarAscedentePoblacion(lista, nuevaPoblacion);
                for (int j = 0; j < 50; j++) {
                     Solucion hab1 = nuevaPoblacion.get(rnd.nextInt(cantidad/2));
                    Solucion hab2 = nuevaPoblacion.get(rnd.nextInt(cantidad/2));
                    
                    // System.out.println(" Habitante 1 :" + hab1);
                    // System.out.println(" Habitante 2 :" + hab2);
                    if (rnd.nextInt(100) <= 80) {
                        hijas = cruce(lista,hab1.getTour(), hab2.getTour(), rnd.nextInt(largoSolucion));
                    }
                    //System.out.println("Hijas sin mutar :"+hijas);
                    hija1 = hijas.get(0);
                    hija2 = hijas.get(1);
                    // System.out.println("M solucion :"+mejorSolucion);

                    // System.out.println("MEJOR de las Hijas : ["+mejorSolucion+"] - FITNESS : "+mejorFitness);
                    if (rnd.nextInt(100) <= 15) {
                        hija1 = mutacion(lista,hijas.get(0), rnd);

                    }
                    if (rnd.nextInt(100) <= 8) {
                        hija2 = mutacion(lista,hijas.get(1), rnd);
                    }
                    // System.out.println("Hija mutada 1 : "+hija1+" | Fitness : "+fitness(lista, hija1));System.out.println("Hija mutada 2 : "+hija2+" | Fitness : "+fitness(lista, hija2));
                    // hijas.clear();
                    hijas.set(0, hija1);
                    hijas.set(1, hija2);
                    

                    nuevaPoblacion.add(hijas.get(0));
                    nuevaPoblacion.add(hijas.get(1));
                }
            }
            
        }
        Solucion mejorPoblacion=mejorDeLaPoblacion(lista,nuevaPoblacion);
        System.out.println("Mejor Nueva Poblacion :"+mejorPoblacion);
        
        
        
        
    }
    
    public static void ordenarAscedentePoblacion(Map<String, Ciudad> lista, List<Solucion> poblacion) {
        
        Solucion aux;
        for (int i = 0; i < poblacion.size() - 1; i++) {
            for (int j = 0; j < poblacion.size() - i - 1; j++) {
                if (poblacion.get(j + 1).getCostoTour() < poblacion.get(j).getCostoTour()) {
                    aux = poblacion.get(j + 1);
                    poblacion.set(j + 1, poblacion.get(j));
                    poblacion.set(j, aux);
                }
            }
        }
    }
    
    
       public static void llenarConGradienteAscedente(Map<String, Ciudad> lista, List<Solucion> poblacion, String ciudades, Random rnd, int cantidad) {
        Solucion solVecina = getVecina(lista,newSolAleatoria(ciudades, rnd), rnd);
        // System.out.println("Sol Vecina :" + solVecina);
        double fitnessVecina = solVecina.getCostoTour();
        Solucion solMejor =new Solucion(lista,newSolAleatoria(ciudades, rnd));

        double mejorFitnes = solMejor.getCostoTour();
        if (mejorFitnes > fitnessVecina) {
            solMejor = solVecina;

        }

        int contador = 0;
        // Ciclo de Cnd de termino
        for (int j = 0; j < 50; j++) {

            solVecina =getVecina(lista, solMejor.getTour(), rnd);
            for (int i = 0; i < cantidad; i++) {

                solVecina = getVecina(lista,solMejor.getTour(),rnd);
                double fitnesVeci = solVecina.getCostoTour();

                if (fitnesVeci < mejorFitnes) {
                    mejorFitnes = fitnesVeci;
                    solMejor = solVecina;
                    contador = 0;
                    // System.out.println("Mejor Sol:"+solMejor+"| mejor fitness :"+mejorFitnes);
                } else {
                    contador++;
                    if (contador == 15) {
                        break;
                    }
                }
                poblacion.add(solMejor);
                //System.out.println("Mejor Solucion Gradiente :"+mejorFitnes+" | indice :"+i); 
                contador = 0;
            }


        }
        System.out.println("MEJOR GRADIENTE ASC : "+solMejor);
    }

    public static Solucion mejorDeLaPoblacion(Map<String, Ciudad> lista, List<Solucion> poblacion) {
        double mejorFitness =poblacion.get(0).getCostoTour();
        Solucion mejorSolucion = poblacion.get(0);
        double fitnessHab;
        for (int i = 0; i < poblacion.size(); i++) {
            fitnessHab =poblacion.get(i).getCostoTour();
            if (mejorFitness > fitnessHab) {
                mejorSolucion = poblacion.get(i);
                mejorFitness = fitnessHab;
            }
        }
        return mejorSolucion;
    }

    public static Solucion mutacion(Map<String, Ciudad> lista,Solucion habitante, Random rnd) {
        Solucion newSol1 = null;
        Solucion newSol2 = null;
        Solucion stTemp = habitante;
        char[] stTemp2;

        int dado1 = rnd.nextInt(stTemp.getTour().length());
        int dado2 = rnd.nextInt(stTemp.getTour().length());

        char posicionA = stTemp.getTour().charAt(dado1);
        char posicionB = stTemp.getTour().charAt(dado2);
        //System.out.println("char a - char b:"+posicionA+"-"+posicionB);
        //                    Antiguo   ,Nuevo
        stTemp2 = stTemp.getTour().toCharArray();
        stTemp2[dado1] = posicionB;
        stTemp2[dado2] = posicionA;

        // Arrays.toString(stTemp2);
        Solucion sol= new Solucion(lista,new String(stTemp2));
        return sol;
    }

    public static List<Solucion> cruce(Map<String, Ciudad> lista,String hab1, String hab2, int dado) {
        //System.out.println("DADO : "+dado);
        List<Solucion> hijas = new ArrayList();
        String habTemp1 = "";
        String habTemp2 = "";

        //LLenar los dos string con con las primeros caracteres de las soluciones habitantes
        for (int i = 0; i < hab1.length(); i++) {

            if (i > dado) {
            } else {
                habTemp1 = habTemp1 + hab1.charAt(i);
                habTemp2 = habTemp2 + hab2.charAt(i);
                //System.out.println("Sol 1 priori :"+habTemp1);
            }
        }
        //   System.out.println("primero digitos :"+habTemp1);
        //   System.out.println("primero digitos :"+habTemp2);
        //--------------------------

        for (int i = 0; i < hab1.length(); i++) {
            //System.out.println("Temp 1 :"+habTemp1);
            //System.out.println(hab2.charAt(i)+"||"+"(-1) :"+habTemp1.indexOf(hab2.charAt(i)));
            if (habTemp1.indexOf(hab2.charAt(i)) < 0) {
                habTemp1 = habTemp1 + hab2.charAt(i);
                // System.out.println("Que pasa HAB 1 :"+habTemp1);
            }
            if (habTemp2.indexOf(hab1.charAt(i)) < 0) {
                habTemp2 = habTemp2 + hab1.charAt(i);
            }

        }

        Solucion sol1=new Solucion(lista, habTemp1);
        Solucion sol2=new Solucion(lista, habTemp2);
        hijas.add(sol1);
        hijas.add(sol2);
        return hijas;
    }

    public static void llenarPoblacionEstocastico(Map<String, Ciudad> lista,List<Solucion> poblacion, String ciudades, Random rnd, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            Solucion sol=new Solucion(lista,newSolAleatoria(ciudades, rnd));
            poblacion.add(sol);
        }
        // return poblacion;
    }

    public static void imprimirPoblacion(Map<String, Ciudad> lista,List<Solucion> poblacion) {
        for (int i = 0; i < poblacion.size(); i++) {
            System.out.println("Habitante " + i + " : " + poblacion.get(i));
        }
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
     
      public static Solucion getVecina(Map<String, Ciudad> lista,String p, Random rnd) {
        Solucion newSol1 = null;
        Solucion newSol2 = null;
        Solucion stTemp =new Solucion(lista, p, rnd);
        char[] stTemp2;

        int dado1 = rnd.nextInt(stTemp.getTour().length());
        int dado2 = rnd.nextInt(stTemp.getTour().length());

        char posicionA = stTemp.getTour().charAt(dado1);
        char posicionB = stTemp.getTour().charAt(dado2);
        //System.out.println("char a - char b:"+posicionA+"-"+posicionB);
        //                    Antiguo   ,Nuevo
        stTemp2 = stTemp.getTour().toCharArray();
        stTemp2[dado1] = posicionB;
        stTemp2[dado2] = posicionA;

        // Arrays.toString(stTemp2);
        Solucion sol=new Solucion(lista, new String(stTemp2));
        return sol;
    }
}
