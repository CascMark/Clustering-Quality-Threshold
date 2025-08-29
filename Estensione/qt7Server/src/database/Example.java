package database;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un singolo esempio (tupla) all'interno di una tabella.
 * Gli attributi dell'esempio sono memorizzati in una lista di oggetti.
 */
public class Example implements Comparable<Example> {

    private List<Object> example = new ArrayList<Object>();

    /**
     * Aggiunge un valore all'esempio.
     * @param o valore da aggiungere
     */
    public void add(Object o) {
        example.add(o);
    }

    /**
     * Restituisce il valore dell'attributo all'indice specificato.
     * @param i indice dell'attributo
     * @return valore dell'attributo
     */
    public Object get(int i) {
        return example.get(i);
    }

    /**
     * Confronta l'esempio corrente con un altro esempio.
     * Il confronto viene effettuato in base all'ordine degli attributi
     * e assume che gli oggetti siano comparabili.
     * @param ex esempio da confrontare
     * @return un intero negativo, zero o positivo in base al risultato del confronto
     */
    public int compareTo(Example ex) {
        int i = 0;
        for (Object o : ex.example) {
            if (!o.equals(this.example.get(i)))
                return ((Comparable) o).compareTo(example.get(i));
            i++;
        }
        return 0;
    }

    /**
     * Restituisce una rappresentazione in formato stringa dell'esempio.
     * @return stringa contenente tutti i valori separati da spazio
     */
    public String toString() {
        String str = "";
        for (Object o : example)
            str += o.toString() + " ";
        return str;
    }

}
