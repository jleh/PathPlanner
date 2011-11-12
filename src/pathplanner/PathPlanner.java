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
    
    static int laskePituus(LinkedList<Kaupunki> kaupungit){
        int matka = 0;
        for(int i = 0; i < kaupungit.size()-1; i++){
            for(Path p : kaupungit.get(i).Naapurit){
                if(p.b == kaupungit.get(i+1)){
                    matka += p.length;
                }
            }
        }
        
        return matka;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<Kaupunki> kaupungit;
        
        System.out.println("Laskenta alkaa");
        
        int iteraatiot, parasInt = 0;
        
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
                System.out.println("Lasketaan " + i + " paras tähän asti: "  + parasInt);
            
            tulos = null;
            tulos = calculator.calculateRoute(kaupungit, paras);
            
            if(tulos != null){
                if(laskePituus(tulos.reitti) < paras.pituus){
                    paras.pituus = laskePituus(tulos.reitti);
                    paras.reitti = tulos.reitti;
                    
                    //Kehitetään tulosta
                    Kaupunki edellinen = calculator.kehitys(paras, null);
                    for(int j = 1; j < kaupungit.size(); j++){
                          edellinen = calculator.kehitys(paras, edellinen);
                    }
                    paras.pituus = laskePituus(paras.reitti);
                    parasInt = paras.pituus;
                    System.out.println("Lyhin " + paras.pituus + " " + paras.reitti.size());
                    
                    //Koodissa on jotain hämärää, antaa ilman tallennusta tässä
                    //jotain aivan surkeaa
                    reader.saveBest(paras);
                }
            }
        }
    }
}
