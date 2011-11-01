/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pathplanner;
import java.util.ArrayList;

/**
 *
 * @author Juuso
 */
public class CalculateRoute {
    
    
    public void calculateRoute(ArrayList<Kaupunki> lista){
        Kaupunki k = lista.get(0);
        
        System.out.println(k.Naapurit.size());
    }
    
    private double evalvoi1(Path p){
        double ret = 0;
        
        if(p.b.visited == false)
            ret += 3;
        
        return ret;
    }
    
    private Path lahinNaapuri(Kaupunki k){
        Path lyhin = k.Naapurit.get(0);
        
        for(Path p : k.Naapurit){
            if(p.length < lyhin.length)
                lyhin = p;
        }
        
        return lyhin;
    }
}
