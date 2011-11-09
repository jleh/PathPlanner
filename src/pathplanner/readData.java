/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pathplanner;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Juuso
 */
public class readData {
    
    public int roads;
    public int locations;
    ArrayList<Kaupunki> kaupungit = new ArrayList<Kaupunki>();
    
    public ArrayList<Kaupunki> readFile(String file){
        String[] tokens;
        roads = 0;
        locations = 0;
        Path p, p1;
        int a, b;
        
        try{
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            Kaupunki k = new Kaupunki();
            while((strLine = br.readLine()) != null) {
                //DO the shit
                tokens = strLine.split(" ");
                
                if(tokens.length == 1 && locations == 0) //Get number of roads
                    locations = Integer.parseInt(tokens[0]);
                if(tokens.length == 1 && roads != 0) //Get number of locations
                    roads = Integer.parseInt(tokens[0]);
                
                if(tokens.length == 3){
                    if(kaupungit.isEmpty())
                        createCities(locations); //Initialize cities
                    
                    p = new Path();
                    p1 = new Path();
                    
                    a = Integer.parseInt(tokens[0]);
                    b = Integer.parseInt(tokens[1]);
                    
                    p.a = kaupungit.get(a); //Reitti toimii molempiin suuntiin
                    p.b = kaupungit.get(b);
                    p1.a = kaupungit.get(b);
                    p1.b = kaupungit.get(a);
                    p.length = Integer.parseInt(tokens[2]);
                    p1.length = Integer.parseInt(tokens[2]);
                    kaupungit.get(Integer.parseInt(tokens[0])).Naapurit.add(p);
                    kaupungit.get(Integer.parseInt(tokens[1])).Naapurit.add(p1);
                }
            }
            
            in.close();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        
        return kaupungit;
    }
    
    private void createCities(int lkm){
        Kaupunki k;
        
        for(int i = 0; i < lkm; i++){
            k = new Kaupunki();
            k.nimi = i;
            kaupungit.add(k);
        }
    }
    
    public void saveBest(Tulos paras){
        PrintWriter kirjoitin;
        
        try {
            kirjoitin = new PrintWriter(new FileOutputStream("tulos.txt"));
            } catch(FileNotFoundException e) {
                System.out.println("Virhe tiedostoissa");
			return;
            }
        for(Kaupunki k : paras.reitti)
            kirjoitin.println(k.nimi);
        
        kirjoitin.close();
    }
}
