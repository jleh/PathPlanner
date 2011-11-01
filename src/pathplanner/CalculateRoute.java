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
    
    public void calculateRoute(ArrayList<Kaupunki> lista){//Heuristinen etsintä
        Kaupunki k = lista.get(0);
        for(Path pa : k.Naapurit){
                pa.eval = evalvoi1(pa);
                lisaaListalle(solmulista, pa);
            }
        
        //Hakualgoritmi
        while(!solmulista.isEmpty()){
            k = solmulista.removeFirst().b;
            System.out.println(k.nimi);
            
            if(vieraillut.size() == solmulista.size()) //Kun on käyty kaikissa
                kaikki = true;
            
            for(Path pa : k.Naapurit){
                pa.eval = evalvoi1(pa);
                lisaaListalle(solmulista, pa);
            }
        }
    }
    
    
    
    private int evalvoi1(Path p){//Evalvointifunktio
        int ret = 0;
        
        if(p.b.visited == false)
            ret += 3;
        if(p == lahinNaapuri(p.a))
            ret += 2;
        if(p.b.visited == false && p == lahinNaapuri(p.a))
            ret += 5;
        
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
    
    public void lisaaListalle(LinkedList lista, Path p){
        if(lista.contains(p))
            return;
        lista.add(p);
        Collections.sort(lista);
    }
}
