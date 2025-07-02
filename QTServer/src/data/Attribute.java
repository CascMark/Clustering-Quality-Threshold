package data;

import java.io.Serializable;

/**
 * Classe astratta che modella l'entità attributo
 */
public abstract class Attribute implements Serializable {

    protected String name;
    protected int index;

    /**
     * Crea un oggetto Attributo assegnando il nome e l'indice passati come argomento
     * @param name nome dell'attributo
     * @param index identificativo numerico dell'attributo
     */
    Attribute(String name, int index){
        this.name = name;
        this.index = index;
    }

    /**
     * Restituisce il nome dell'oggetto Attributo
     * @return nome dell'oggetto Attributo
     */
    protected String getName(){ return name; }

    /**
     * Restituisce l'identificativo numerico dell'oggetto Attributo
     * @return
     */
    protected int getIndex(){ return index; }

    /**
     * Restituisce una stringa rappresentante lo stato dell'oggetto
     * @return nome dell'oggetto Attributo
     */
    @Override
    public String toString(){ return name; }

}