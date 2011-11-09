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
                if(p.b == kaupungit.get(i+1))
                    matka += p.length;
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
                System.out.println("Lasketaan " + i + " paras tähän asti: " + paras.pituus);
            
            if((tulos = calculator.calculateRoute(kaupungit, paras)) != null){
                if(tulos.pituus < paras.pituus)
                    paras = tulos;
            }
        }
        System.out.println("Paras pituus: " + paras.pituus);  
        System.out.println(laskePituus(paras.reitti) + " " + paras.reitti.size());
        
        //Kehitetään tulosta
        Kaupunki edellinen = calculator.kehitys(paras, null);
        for(int i = 1; i < kaupungit.size(); i++){
           edellinen = calculator.kehitys(paras, edellinen);
        }
        calculator.kehitys(paras, edellinen);
        
        System.out.println(laskePituus(paras.reitti) + " " + paras.reitti.size());
        //for(Kaupunki k : paras.reitti)
        //    System.out.println(k.nimi);
        
        reader.saveBest(paras); //Tallennetaan tiedostioon
        
    }
}
