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
public class Kaupunki {
    public int nimi;
    public ArrayList<Path> Naapurit = new ArrayList<Path>();
    public boolean visited = false;
    public ArrayList<Kaupunki> edellinen = new ArrayList<Kaupunki>();
    public int ddist;
    public Kaupunki prev;
    public int vierailut = 0;
}
