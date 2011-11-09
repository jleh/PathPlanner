/*
 * Tulosluokka
 * 
 */
package pathplanner;
import java.util.LinkedList;

/**
 *
 * @author Juuso
 */
public class Tulos {
    public LinkedList<Kaupunki> reitti = new LinkedList<Kaupunki>();
    public int pituus;
    
    public Tulos(int m, LinkedList<Kaupunki> r){
        reitti = r;
        pituus = m;
    }
}
