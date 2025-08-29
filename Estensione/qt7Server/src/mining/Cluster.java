package mining;
import data.Data;
import data.EmptyDatasetException;
import data.Tuple;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Classe che modella un cluster, cioè un insieme di tuple raggruppate attorno a un centroide.
 * Ogni cluster mantiene il proprio centroide e l'insieme degli ID delle tuple che vi appartengono.
 */
public class Cluster implements Iterable<Integer>, Comparable<Cluster>, Serializable {

    private Tuple centroid;
    private HashSet<Integer> clusteredData;

    /**
     * Costruttore che inizializza il cluster con un centroide.
     * @param centroid tupla che rappresenta il centroide iniziale del cluster
     */
    Cluster(Tuple centroid) {
        this.centroid = centroid;
        this.clusteredData = new HashSet<>();
    }

    /**
     * Restituisce il centroide del cluster.
     * @return centroide come oggetto Tuple
     */
    Tuple getCentroid() {
        return centroid;
    }

    /**
     * Aggiunge l'ID di una tupla al cluster.
     * @param id indice della tupla da aggiungere
     * @return true se l'inserimento è avvenuto con successo (la tupla non era già presente), false altrimenti
     */
    boolean addData(int id) {
        return clusteredData.add(id);
    }

    /**
     * Verifica se una tupla (tramite ID) è contenuta nel cluster.
     * @param id ID della tupla da cercare
     * @return {@code true} se la tupla è presente nel cluster, false altrimenti
     */
    boolean contain(int id) {
        return clusteredData.contains(id);
    }

    /**
     * Rimuove una tupla dal cluster (tramite il suo ID).
     * @param id ID della tupla da rimuovere
     */
    void removeTuple(int id) {
        clusteredData.remove(id);
    }

    /**
     * Restituisce il numero di tuple assegnate al cluster.
     * @return dimensione del cluster
     */
    public int getSize() {
        return clusteredData.size();
    }

    /**
     * Restituisce un iteratore sugli ID delle tuple contenute nel cluster.
     * @return iteratore sugli ID
     */
    @Override
    public Iterator<Integer> iterator() {
        return clusteredData.iterator();
    }

    /**
     * Restituisce una stringa con la rappresentazione del centroide.
     * @return stringa rappresentante il centroide
     */
    @Override
    public String toString() {
        String s = "Centroid=(";
        for (int i = 0; i < centroid.getLength(); i++) {
            s += centroid.get(i);
            if (i < centroid.getLength() - 1) {
                s += ", ";
            }
        }
        s += ")";
        s += "\n";
        return s;
    }

    /**
     * Restituisce una rappresentazione testuale del cluster che include:
     * il centroide, tutte le tuple assegnate al cluster e la distanza media da esse al centroide.
     * @param data oggetto Data contenente tutte le tuple
     * @return descrizione testuale del cluster
     */
    public String toString(Data data) {
        String str = "Centroid=(";
        for (int i = 0; i < centroid.getLength(); i++) {
            str += centroid.get(i) + " ";
        }
        str += ")\nExamples:\n";
        try {
            for (Integer id : clusteredData) {
                str += "[";
                for (int j = 0; j < data.getNumberOfAttributes(); j++) {
                    str += data.getAttributeValue(id, j) + " ";
                }
                str += "] dist=" + getCentroid().getDistance(data.getItemSet(id)) + "\n";
            }
            str += "\nAvgDistance=" + getCentroid().avgDistance(data, clusteredData);
        } catch (EmptyDatasetException ede) {
            System.out.println("Empty dataset!");
        }
        return str;
    }

    /**
     * Confronta due cluster in base alla dimensione (numero di tuple).
     * @param cluster cluster da confrontare
     * @return 1 se questo cluster è più grande, -1 se più piccolo, 0 se uguale
     */
    @Override
    public int compareTo(Cluster cluster) {
        return Integer.compare(this.clusteredData.size(), cluster.getSize());
    }
}
