/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pathplanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Juuso
 */
public class CalculateRoute {
    
    LinkedList<Path> solmulista = new LinkedList<Path>();
    ArrayList<Kaupunki> vieraillut = new ArrayList<Kaupunki>();
    boolean kaikki = false;
    Path polku;
    
    public void calculateRoute(ArrayList<Kaupunki> lista){//Heuristinen etsintä
        Kaupunki k = lista.get(0);
        for(Path pa : k.Naapurit){
                pa.eval = evalvoi1(pa);
                lisaaListalle(solmulista, pa);
            }
        
        //Hakualgoritmi
        while(!solmulista.isEmpty()){
            polku = solmulista.removeFirst();
            k = polku.b;
            
            polku.eval = 0;
            
            if(!vieraillut.contains(k))
                vieraillut.add(k);
            
            System.out.println("Nyk:" + k.nimi + " Vier:" + vieraillut.size()
                    + " Listalla: " + solmulista.size() + " seur: " + solmulista.getFirst().toString()); //For debug
            
            if(vieraillut.size() == solmulista.size()){ //Kun on käyty kaikissa
                kaikki = true;
                System.out.println("Kaikissa käyty");
                return;
            }
            
            for(Path pa : k.Naapurit){
                pa.eval = evalvoi1(pa);
                lisaaListalle(solmulista, pa);
            }
        }
    }
    
    
    
    private int evalvoi1(Path p){//Evalvointifunktio
        int ret = 0;
        
        if(p.b.visited == false)
            ret += 5;
        if(p == lahinNaapuri(p.a))
            ret += 2;
        if(p.b.visited == false && p == lahinNaapuri(p.a))
            ret += 5;
        if(p.b.visited == true)
            ret -= 3;
        if(kaikki == true && p.b.nimi == 0)
            ret += 20;
        
        ret += seuraavatPaikat(p);
        
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
    
    //Etsii seuraavan kaupungin seuraavat kaupungit
    private int seuraavatPaikat(Path p){
        if(p.b.visited == false)
            return 3;
        else
            return 0;
    }
    
    public void lisaaListalle(LinkedList lista, Path p){ //Solmulistan päivitys
        if(lista.contains(p))
            return;
        lista.add(p);
        Collections.sort(lista);
    }
}
