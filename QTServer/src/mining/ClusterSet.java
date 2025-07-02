package mining;
import data.Data;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

public class ClusterSet implements Iterable<Cluster>, Serializable {

    private Set<Cluster> C = new TreeSet<Cluster>();

    ClusterSet(){}

    public void add(Cluster c){
       C.add(c);
    }

    public Iterator<Cluster> iterator(){
        return C.iterator();
    }

    @Override
    public String toString(){
        StringBuilder centroid_string = new StringBuilder();
        Iterator<Cluster> iterator = C.iterator();
        while(iterator.hasNext()){
            centroid_string.append(iterator.next());
            if(iterator.hasNext()) {
                centroid_string.append(", ");
            }
        }
        return centroid_string.toString();
    }

    public String toString(Data data){
        StringBuilder str = new StringBuilder();
        int index = 0;
        for(Cluster cluster : C){
            if (cluster != null){
                str.append(index).append(":").append(cluster.toString(data)).append("\n");
            }
            index++;
        }
        return str.toString();
    }

}
