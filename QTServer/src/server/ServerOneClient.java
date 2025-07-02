package server;

import data.Data;
import data.EmptyDatasetException;
import database.DatabaseConnectionException;
import database.EmptySetException;
import mining.ClusteringRadiusException;
import mining.QTMiner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

public class ServerOneClient extends Thread{

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private QTMiner kmeans;
    private Data data;

    ServerOneClient(Socket s) throws IOException {
        this.socket = s;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.start();
    }

    @Override
    public void run(){
        try{
            while(true){
                int scelta = (int) in.readObject();

                switch(scelta){
                    case 0:
                        scopriCluster();
                        break;
                    case 1:
                        leggiCluster();
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

    public void scopriCluster() throws IOException, ClassNotFoundException {
        String tableName = (String) in.readObject();
        double radius = (double) in.readObject();
        try {
            this.data = new Data(tableName);
            this.kmeans = new QTMiner(radius);
            int numClusters = kmeans.compute(data);
            out.writeObject("OK");
            out.writeObject(kmeans.getC().toString(data));
            String fileName = (String) in.readObject();
            kmeans.salva(fileName);
        } catch (EmptyDatasetException ede) {
            System.out.println("Dataset vuoto: " + ede.getMessage());
        } catch (ClusteringRadiusException cre) {
            System.out.println("Raggio di clustering non valido: " + cre.getMessage());
        } catch (DatabaseConnectionException dce) {
            System.out.println("Errore connessione database: " + dce.getMessage());
        } catch (EmptySetException ese) {
            System.out.println("Set di dati vuoto: " + ese.getMessage());
        } catch (IOException ioe) {
            System.out.println("Errore I/O: " + ioe.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Impossibile trovare la classe specificata: " + cnfe.getMessage());
        } catch (SQLException sqle){
            System.out.println("Errore nel caricamento del database: " + sqle.getMessage());
        }
    }

    public void leggiCluster() throws FileNotFoundException, ClassNotFoundException, IOException {
        String fileName = (String) in.readObject();
        String tableName = (String) in.readObject();

        try {
            this.kmeans = new QTMiner(fileName);
            this.data = new Data(tableName);
            out.writeObject("OK");
            out.writeObject(kmeans.getC().toString(data));
        } catch (FileNotFoundException e) {
            out.writeObject("File non trovato: " + e.getMessage());
        } catch (DatabaseConnectionException e) {
            out.writeObject("Errore connessione database: " + e.getMessage());
        } catch (EmptySetException e) {
            out.writeObject("Set di dati vuoto: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            out.writeObject("Errore nel caricamento: " + e.getMessage());
        } catch (SQLException sqle){
            System.out.println("Errore nel caricamento del database: " + sqle.getMessage());
        }
    }
}
