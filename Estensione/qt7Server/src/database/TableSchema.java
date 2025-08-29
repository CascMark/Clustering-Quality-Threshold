package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe che modella lo schema di una tabella
 * in un database relazionale. Fornisce informazioni
 * su nomi e tipi degli attributi.
 */
public class TableSchema {
    private DbAccess db;

    /**
     * Classe interna che rappresenta una colonna della tabella.
     */
    public class Column {
        private String name;
        private String type;

        /**
         * Costruttore della colonna.
         * @param name nome della colonna
         * @param type tipo della colonna (es. "string" o "number")
         */
        Column(String name, String type) {
            this.name = name;
            this.type = type;
        }

        /**
         * Restituisce il nome della colonna.
         * @return nome della colonna
         */
        public String getColumnName() {
            return name;
        }

        /**
         * Indica se la colonna è di tipo numerico.
         * @return true se la colonna è numerica, false altrimenti
         */
        public boolean isNumber() {
            return type.equals("number");
        }

        /**
         * Restituisce una rappresentazione testuale della colonna.
         * @return stringa contenente nome e tipo della colonna
         */
        public String toString() {
            return name + ":" + type;
        }
    }

    List<Column> tableSchema = new ArrayList<Column>();

    /**
     * Costruisce lo schema della tabella specificata,
     * estraendo nome e tipo delle colonne dal database.
     * @param db oggetto per l'accesso al database
     * @param tableName nome della tabella da analizzare
     * @throws SQLException se si verifica un errore SQL
     */
    public TableSchema(DbAccess db, String tableName) throws SQLException {
        this.db = db;
        HashMap<String, String> mapSQL_JAVATypes = new HashMap<String, String>();

        // Mappa dei tipi SQL a tipi semplificati (string/number)
        mapSQL_JAVATypes.put("CHAR", "string");
        mapSQL_JAVATypes.put("VARCHAR", "string");
        mapSQL_JAVATypes.put("LONGVARCHAR", "string");
        mapSQL_JAVATypes.put("BIT", "string");
        mapSQL_JAVATypes.put("SHORT", "number");
        mapSQL_JAVATypes.put("INT", "number");
        mapSQL_JAVATypes.put("LONG", "number");
        mapSQL_JAVATypes.put("FLOAT", "number");
        mapSQL_JAVATypes.put("DOUBLE", "number");

        Connection con = db.getConnection();
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getColumns(null, null, tableName, null);

        while (res.next()) {
            String columnType = res.getString("TYPE_NAME");
            if (mapSQL_JAVATypes.containsKey(columnType)) {
                tableSchema.add(new Column(
                        res.getString("COLUMN_NAME"),
                        mapSQL_JAVATypes.get(columnType))
                );
            }
        }
        res.close();
    }

    /**
     * Restituisce il numero di attributi (colonne) nello schema.
     * @return numero di colonne
     */
    public int getNumberOfAttributes() {
        return tableSchema.size();
    }

    /**
     * Restituisce la colonna in una certa posizione.
     * @param index indice della colonna
     * @return oggetto Column corrispondente
     */
    public Column getColumn(int index) {
        return tableSchema.get(index);
    }
}
