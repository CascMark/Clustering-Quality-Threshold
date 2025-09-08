
/**
 * il package {@code data} contiene le classi rappresentanti i dati scambiati tra il client e il server.
 *
 * <ul>
 *     <li> La classe {@link data.Attribute} modella l'entità attributo</li>
 *     <li> La classe {@link data.ContinuousAttribute} modella l'entità attributo continuo (estende attribute)</li>
 *     <li> La classe {@link data.DiscreteAttribute} modella l'entità attributo discreto (estende attribute)</li>
 *     <li> La classe {@link data.Item} modella un generico item</li>
 *     <li> La classe {@link data.ContinuousItem} modella una coppia attributo continuo - valore numerico (estende Item)</li>
 *     <li> La classe {@link data.DiscreteItem} modella una coppia attributo discreto - valore discreto (estende Item)</li>
 *     <li> La classe {@link data.Tuple} rappresenta una tupla come sequenze di attributo - valore</li>
 *     <li> La classe {@link data.Data} modella l'insieme di transazioni o di tuple (dette anche esempi)</li>
 *     <li> La classe {@link data.EmptyDatasetException} rappresenta un'eccezione persoalizzata lanciata quando il dataset è vuoto</li>
 * </ul>
 */

package data;

