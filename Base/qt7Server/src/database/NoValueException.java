package database;

/**
 * Eccezione lanciata quando all'interno del resultset
 * un valore risulta assente
 */
public class NoValueException extends Exception {
    /**
     * Costruttore di default dell'eccezione
     */
    public NoValueException(){}

    /**
     * Costruttore che permette di specificare un messaggio di errore.
     * @param message messaggio descrittivo dell'errore
     */
    public NoValueException(String message) {
        super(message);
    }
}
