package data;

import java.io.Serializable;

/**
 * Classe concreta che estende la classe Item, rappresenta una coppia
 * <Attributo discreto>-<valore discreto>
 */
public class DiscreteItem extends Item implements Serializable {

    /**
     * Invoca il costruttore della classe madre
     * @param attribute attributo coinvolto nell'item
     * @param value valore assegnato all'attributo
     */
    DiscreteItem(DiscreteAttribute attribute, String value){
        super(attribute, value);
    }

    /**
     * Restituisce 0 se (getValue().equals(a)), 1 altrimenti
     * @param a oggetto da confrontare
     * @return 1 oppure 0
     */
    public double distance(Object a) {
        if (a instanceof Item) {
            if (getValue().equals(((Item) a).getValue())){
                return 0;
            }
        }
        return 1;
    }
}
