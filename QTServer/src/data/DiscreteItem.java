package data;

import java.io.Serializable;

public class DiscreteItem extends Item implements Serializable {

    DiscreteItem(DiscreteAttribute attribute, String value){
        super(attribute, value);
    }

    public double distance(Object a) {
        if (a instanceof Item) {
            if (getValue().equals(((Item) a).getValue())){
                return 0;
            }
        }
        return 1;
    }
}
