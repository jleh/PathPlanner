/*
 * Reitin laskenta
 * 
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
    
    
    public Tulos calculateRoute(ArrayList<Kaupunki> lista, Tulos paras){
        //Reittiä etsitään ensin satunnaisesti ja kunnes kaikissa paikoissa on
        //käyty, etsitään lyhin reitti takaisin lähtöpaikkaan A*-haulla
        int matka = 0;
        int loppu = 0;
        edellinen = null;
        reitti.clear();
        vieraillut.clear();
        katsotut.clear();
        solmulista.clear();
        alustaKaupungit(lista);
        Kaupunki k = lista.get(0);
        for(Path pa : k.Naapurit){
            solmulista.add(pa);
        }
        
        reitti.add(k);
        
        Kaupunki viimeisin = k;
        
    //Hakualgoritmi
        while(true){
            Collections.shuffle(solmulista);
            Path p = solmulista.getFirst();
            boolean ekavaihe = false;
            
            for(Path pt : solmulista){ //Mennään ensin lähimpään käymättömään paikkaan
                if(!vieraillut.contains(pt.b) && pt.length < p.length){
                    p = pt;
                    ekavaihe = true;
                }
            }
            
            if(ekavaihe == false){ //Jos äsken ei löytynyt paikkaa niin otetaan randomilla
                //Collections.shuffle(solmulista);
                p = solmulista.getFirst();

                if(vieraillut.contains(p.b) || p.b == viimeisin) { 
                    Collections.shuffle(solmulista);
                    p = solmulista.removeFirst();
                }

                Path p2 = solmulista.removeFirst();
                if(!vieraillut.contains(p2.b) || p2.b != viimeisin && p2.length < p.length)
                    p = p2;
            }
            
            Kaupunki kaupunki = p.b;
            matka += p.length;
            
            reitti.addLast(kaupunki);
            
            //System.out.println(kaupunki.nimi + " " + matka);
            if(!vieraillut.contains(kaupunki))
                vieraillut.add(kaupunki);
                
            solmulista.clear();
            
            for(Path pa : kaupunki.Naapurit)
                solmulista.add(pa);
            
            if(vieraillut.size() == lista.size() && kaupunki.nimi == 0){
                //vika = kaupunki;
                loppu = AStar(kaupunki, reitti); //Haetaan optimaalinen reitti loppuun
                break;
            }
        }
               
      //  while(!tulos.isEmpty()){
      //      reitti.addLast(tulos.removeLast().b);
      //  }
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
    
    private void alustaKaupungit(ArrayList<Kaupunki> lista){
        for(Kaupunki k : lista){
            k.visited = false;
            //k.vierailut = 0;
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
    
    
    public Kaupunki kehitys(Tulos paras, Kaupunki viime){
        //Poistetaan turhia kiertoja kaupunkien välillä
        LinkedList<Kaupunki> kaupunkilista = new LinkedList<Kaupunki>();
        
        //Alustus
        for(Kaupunki k : paras.reitti){
            k.vierailut = 0;
            if(!kaupunkilista.contains(k))
                kaupunkilista.add(k);
        }
        
        for(Kaupunki k : paras.reitti){
            k.vierailut++;
        }
        
        
        int suurin = 0;
        Kaupunki eniten = null;
        for(Kaupunki k : kaupunkilista){
            if(k.vierailut > suurin && k != viime){
                eniten = k;
                suurin = k.vierailut;
            }
            //System.out.println(k.nimi + " " + k.vierailut);
        }
        //System.out.println(eniten.nimi + " " + eniten.vierailut + " " + eniten.Naapurit.size());
        
        int p = 0;
        for(int i = 1; i < paras.reitti.size()-2; i++){
            if(paras.reitti.get(i) == eniten){
                //System.out.println(paras.reitti.get(i-1).nimi + " " + paras.reitti.get(i+1).nimi);
                if(onkoNaapureita(paras.reitti.get(i-1), paras.reitti.get(i+1))){
                    if(valimatka(paras.reitti.get(i-1), paras.reitti.get(i+1)) < 
                            valimatka(paras.reitti.get(i-1), paras.reitti.get(i)) + valimatka(paras.reitti.get(i), paras.reitti.get(i+1))){
                        paras.reitti.remove(i);
                        if(!paras.reitti.contains(eniten))
                            paras.reitti.add(i, eniten);
                    }
                }
            }
        }
        
        return eniten;
    }
    
    private boolean onkoNaapureita(Kaupunki a, Kaupunki b){
        for(Path p : a.Naapurit){
            if(p.b == b){
                return true;
            }
        }
        return false;
    }
    
    private int valimatka(Kaupunki a, Kaupunki b){
        for(Path p : a.Naapurit){
            if(p.b == b)
                return p.length;
        }
        return Integer.MAX_VALUE;
    }
}
