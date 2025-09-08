
/**
 *  Il package {@code database} contiene le classi che permettono di connettersi ad un database {@code MySQL-Server} e di manipolare i dati in esso contenuti.
 *
 *  <ul>
 *      <li> La classe {@link database.DbAccess} rappresenta una connessione conm un database {@code MySQL-Server} utilizzando il package precompilato {@code mysql-connector-java-8.0.17.jar}</li>
 *      <li> La classe {@link database.TableData} rappresenta i dati contenuti in una tabella del database</li>
 *      <li> La classe {@link database.TableSchema} rappresenta lo schema di una tabella del database</li>
 *      <li> La classe {@link database.Example} rappresenta una singola tupla all'interno di una tabella</li>
 *      <li> La classe {@link database.QUERY_TYPE} è una classe enumerativa</li>
 *      <li> La classe {@link database.DatabaseConnectionException} è un'eccezione personalizzata lanciata quando c'è un errore durante la connessione al database</li>
 *      <li> La classe {@link database.EmptySetException} è un'eccezione personalizzata lanciata quando la tabella non presenta valori</li>
 *      <li> La classe {@link database.NoValueException}  è un'eccezione personalizzata lanciata quando nel resultset un valore risulta assente</li>
 *  </ul>
 */

package database;