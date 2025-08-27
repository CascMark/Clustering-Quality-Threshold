package data;

/**
 * Eccezione lanciata quando il dataset risulta essere vuoto
 */
public class EmptyDatasetException extends Exception {
    /**
     * Costruttore di default dell'eccezione
     */
    public EmptyDatasetException(){}

    /**
     * Costruttore che permette di specificare un messaggio di errore.
     * @param message messaggio descrittivo dell'errore
     */
    public EmptyDatasetException(String message) {
        super(message);
    }
}
