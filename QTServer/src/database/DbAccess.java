package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilizzata per realizzare il collegamento
 * al database
 */
public class DbAccess {

    private String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private final String DBMS = "jdbc:mysql";
    private final String SERVER = "localhost";
    private final String DATABASE = "MapDB";
    private final int PORT = 3306;
    private final String USER_ID = "MapUser";
    private final String PASSWORD = "map";
    private Connection conn;

    /**
     * Impartisce al class loader l’ordine di caricare il driver mysql, inizializza la connessione riferita da
     * conn.
     * @throws DatabaseConnectionException metodo solleva e propaga una eccezione di tipo
     * DatabaseConnectionException in caso di fallimento nella connessione al database.
     */
    public void initConnection() throws DatabaseConnectionException{
        try {
            Class.forName(DRIVER_CLASS_NAME);
            String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
            conn = DriverManager.getConnection(connectionString);
        } catch (ClassNotFoundException cnfe) {
            throw new DatabaseConnectionException("Driver JDBC non trovato: " + cnfe.getMessage());
        } catch (SQLException sqle) {
            throw new DatabaseConnectionException("Impossibile connettersi al database: " + sqle.getMessage());
        }
    }

    /**
     * Restituisce conn
     * @return conn
     */
    public Connection getConnection(){ return conn; }

    /**
     * Chiude la connessione con
     * @throws SQLException
     */
    public void closeConnection() throws SQLException{
        if(conn != null && !conn.isClosed()){
            conn.close();
        }
        throw new SQLException();
    }
}
