/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pathplanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Juuso
 */
public class CalculateRoute {
    
    LinkedList<Path> solmulista = new LinkedList<Path>();
    ArrayList<Kaupunki> vieraillut = new ArrayList<Kaupunki>();
    ArrayList<Path> katsotut = new ArrayList<Path>();
    boolean kaikki = false;
    Path polku;
    Kaupunki edellinen = new Kaupunki();
    
    
    public void calculateRoute(ArrayList<Kaupunki> lista){//Heuristinen etsintä
        Kaupunki k = lista.get(0);
        for(Path pa : k.Naapurit){
            pa.eval = evalvoi1(pa);
            lisaaListalle(solmulista, pa);
        }

        int matka = 0;
        
        System.out.println("0");
        
    //Hakualgoritmi
        while(!solmulista.isEmpty()){

            polku = solmulista.removeFirst();
            k = polku.b;

            System.out.println(k.nimi);
            if(k.nimi == 0 && kaikki == true){ //Route is found
                break;
            }

            matka += polku.length;
            polku.eval = 0;
            
            if(!vieraillut.contains(k))
                vieraillut.add(k);
            
            
            //Debug print
            // System.out.println("Nyk:" + k.nimi + " Vier:" + vieraillut.size()
            //         + " Listalla: " + solmulista.size() + " " + matka);// + " seur: " + solmulista.getFirst().toString()); //For debug
            
            if(vieraillut.size() == lista.size()){ //Kun on käyty kaikissa
                kaikki = true;
                //System.out.println("Kaikissa käyty");
                //return;
            }
            
            for(Path pa : k.Naapurit){
                pa.eval = evalvoi1(pa);
                lisaaListalle(solmulista, pa);
            }
        }
               
        System.out.println("Total distance: " + matka);
    }
    
    
    
    private int evalvoi1(Path p){//Evalvointifunktio
        int ret = 0;
        
        if(p.b.visited == false)
            ret += 15;
        if(p == lahinNaapuri(p.a))
            ret += 10;
        if(p.b.visited == false && p == lahinNaapuri(p.a))
            ret += 25;
        if(p.b.visited == true && kaikki == false)
            ret -= 10;
        if(kaikki == true && p.b.nimi == 0)
            ret += 20;
        
        if(lahinNaapuri(p.b).b.nimi == 0)
            ret += 50;
        
        if(lahinNaapuri(p.b).b.visited == false)
            ret += 30;
        
        if(edellinen == p.b)
            ret -= 20;
        

        
        return ret;
    }
    
    private Path lahinNaapuri(Kaupunki k){ //Etsii lähimmän naapurin
        Path lyhin = k.Naapurit.get(0);
        
        for(Path p : k.Naapurit){
            if(p.length < lyhin.length)
                lyhin = p;
        }
        return lyhin;
    }
    
    public void lisaaListalle(LinkedList lista, Path p){ //Solmulistan päivitys
        if(lista.contains(p))
            return;
        lista.add(p);
        Collections.sort(lista);
    }
}
