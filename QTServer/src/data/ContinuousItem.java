package data;

import java.io.Serializable;

public class ContinuousItem extends Item implements Serializable {

    ContinuousItem(ContinuousAttribute attribute, Double value){
        super(attribute, value);
    }

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
