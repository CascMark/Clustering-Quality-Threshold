package mining;
import data.*;

import java.io.*;
import java.util.Iterator;
public class QTMiner implements Serializable {

    public ClusterSet C;
    public double radius;

    public QTMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream inFile = new FileInputStream(fileName);
        ObjectInputStream InStream = new ObjectInputStream(inFile);
        this.C = (ClusterSet) InStream.readObject();
        InStream.close();
        inFile.close();
    }

    public QTMiner(double radius){
        C = new ClusterSet();
        this.radius = radius;
    }

    public void salva(String fileName) throws FileNotFoundException, IOException{
        FileOutputStream OutFile = new FileOutputStream(fileName);
        ObjectOutputStream OutStream = new ObjectOutputStream(OutFile);
        OutStream.writeObject(C);
        OutStream.close();
    }

    public ClusterSet getC(){ return C; }

    protected Cluster buildCandidateCluster(Data data, boolean isClustered[]){
        Cluster CandidateCluster = null;
        int maxSize = 0;
        for(int i = 0; i < data.getNumberOfExamples(); i++) {
            if(!isClustered[i]){
                Tuple centroid = data.getItemSet(i);
                Cluster candidateCluster = new Cluster(centroid);
                candidateCluster.addData(i);
                for(int j = 0; j < data.getNumberOfExamples(); j++) {
                    if(!isClustered[j] && i != j) {
                        Tuple otherTuple = data.getItemSet(j);
                        double distance = centroid.getDistance(otherTuple);
                        if(distance <= radius) {
                            candidateCluster.addData(j);
                        }
                    }
                }
                int currentSize = candidateCluster.getSize();
                if(currentSize > maxSize) {
                    maxSize = currentSize;
                    CandidateCluster = candidateCluster;
                }
            }
        }
        return CandidateCluster;
    }

    public int compute(Data data) throws ClusteringRadiusException, EmptyDatasetException{
        if(data.getNumberOfExamples() == 0){
            throw new EmptyDatasetException();
        }
        int numclusters=0;
        boolean isClustered[]=new boolean[data.getNumberOfExamples()];
        for(int i=0;i<isClustered.length;i++)
            isClustered[i]=false;
        int countClustered=0;
        while(countClustered!=data.getNumberOfExamples())
        {
            Cluster c = buildCandidateCluster(data, isClustered);
            C.add(c);
            numclusters++;
            Iterator<Integer> clusteredTupleId = c.iterator();
            while(clusteredTupleId.hasNext()) {
                int id = clusteredTupleId.next();
                isClustered[id] = true;
            }
            countClustered += c.getSize();
        }
        if(numclusters == 1){
            throw new ClusteringRadiusException();
        }
        return numclusters;
    }
}
