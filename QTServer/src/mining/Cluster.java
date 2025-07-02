package mining;
import data.Data;
import data.EmptyDatasetException;
import data.Tuple;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

public class Cluster implements Iterable<Integer>, Comparable<Cluster>, Serializable {

	private Tuple centroid;
	private HashSet<Integer> clusteredData;

	Cluster(Tuple centroid){
		this.centroid=centroid;
		this.clusteredData = new HashSet<Integer>();
	}
		
	Tuple getCentroid(){
		return centroid;
	}
	
	//return true if the tuple is changing cluster
	boolean addData(int id){
		return clusteredData.add(id);
		
	}
	
	//verifica se una transazione � clusterizzata nell'array corrente
	boolean contain(int id){
		return clusteredData.contains(id);
	}
	

	//remove the tuplethat has changed the cluster
	void removeTuple(int id){
		clusteredData.remove(id);
	}

	public int  getSize(){
		return clusteredData.size();
	}

	@Override
	public Iterator<Integer> iterator(){
		return clusteredData.iterator();
	}

	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i);
		str+=")";
		return str;
		
	}

	public String toString(Data data){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i)+ " ";
		str+=")\nExamples:\n";
		try {
			for (Integer id : clusteredData) {
				str += "[";
				for (int j = 0; j < data.getNumberOfAttributes(); j++)
					str += data.getAttributeValue(id, j) + " ";
				str += "] dist=" + getCentroid().getDistance(data.getItemSet(id)) + "\n";
			}
			Integer[] array = clusteredData.toArray(new Integer[0]);
			int[] intArray = new int[array.length];
			for(int i = 0; i < array.length; i++) {
				intArray[i] = array[i];
			}
			str += "\nAvgDistance=" + getCentroid().avgDistance(data, clusteredData);
		}
		catch(EmptyDatasetException ede){
			System.out.println("Empty dataset!");
		}
		return str;
	}

	@Override
	public int compareTo(Cluster cluster){
		if(this.clusteredData.size() > cluster.getSize()){ return 1;}
		if(this.clusteredData.size() < cluster.getSize()){ return -1;}
		return 0;
	}

}
