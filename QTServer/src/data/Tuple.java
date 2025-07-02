package data;
import java.io.Serializable;
import java.util.Set;
import java.util.Iterator;
import java.util.Arrays;

public class Tuple implements Iterable<Item>, Serializable{

    public Item[] tuple;

    Tuple(int size){
        tuple = new Item[size];
    }

    public int getLength(){ return tuple.length; }

    public Item get(int index) { return tuple[index]; }

    public void add(Item c, int i){ tuple[i] = c;}

    public double getDistance(Tuple obj){
        double distance_value = 0;
        for(int i = 0; i < this.tuple.length; i++){
            distance_value = distance_value + this.tuple[i].distance(obj.tuple[i]);
        }
        return distance_value;
    }

    @Override
    public Iterator<Item> iterator(){
        return Arrays.asList(tuple).iterator();
    }

    public double avgDistance(Data data, Set<Integer> clusteredData){
        double p = 0.0;
        double sumD = 0.0;
        Iterator<Integer> it = clusteredData.iterator();
        while(it.hasNext()) {
            int id = it.next();
            double d = getDistance(data.getItemSet(id));
            sumD += d;
        }
        p = sumD / clusteredData.size();
        return p;
    }
}