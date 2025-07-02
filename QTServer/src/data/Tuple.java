package data;
import java.io.Serializable;
import java.util.Set;
import java.util.Iterator;
import java.util.Arrays;

/**
 * Classe che rappresenta una tupla come sequenza di
 * coppie attributo-valore
 */
public class Tuple implements Iterable<Item>, Serializable{

    private Item[] tuple;

    /**
     * Costruisce  l'oggetto riferito da tuple
     * @param size numero di item che costituirà la tupla
     */
    Tuple(int size){
        tuple = new Item[size];
    }

    /**
     * Restituisce la lughezza di tuple
     * @return tuple.length
     */
    public int getLength(){ return tuple.length; }

    /**
     * Restituisce l'item in posizione index
     * @param index posizione
     * @return Item
     */
    public Item get(int index) { return tuple[index]; }

    /**
     * Memorizza c in tuple[i]
     * @param c oggetto Item da memorizzare
     * @param i posizione in cui memorizzare c all'interno di tuple
     */
    public void add(Item c, int i){ tuple[i] = c;}

    /**
     * determina la distanza tra la tupla riferita da obj e la
     * tupla corrente (riferita da this). La distanza è ottenuta come la somma delle
     * distanze tra gli item in posizioni eguali nelle due tuple.
     * @param obj oggetto da confrontare
     * @return distanza
     */
    public double getDistance(Tuple obj){
        double distance_value = 0;
        for(int i = 0; i < this.tuple.length; i++){
            distance_value = distance_value + this.tuple[i].distance(obj.tuple[i]);
        }
        return distance_value;
    }

    /**
     * Restituisce un iteratore sugli item della tupla.
     * @return iteratore sugli oggetti {@link Item} della tupla
     */
    @Override
    public Iterator<Item> iterator(){
        return Arrays.asList(tuple).iterator();
    }

    /**
     * Comportamento:
     * restituisce la media delle distanze tra la tupla
     * corrente e quelle ottenibili dalle righe della matrice in data aventi indice  in
     * clusteredData.
     * @param data oggetto Data contenente tutte le tuple
     * @param clusteredData insieme di indici delle tuple su cui calcolare la media
     * @return distanza media tra la tupla corrente e quelle specificate
     */
    public double avgDistance(Data data, Set<Integer> clusteredData){
        double p = 0.0;
        double sumD = 0.0;
        Iterator<Integer> it = clusteredData.iterator();
        while(it.hasNext()) {
            int id = it.next();
            double d = getDistance(data.getItemSet(id));
            sumD += d;
        }
        p = sumD / clusteredData.size();
        return p;
    }
}