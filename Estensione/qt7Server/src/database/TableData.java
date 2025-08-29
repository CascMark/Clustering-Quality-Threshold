package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import database.TableSchema.Column;

/**
 * Classe che si occupa dell'accesso ai dati di una tabella del database.
 * Fornisce metodi per estrarre transazioni distinte, valori distinti di una colonna
 * e valori aggregati (minimo o massimo).
 */
public class TableData {

    private DbAccess db;

    /**
     * Costruttore della classe.
     * @param db oggetto per la connessione al database
     */
    public TableData(DbAccess db) {
        this.db = db;
    }

    /**
     * Ricava lo schema della tabella con nome table. Esegue una
     * interrogazione per estrarre le tuple distinte da tale tabella. Per ogni tupla del resultset,
     * si crea un oggetto, istanza della classe Example, il cui riferimento va incluso nella lista
     * da restituire. In particolare, per la tupla corrente nel resultset, si estraggono i valori dei
     * singoli campi (usando getFloat() o getString()), e li si aggiungono all’oggetto istanza
     * della classe Example che si sta costruendo
     * @param table nome della tabella
     * @return lista delle transazioni distinte
     * @throws SQLException Il metodo può propagare un eccezione di tipo SQLException (in presenza di errori nella
     * esecuzione della query)
     * @throws EmptySetException Il metodo può propagare un eccezione di tipo EmptySetException (se il resultset è vuoto)
     */
    public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException {
        LinkedList<Example> transSet = new LinkedList<Example>();
        Statement statement;
        TableSchema tSchema = new TableSchema(db, table);

        String query = "select distinct ";
        for (int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
            Column c = tSchema.getColumn(i);
            if (i > 0)
                query += ",";
            query += c.getColumnName();
        }
        if (tSchema.getNumberOfAttributes() == 0)
            throw new SQLException();
        query += (" FROM " + table);

        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        boolean empty = true;
        while (rs.next()) {
            empty = false;
            Example currentTuple = new Example();
            for (int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
                if (tSchema.getColumn(i).isNumber())
                    currentTuple.add(rs.getDouble(i + 1));
                else
                    currentTuple.add(rs.getString(i + 1));
            }
            transSet.add(currentTuple);
        }
        rs.close();
        statement.close();
        if (empty)
            throw new EmptySetException();

        return transSet;
    }

    /**
     * Restituisce l'insieme dei valori distinti presenti in una certa colonna di una tabella.
     * @param table nome della tabella
     * @param column oggetto che rappresenta la colonna
     * @return insieme ordinato dei valori distinti
     * @throws SQLException se si verifica un errore nell'accesso al database
     */
    public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
        Set<Object> valueSet = new TreeSet<Object>();
        Statement statement;
        TableSchema tSchema = new TableSchema(db, table);

        String query = "select distinct ";
        query += column.getColumnName();
        query += (" FROM " + table);
        query += (" ORDER BY " + column.getColumnName());

        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            if (column.isNumber())
                valueSet.add(rs.getDouble(1));
            else
                valueSet.add(rs.getString(1));
        }
        rs.close();
        statement.close();

        return valueSet;
    }

    /**
     * Restituisce il valore aggregato (massimo o minimo) di una colonna.
     * @param table nome della tabella
     * @param column oggetto che rappresenta la colonna
     * @param aggregate tipo di aggregazione da eseguire (MIN o MAX)
     * @return valore massimo o minimo della colonna
     * @throws SQLException se si verifica un errore nell'accesso al database
     * @throws NoValueException se non è stato trovato alcun valore
     */
    public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate)
            throws SQLException, NoValueException {
        Statement statement;
        TableSchema tSchema = new TableSchema(db, table);
        Object value = null;
        String aggregateOp = "";

        String query = "select ";
        if (aggregate == QUERY_TYPE.MAX)
            aggregateOp += "max";
        else
            aggregateOp += "min";
        query += aggregateOp + "(" + column.getColumnName() + ") FROM " + table;

        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            if (column.isNumber())
                value = rs.getFloat(1);
            else
                value = rs.getString(1);
        }
        rs.close();
        statement.close();

        if (value == null)
            throw new NoValueException("No " + aggregateOp + " on " + column.getColumnName());

        return value;
    }
}
