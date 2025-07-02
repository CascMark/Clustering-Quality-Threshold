package data;
import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

public class DiscreteAttribute extends Attribute implements Iterable<String>, Serializable{

    public TreeSet<String> values;

    DiscreteAttribute(String name, int index, String values[]){
        super(name, index);
        this.values = new TreeSet<String>();
        for(String value : values){
            this.values.add(value);
        }
    }

    @Override
    public Iterator<String> iterator(){
        return values.iterator();
    }

    public int getNumberOfDistinctValues(){ return values.size(); }

}