package default_package;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client per la comunicazione con il server del clustering Quality Threshold (QT).
 * Permette di inviare richieste al server per apprendere dai dati presenti
 * nel database o nei file, salvare cluster e tabelle su file, e ricevere
 * i risultati del clustering.
 */
public class ClusClient {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;

    /**
     * Crea un client che stabilisce la connessione all'indirizzo ip e alla porta specificata.
     * @param ip  indirizzo ip del server
     * @param port porta su cui il server è in ascolto
     * @throws IOException eccezione in caso di errore di connessione
     */
    public ClusClient(String ip, int port) throws IOException{
        InetAddress addr = InetAddress.getByName(ip);
        System.out.println("addr = " + addr);
        socket = new Socket(addr, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Metodo che gestisce l'apprendimento dei dati da database, specificando il raggio di clustering
     * @param radius raggio di clustering
     * @return stringa rappresentante il risultato del clustering
     * @throws IOException eccezione in caso di errore nello stream
     * @throws ClassNotFoundException eccezione in caso di lettura dell'oggetto del server
     * @throws ServerException eccezione in caso di errore nella connessione al server
     */
    protected String learningFromDbTable(double radius) throws IOException, ClassNotFoundException, ServerException{
        out.writeObject(1);
        out.writeObject(radius);
        String result  = (String)in.readObject();
        if(result.equals("OK")){
            int numClusters = (Integer)in.readObject();
            return (String)in.readObject();
        }
        else throw new ServerException(result);
    }

    /**
     * Metodo che gestisce il salvataggio del risultato del clustering su file
     * @throws IOException eccezione in caso di errore nello stream
     * @throws ClassNotFoundException eccezione in caso di lettura dell'oggetto del server
     * @throws ServerException eccezione in caso di errore nella connessione al server
     */
    protected void storeClusterInFile() throws IOException, ClassNotFoundException, ServerException{
        out.writeObject(2);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    /**
     * Richiede al server di salvare una tabella specificata del database su file.
     *
     * @param table_name il nome della tabella da salvare
     * @throws IOException           se si verifica un errore di comunicazione
     * @throws ClassNotFoundException se si verifica un errore nella lettura dell'oggetto dal server
     * @throws ServerException       se il server ritorna un messaggio di errore
     */
    protected void storeTableFromDb(String table_name) throws IOException, ClassNotFoundException, ServerException{
        out.writeObject(0);
        out.writeObject(table_name);
        String result = (String)in.readObject();
        if(!result.equals("OK"))
            throw new ServerException(result);
    }

    /**
     * Avvia l'apprendimento dei cluster partendo da una tabella salvata su file,
     * usando un raggio specificato per il clustering.
     *
     * @param table_name il nome della tabella da caricare dal file
     * @param radius     il raggio massimo per il clustering
     * @return una stringa che rappresenta il risultato del clustering
     * @throws IOException           se si verifica un errore di comunicazione
     * @throws ClassNotFoundException se si verifica un errore nella lettura dell'oggetto dal server
     * @throws ServerException       se il server ritorna un messaggio di errore
     */
    protected String learningFromFile(String table_name, double radius) throws IOException, ClassNotFoundException, ServerException{
        out.writeObject(3);
        out.writeObject(table_name);
        out.writeObject(radius);
        String result = (String)in.readObject();
        if(result.equals("OK")){
            return (String)in.readObject();
        }
        else throw new ServerException(result);
    }

}