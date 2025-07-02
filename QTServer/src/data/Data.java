package data;
import database.*;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Set;

/**
 * Classe concreta per modellare l'insieme di transazione
 * o tuple o anche denominati esempi
 */
public class Data implements Iterable<Attribute>, Serializable {

	private List<Example> data = new ArrayList<Example>();
	private int numberOfExamples;
	public List<Attribute> attributeSet;

	/**
	 * Costruisce un oggetto {@code Data} popolando lo schema e i dati
	 * dalla tabella del database specificata.
	 *
	 * @param nome_tabella nome della tabella nel database da cui estrarre i dati
	 * @throws DatabaseConnectionException se la connessione al database fallisce
	 * @throws EmptySetException se la tabella è vuota
	 * @throws SQLException se si verifica un errore SQL
	 */
	public Data(String nome_tabella) throws DatabaseConnectionException, EmptySetException, SQLException {
		DbAccess db = new DbAccess();
		try {
			db.initConnection();
			TableData tableData = new TableData(db);
			TableSchema tableSchema = new TableSchema(db, nome_tabella);
			data = tableData.getDistinctTransazioni(nome_tabella);
			numberOfExamples = data.size();
			attributeSet = new ArrayList<Attribute>();
			for (int i = 0; i < tableSchema.getNumberOfAttributes(); i++) {
				TableSchema.Column column = tableSchema.getColumn(i);
				if (column.isNumber()) {
					try {
						Object minObj = tableData.getAggregateColumnValue(nome_tabella, column, QUERY_TYPE.MIN);
						Object maxObj = tableData.getAggregateColumnValue(nome_tabella, column, QUERY_TYPE.MAX);
						double min, max;
						if (minObj instanceof Float) {
							min = ((Float) minObj).doubleValue();
						} else {
							min = (Double) minObj;
						}
						if (maxObj instanceof Float) {
							max = ((Float) maxObj).doubleValue();
						} else {
							max = (Double) maxObj;
						}
						ContinuousAttribute continuousAttr = new ContinuousAttribute(column.getColumnName(), i, min, max);
						attributeSet.add(continuousAttr);
					} catch (NoValueException e) {
						throw new SQLException("Errore nel calcolo di min/max per l'attributo " + column.getColumnName());
					}
				} else {
					Set<Object> distinctValues = tableData.getDistinctColumnValues(nome_tabella, column);
					String[] valuesArray = new String[distinctValues.size()];
					int index = 0;
					for (Object value : distinctValues) {
						valuesArray[index++] = (String) value;
					}
					DiscreteAttribute discreteAttr = new DiscreteAttribute(column.getColumnName(), i, valuesArray);
					attributeSet.add(discreteAttr);
				}
			}
		} catch (DatabaseConnectionException dce) {
			throw new DatabaseConnectionException("Impossibile connettersi al database: " + dce.getMessage());
		} catch (EmptySetException ese) {
			throw new EmptySetException("La tabella " + nome_tabella + " è vuota: " + ese.getMessage());
		} catch (SQLException sqle) {
			throw new SQLException("Errore SQL nel recupero della tabella: " + sqle.getMessage());
		} finally {
			try {
				if (db != null) {
					db.closeConnection();
				}
			} catch (SQLException e){}
		}
	}

	/**
	 * Restituisce il numero di esempi
	 * @return numero di esempi
	 */
	public int getNumberOfExamples() {
		return numberOfExamples;
	}

	/**
	 * Restituisce il numero di attributi
	 * @return numero di attributi
	 */
	public int getNumberOfAttributes() {
		return attributeSet.size();
	}

	/**
	 * Restituisce lo schema degli attributi nel dataset
	 * @return lista degli attributi
	 */
	public List<Attribute> getAttributeSchema() {
		return attributeSet;
	}

	/**
	 * Restituisce il valore di un certo attributo nella posizione
	 * specificata
	 * @param exampleIndex indice dell'esempio
	 * @param attributeIndex indice dell'attributo
	 * @return valore dell'attributo corrispondente agli indici
	 * @throws EmptyDatasetException se il dataset è vuoto
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex) throws EmptyDatasetException {
		if (numberOfExamples == 0) {
			throw new EmptyDatasetException();
		}
		return data.get(exampleIndex).get(attributeIndex);
	}

	/**
	 * Restituisce l'attributo nella posizione specificata dello schema
	 * @param index indice dell'attributo
	 * @return attributo corrispondente all'indice passato
	 */
	public Attribute getAttribute(int index) {
		return attributeSet.get(index);
	}

	/**
	 * crea una stringa in cui memorizza lo schema della
	 * tabella (vedi attributeSet) e le transazioni memorizzate in data,
	 * opportunamente enumerate. Restituisce tale stringa
	 * @return stringa rappresentante il dataset
	 */
	@Override
	public String toString() {
		StringBuilder attributeSchema = new StringBuilder();

		Iterator<Attribute> iterator = attributeSet.iterator();
		while (iterator.hasNext()) {
			Attribute attr = iterator.next();
			attributeSchema.append(attr.getName());
			if (iterator.hasNext()) {
				attributeSchema.append(", ");
			}
		}
		attributeSchema.append("\n");

		for (int i = 0; i < numberOfExamples; i++) {
			attributeSchema.append(i).append(":");

			for (int j = 0; j < attributeSet.size(); j++) {
				attributeSchema.append(data.get(i).get(j));
				if (j < attributeSet.size() - 1) {
					attributeSchema.append(",");
				}
			}
			attributeSchema.append(",\n");
		}
		return attributeSchema.toString();
	}

	/**
	 * Costruisce un oggetto Tuple rappresentante l'itemset
	 * corrispondente all'esempio in posizione index, associando
	 * ad ogni attributo l'opportuno tipo di Item.
	 *
	 * @param index indice dell'esempio
	 * @return tupla corrispondente all'esempio
	 */
	public Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(attributeSet.size());
		Iterator<Attribute> attribute = attributeSet.iterator();
		int i = 0;
		while (attribute.hasNext()) {
			if (attribute.next() instanceof DiscreteAttribute) {
				tuple.add(new DiscreteItem((DiscreteAttribute) attributeSet.get(i), (String) data.get(index).get(i)), i);
				i++;
			} else {
				tuple.add(new ContinuousItem((ContinuousAttribute) attributeSet.get(i), (Double) data.get(index).get(i)), i);
				i++;
			}
		}
		return tuple;
	}

	/**
	 * Restituisce un iteratore sugli attributi dello schema.
	 *
	 * @return iteratore sugli attributi
	 */
	@Override
	public Iterator<Attribute> iterator() {
		return this.attributeSet.iterator();
	}

	/**
	 * Metodo di test che crea un oggetto Data da una tabella
	 * fittizia e ne stampa il contenuto.
	 *
	 * @param args argomenti da linea di comando (non utilizzati)
	 */
	public static void main(String args[]) {
		try {
			Data trainingSet = new Data("nome_tabella");
			System.out.println(trainingSet);
		} catch (DatabaseConnectionException | EmptySetException | SQLException e) {
			System.err.println("Errore: " + e.getMessage());
		}
	}
}