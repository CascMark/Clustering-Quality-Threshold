package default_package;

/**
 * Eccezione personalizzata lanciata in caso di raggio non valido
 */
public class InvalidRadiusException extends Exception {
    /**
     * Costruisce una nuova eccezione con il messaggio specificato.
     * @param message Il messaggio di errore.
     */
    public InvalidRadiusException(String message) {
        super(message);
    }
}
