/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pathplanner;

/**
 *
 * @author Juuso
 */
public class Path implements Comparable{
    public Kaupunki a;
    public Kaupunki b;
    int length;
    int eval = 0;

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof Path))
            throw new ClassCastException("Ei toimi");
        return this.eval - ((Path)o).eval;
    }
    
    public String toString(){
        return a.nimi + " " + b.nimi + " " + eval;
    }
}
