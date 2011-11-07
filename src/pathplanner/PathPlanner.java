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
        
        int iteraatiot;
        
        if(args.length > 1){
            kaupungit = reader.readFile(args[0]);
            iteraatiot = Integer.parseInt(args[1]);
        }
        else {
            kaupungit = reader.readFile("map.100");
            iteraatiot = 1000;
        }
        
        Tulos paras = new Tulos(Integer.MAX_VALUE, new LinkedList<Kaupunki>());
        Tulos tulos;
        for(int i = 0; i < iteraatiot; i++){
            if(i % 100 == 0)
                System.out.println("Lasketaan " + i);
            
            tulos = calculator.calculateRoute(kaupungit);
            if(tulos.pituus < paras.pituus)
                paras = tulos;
        }
        System.out.println("Paras pituus: " + paras.pituus);
        for(Kaupunki k : paras.reitti)
            System.out.println(k.nimi);
        
        //calculator.AStar(kaupungit, kaupungit.get(85));
    }
}
