package database;

/**
 * Eccezione lanciata quando viene restituito un
 * resultset vuoto
 */
public class EmptySetException extends Exception {
    /**
     * Costruttore di default dell'eccezione
     */
    public EmptySetException(){}

    /**
     * Costruttore che permette di specificare un messaggio di errore.
     * @param message messaggio descrittivo dell'errore
     */
    public EmptySetException(String message) {
        super(message);
    }
}
