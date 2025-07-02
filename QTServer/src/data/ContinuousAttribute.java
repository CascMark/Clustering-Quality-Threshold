package data;

import java.io.Serializable;

public class ContinuousAttribute extends Attribute implements Serializable {

    protected double max;
    protected double min;

    ContinuousAttribute(String name, int index, double min, double max){
        super(name, index);
        this.max = max;
        this.min = min;
    }

    public double getScaledValue(double v){
        return (v-min)/(max-min);
    }

}