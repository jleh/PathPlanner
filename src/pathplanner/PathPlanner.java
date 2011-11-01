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
public class PathPlanner {

    static readData reader = new readData();
    static CalculateRoute calculator = new CalculateRoute();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<Kaupunki> kaupungit;
        
        kaupungit = reader.readFile("200.txt");
        calculator.calculateRoute(kaupungit);
        
    }
}
