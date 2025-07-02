package data;
import java.io.Serializable;

abstract class Item implements Serializable {

    protected Attribute attribute;
    protected Object value;

    Item(Attribute attribute, Object value){
        this.attribute = attribute;
        this.value = value;
    }

    public Attribute getAttribute(){ return attribute; }

    public Object getValue(){ return value; }

    @Override
    public String toString(){ return value.toString(); }

    abstract double distance(Object a);

}
