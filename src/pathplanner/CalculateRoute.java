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
}
