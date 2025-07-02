package data;

import java.io.Serializable;

/**
 * Classe concreta che estende la classe Item e modella
 * una coppia Attributo continuo - valore numerico
 */
public class ContinuousItem extends Item implements Serializable {

    /**
     * Richiama il costruttore della superclasse
     * @param attribute attributo coinvolto nell'item
     * @param value valore assegnato all'attributo
     */
    ContinuousItem(ContinuousAttribute attribute, Double value){
        super(attribute, value);
    }

    /**
     * Determina la distanza (in valore assoluto) tra il valore
     * scalato memorizzato nello item corrente (this.getValue()) e quello
     * scalato associato al parametro a.
     * @param a valore scalato
     * @return
     */
    @Override
    public double distance(Object a){
        ContinuousItem other = (ContinuousItem) a;
        ContinuousAttribute attr = (ContinuousAttribute) this.getAttribute();
        double cur_value = (Double) this.getValue();
        double other_value = (Double) other.getValue();
        double cur_scaled_value = attr.getScaledValue(cur_value);
        double other_scaled_value = attr.getScaledValue(other_value);
        return Math.abs(cur_scaled_value - other_scaled_value);
    }
}
