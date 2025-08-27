package database;

/**
 * Eccezione lanciata quando si presenta un fallimento
 * durante il collegamento al database
 */
public class DatabaseConnectionException extends Exception {
    /**
     * Costruttore di default dell'eccezione
     */
    public DatabaseConnectionException(){}

    /**
     * Costruttore che permette di specificare un messaggio di errore.
     * @param message messaggio descrittivo dell'errore
     */
    public DatabaseConnectionException(String message) {
        super(message);
    }
}
