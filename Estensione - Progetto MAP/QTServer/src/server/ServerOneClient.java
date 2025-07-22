package server;

import data.Data;
import data.EmptyDatasetException;
import database.DatabaseConnectionException;
import database.EmptySetException;
import mining.ClusteringRadiusException;
import mining.QTMiner;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Classe che gestisce un singolo client connesso al server.
 * Estende la classe Thread e permette la gestione concorrente di più client.
 */
public class ServerOneClient extends Thread{

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private QTMiner kmeans;
    private Data data;

    /**
     * Costruttore di classe. Inizializza gli attributi socket, in e out.
     * Avvia il thread.
     * @param s socket del client
     * @throws IOException IOException se si verifica un errore di I/O nella creazione degli stream
     */
    ServerOneClient(Socket s) throws IOException {
        this.socket = s;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.start();
    }

    /**
     * Riscrive il metodo run della superclasse Thread al fine di gestire le richieste del client.
     */
    @Override
    public void run(){
        try{
            while(true){
                int scelta = (int) in.readObject();

                switch(scelta){
                    case 0:
                        storeTableFromDb();
                        break;
                    case 1:
                        learningFromDbTable();
                        break;
                    case 2:
                        storeClusterInFile();
                        break;
                    case 3:
                        learningFromFile();
                        break;
                    default:
                        System.out.println("Scelta non valida");
                }
            }
        }catch(IOException ioe){
            System.out.println("Errore " + ioe.getMessage());
        }
        catch(ClassNotFoundException cnfe){
            System.out.println("Errore, classe non trovata" + cnfe.getMessage());
        }
        finally {
            try{
                socket.close();
                in.close();
                out.close();
            }catch(IOException ioe){
                System.out.println("Errore nella chisura del socket o degli ObjectStream" + ioe.getMessage());
            }
        }
    }

    /**
     * Metodo che carica i dati da una tabella del database.
     * Corrisponde al caso 0 del client (storeTableFromDb).
     * @throws IOException se si verifica un errore di input/output
     * @throws ClassNotFoundException se il formato degli oggetti ricevuti non è valido
     */
    public void storeTableFromDb() throws IOException, ClassNotFoundException {
        String tableName = (String) in.readObject();

        try {
            this.data = new Data(tableName);
            out.writeObject("OK");
        } catch (DatabaseConnectionException dce) {
            out.writeObject("Errore connessione database: " + dce.getMessage());
        } catch (EmptySetException ese) {
            out.writeObject("Set di dati vuoto: " + ese.getMessage());
        } catch (SQLException sqle) {
            out.writeObject("Errore nel caricamento del database: " + sqle.getMessage());
        } catch (Exception e) {
            out.writeObject("Errore generico: " + e.getMessage());
        }
    }

    /**
     * Metodo che esegue il clustering sui dati caricati dal database.
     * Corrisponde al caso 1 del client (learningFromDbTable).
     * @throws IOException se si verifica un errore di input/output
     * @throws ClassNotFoundException se il formato degli oggetti ricevuti non è valido
     */
    public void learningFromDbTable() throws IOException, ClassNotFoundException {
        double radius = (double) in.readObject();

        try {
            if (this.data == null) {
                out.writeObject("Errore: nessun dato caricato. Eseguire prima il caricamento da database.");
                return;
            }

            this.kmeans = new QTMiner(radius);
            int numClusters = kmeans.compute(data);

            out.writeObject("OK");
            out.writeObject(numClusters);
            out.writeObject(kmeans.getC().toString(data));

        } catch (ClusteringRadiusException cre) {
            out.writeObject("Raggio di clustering non valido: " + cre.getMessage());
        } catch (EmptyDatasetException ede) {
            out.writeObject("Dataset vuoto: " + ede.getMessage());
        } catch (Exception e) {
            out.writeObject("Errore nel clustering: " + e.getMessage());
        }
    }

    /**
     * Metodo che salva i cluster in un file.
     * Corrisponde al caso 2 del client (storeClusterInFile).
     * @throws IOException se si verifica un errore di input/output
     * @throws ClassNotFoundException se il formato degli oggetti ricevuti non è valido
     */
    public void storeClusterInFile() throws IOException, ClassNotFoundException {
        try {
            if (this.kmeans == null) {
                out.writeObject("Errore: nessun clustering eseguito. Eseguire prima il clustering.");
                return;
            }

            // Genera un nome file automatico o usa un nome predefinito
            String fileName = "cluster_" + System.currentTimeMillis() + ".dat";
            kmeans.salva(fileName);

            out.writeObject("OK");

        } catch (IOException ioe) {
            out.writeObject("Errore nel salvataggio del file: " + ioe.getMessage());
        } catch (Exception e) {
            out.writeObject("Errore generico nel salvataggio: " + e.getMessage());
        }
    }

    /**
     * Metodo che esegue il clustering direttamente da file.
     * Corrisponde al caso 3 del client (learningFromFile).
     * @throws IOException se si verifica un errore di input/output
     * @throws ClassNotFoundException se il formato degli oggetti ricevuti non è valido
     */
    public void learningFromFile() throws IOException, ClassNotFoundException {
        String tableName = (String) in.readObject();
        double radius = (double) in.readObject();

        try {
            this.data = new Data(tableName);
            this.kmeans = new QTMiner(radius);
            int numClusters = kmeans.compute(data);

            out.writeObject("OK");
            out.writeObject(kmeans.getC().toString(data));

        } catch (EmptyDatasetException ede) {
            out.writeObject("Dataset vuoto: " + ede.getMessage());
        } catch (ClusteringRadiusException cre) {
            out.writeObject("Raggio di clustering non valido: " + cre.getMessage());
        } catch (DatabaseConnectionException dce) {
            out.writeObject("Errore connessione database: " + dce.getMessage());
        } catch (EmptySetException ese) {
            out.writeObject("Set di dati vuoto: " + ese.getMessage());
        } catch (SQLException sqle) {
            out.writeObject("Errore nel caricamento del database: " + sqle.getMessage());
        } catch (Exception e) {
            out.writeObject("Errore generico: " + e.getMessage());
        }
    }
}