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
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<Kaupunki> kaupungit;
        
        kaupungit = reader.readFile("map.100");
    }
}
