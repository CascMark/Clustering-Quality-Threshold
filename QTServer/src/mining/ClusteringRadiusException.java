package mining;

/**
 * Eccezione lanciata quando il raggio di clustering risulta troppo grande,
 * causando la creazione di un solo cluster.
 */
public class ClusteringRadiusException extends Exception {

    /**
     * Costruttore di default dell'eccezione.
     */
    public ClusteringRadiusException() {}

    /**
     * Costruttore che permette di specificare un messaggio di errore.
     * @param msg messaggio descrittivo dell'errore
     */
    public ClusteringRadiusException(String msg) {
        super(msg);
    }
}
