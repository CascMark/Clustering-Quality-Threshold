package data;

import java.io.Serializable;

/**
 * Classe concreta che modella un attributo continuo (numerico), estende la classe attribute
 */
public class ContinuousAttribute extends Attribute implements Serializable {

    protected double max;
    protected double min;

    /**
     * Costruttore della classe ContinuousAttribute, invoca il costruttore
     * della classe madre e inizializza i membri aggiunti per estensione
     * @param name nome dell'oggetto attributo continuo
     * @param index identificativo numerico dell'oggetto attributo continuo
     * @param min estremo minimo dell'intervallo di valori che l'attributo può realmente assumere nel dataset
     * @param max estremo massimo dell'intervallo di valori che l'attributo può realmente assumere nel dataset
     */
    ContinuousAttribute(String name, int index, double min, double max){
        super(name, index);
        this.max = max;
        this.min = min;
    }

    /**
     * Calcola e restituisce il valore scalato del parametro
     * passato in input. Lo scaling ha come codominio l’intervallo [0,1].
     * @param v valore dell'attributo da scalare
     * @return scaling di v
     */
    public double getScaledValue(double v){
        return (v-min)/(max-min);
    }

}