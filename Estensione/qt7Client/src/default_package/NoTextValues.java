package default_package;

/**
 * Eccezione lanciata in caso di assenza di valori all'interno
 * dei campi dell'applicazione
 */
public class NoTextValues extends Exception{
    /**
     * Costruisce una nuova eccezione con il messaggio specificato
     * @param message il messaggio d'errore
     */
    public NoTextValues(String message) {
        super(message);
    }
}
