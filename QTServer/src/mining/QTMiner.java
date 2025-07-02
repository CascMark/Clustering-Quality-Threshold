package mining;
import data.*;

import java.io.*;
import java.util.Iterator;

/**
 * Implementazione dell'algoritmo QT per il clustering.
 */
public class QTMiner implements Serializable {

    public ClusterSet C;
    public double radius;

    /**
     * Costruttore che carica un oggetto ClusterSet da file.
     * @param fileName nome del file da cui leggere il ClusterSet
     * @throws FileNotFoundException se il file non viene trovato
     * @throws IOException se si verifica un errore di I/O
     * @throws ClassNotFoundException se la classe del ClusterSet non viene trovata
     */
    public QTMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream inFile = new FileInputStream(fileName);
        ObjectInputStream InStream = new ObjectInputStream(inFile);
        this.C = (ClusterSet) InStream.readObject();
        InStream.close();
        inFile.close();
    }

    /**
     * Costruttore che inizializza un nuovo oggetto QTMiner con un dato raggio.
     * @param radius raggio massimo per includere elementi in un cluster
     */
    public QTMiner(double radius){
        C = new ClusterSet();
        this.radius = radius;
    }

    /**
     * Salva l'oggetto ClusterSet su file.
     * @param fileName nome del file su cui salvare
     * @throws FileNotFoundException se il file non viene trovato
     * @throws IOException se si verifica un errore di I/O
     */
    public void salva(String fileName) throws FileNotFoundException, IOException{
        FileOutputStream OutFile = new FileOutputStream(fileName);
        ObjectOutputStream OutStream = new ObjectOutputStream(OutFile);
        OutStream.writeObject(C);
        OutStream.close();
    }

    /**
     * Restituisce il ClusterSet associato.
     * @return oggetto ClusterSet
     */
    public ClusterSet getC(){ return C; }

    /**
     * Costruisce un cluster candidato scegliendo come centroide una tupla non ancora clusterizzata.
     * @param data insieme di dati da cui costruire i cluster
     * @param isClustered array che indica se una tupla è già clusterizzata
     * @return cluster candidato con il numero massimo di tuple
     */
    private Cluster buildCandidateCluster(Data data, boolean isClustered[]){
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

    /**
     * Esegue l'algoritmo di clustering QT sull'insieme di dati fornito.
     * @param data insieme di dati su cui effettuare il clustering
     * @return numero di cluster ottenuti
     * @throws ClusteringRadiusException se viene generato un solo cluster
     * @throws EmptyDatasetException se l'insieme di dati è vuoto
     */
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
