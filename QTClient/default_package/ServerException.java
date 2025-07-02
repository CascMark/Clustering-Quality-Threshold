package default_package;
/**
 * Eccezione personalizzata lanciata in caso di errore lato server.
 */
public class ServerException extends Exception {
    /**
     * Costruisce una nuova eccezione con il messaggio specificato.
     * @param message Il messaggio di errore.
     */
    public ServerException(String message) {
        super(message);
    }
}
