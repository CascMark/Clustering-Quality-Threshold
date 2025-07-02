package data;
import java.io.Serializable;

/**
 * Classe astratta che modella un generico Item
 * (coppia attributo - valore)
 */
public abstract class Item implements Serializable {

    private Attribute attribute;
    private Object value;

    /**
     * Inizializza i valori dei membri attributi
     * @param attribute attributo coinvolto nell'item
     * @param value valore assegnato all'attributo
     */
    Item(Attribute attribute, Object value){
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Restituisce attribute
     * @return attribute
     */
    public Attribute getAttribute(){ return attribute; }

    /**
     * Restituisce value
     * @return value
     */
    public Object getValue(){ return value; }

    /**
     * Restituisce una stringa rappresentante lo stato dell'oggetto
     * @return value
     */
    @Override
    public String toString(){ return value.toString(); }

    /**
     * Metodo astratto, l'implementazione varia tra Item discreto e continuo
     * @param a
     * @return
     */
    abstract double distance(Object a);

}
