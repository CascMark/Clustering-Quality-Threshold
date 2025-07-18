package data;
import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Classe concreta che estende la classe Attribute e rappresenta
 * un attributo discreto (categorico)
 */
public class DiscreteAttribute extends Attribute implements Iterable<String>, Serializable{

    private TreeSet<String> values;

    /**
     * Invoca il costruttore della classe madre e
     * inizializza il membro values con il parametro in input.
     * @param name nome dell'attributo
     * @param index dentificativo numerico dell'attributo
     * @param values  array di stringhe rappresentanti il dominio dell'attributo
     */
    public DiscreteAttribute(String name, int index, String values[]){
        super(name, index);
        this.values = new TreeSet<String>();
        for(String value : values){
            this.values.add(value);
        }
    }

    /**
     * Restituisce un iteratore sui valori del dominio dell'attributo discreto.
     * L'iterazione segue l'ordine naturale (alfabetico) dei valori.
     *
     * @return un oggetto Iterator<String> per iterare sui valori
     */
    @Override
    public Iterator<String> iterator(){
        return values.iterator();
    }

    /**
     *
     * @return restituisce la dimensione di values
     */
    public int getNumberOfDistinctValues(){ return values.size(); }

}