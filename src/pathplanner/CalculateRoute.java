/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pathplanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Juuso
 */
public class CalculateRoute {
    
    LinkedList<Path> solmulista = new LinkedList<Path>();
    ArrayList<Kaupunki> vieraillut = new ArrayList<Kaupunki>();
    LinkedList<Kaupunki> reitti = new LinkedList<Kaupunki>();
    ArrayList<Path> katsotut = new ArrayList<Path>();
    boolean kaikki = false;
    Path polku;
    Kaupunki edellinen = new Kaupunki();
    
    
    public Tulos calculateRoute(ArrayList<Kaupunki> lista){//Heuristinen etsintä
        int matka = 0;
        int loppu = 0;
        reitti.clear();
        vieraillut.clear();
        katsotut.clear();
        solmulista.clear();
        alustaKaupungit(lista);
        Kaupunki k = lista.get(0);
        for(Path pa : k.Naapurit){
            //pa.eval = evalvoi1(pa);
            //lisaaListalle(solmulista, pa);
            solmulista.add(pa);
        }
        
        reitti.add(k);
        
        //System.out.println("0");
        LinkedList<Path> tulos = new LinkedList<Path>();
        
    //Hakualgoritmi
        while(true){
            Collections.shuffle(solmulista);
            Path p = solmulista.getFirst();
            
            
            Kaupunki kaupunki = p.b;
            matka += p.length;
            
            reitti.add(kaupunki);
            
            //System.out.println(kaupunki.nimi + " ");
            if(!vieraillut.contains(kaupunki))
                vieraillut.add(kaupunki);
                
            solmulista.clear();
            
            for(Path pa : kaupunki.Naapurit)
                solmulista.add(pa);
            
            if(vieraillut.size() == 100){
                //vika = kaupunki;
                loppu = AStar(kaupunki, tulos);
                break;
            }
        }
               
        //System.out.println("Total distance: " + matka + " " + loppu);
        while(!tulos.isEmpty()){
            reitti.addLast(tulos.removeLast().b);
        }
        return new Tulos(matka+loppu, reitti);
    }
    
    
    
    private int evalvoi(Path p){//Evalvointifunktio
        int ret = 0;

        if(p == lahinNaapuri(p.a))
            ret += 2;
        if(lahinNaapuri(p.b).b.nimi == 0) 
            ret += 20;
        
        return ret;
    }
    
    private Path lahinNaapuri(Kaupunki k){ //Etsii lähimmän naapurin
        Path lyhin = k.Naapurit.get(0);
        
        for(Path p : k.Naapurit){
            if(p.length < lyhin.length)
                lyhin = p;
        }
        return lyhin;
    }
    
    private int vierailtujaNaapureita(Kaupunki k){
        int naapurit = 0;
        
        for(Path p : k.Naapurit){
            if(p.b.visited == true)
                naapurit++;
        }
        
        return naapurit;
    }
    
    private void alustaKaupungit(ArrayList<Kaupunki> lista){
        for(Kaupunki k : lista){
            k.visited = false;
            for(Path p : k.Naapurit){
                p.eval = 0;
                p.prev = null;
            }
        }
    }
    
    public void lisaaListalle(LinkedList lista, Path p){ //Solmulistan päivitys
        if(lista.contains(p))
            return;
        lista.add(p);
        Collections.sort(lista);
    }
    
    public int AStar(Kaupunki alku, LinkedList tulos){
        if(alku == null)
            return 0;
        
        katsotut.clear();
        LinkedList<Path> polkulista = new LinkedList<Path>();
        
        for(Path p : alku.Naapurit){
            evalvoi(p);
            lisaaListalle(polkulista, p);
        }
        
        Path haettava, maali = null;
        
        while(!polkulista.isEmpty()){
            haettava = polkulista.removeFirst();
            
            //System.out.println(haettava.b.nimi);
            
            if(haettava.b.nimi == 0){
                maali = haettava;
                break;
            }
            
            for(Path p : haettava.b.Naapurit){
                if(!katsotut.contains(p)){
                    katsotut.add(p);
                    evalvoi(p);
                    lisaaListalle(polkulista, p);
                    if(p.b != alku)
                        p.prev = haettava;
                }
            }
        }
        
        Path print = maali;
        int kulku = 0;
        
        while(print.prev != null){
            //System.out.print(print.b.nimi + " " + print.a.nimi + "; ");
            tulos.addLast(print);
            kulku += print.length;
            print = print.prev;
        }
        
        return kulku;
    }
}
