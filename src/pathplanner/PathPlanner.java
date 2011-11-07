/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pathplanner;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Juuso
 */
public class PathPlanner {

    static readData reader = new readData();
    static CalculateRoute calculator = new CalculateRoute();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<Kaupunki> kaupungit;
        
        if(args.length > 0)
            kaupungit = reader.readFile(args[0]);
        else
            kaupungit = reader.readFile("map.100");
        
        
        Tulos paras = new Tulos(Integer.MAX_VALUE, new LinkedList<Kaupunki>());
        Tulos tulos;
        for(int i = 0; i < 1000; i++){
            tulos = calculator.calculateRoute(kaupungit);
            if(tulos.pituus < paras.pituus)
                paras = tulos;
        }
        System.out.println(paras.pituus);
        for(Kaupunki k : paras.reitti)
            System.out.println(k.nimi);
        
        //calculator.AStar(kaupungit, kaupungit.get(85));
    }
}
