package default_package;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import keyboardinput.Keyboard;
/**
 * Classe di test che stabilisce una connessione socket con un server per
 * gestire operazioni di clustering, come il caricamento da file o database
 * e la serializzazione dei risultati.
 */
public class MainTest {
	private ObjectOutputStream out;
	private ObjectInputStream in ;

	/**
	 * Costruisce un oggetto MainTest creando un socket verso un indirizzo IP e porta specificati.
	 *
	 * @param ip l'indirizzo IP del server
	 * @param port la porta del server
	 * @throws IOException se si verifica un errore nella connessione
	 */
	public MainTest(String ip, int port) throws IOException{
		InetAddress addr = InetAddress.getByName(ip);
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, port);
		System.out.println(socket);

		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());	;
	}


	/**
	 * Mostra un menu all'utente per scegliere se caricare i cluster da file o da database.
	 *
	 * @return l'opzione scelta dall'utente (1 o 2)
	 */
	private int menu(){
		int answer;

		do{
			System.out.println("(1) Load clusters from file");
			System.out.println("(2) Load data from db");
			System.out.print("(1/2):");
			answer=Keyboard.readInt();
		}
		while(answer<=0 || answer>2);
		return answer;

	}


	/**
	 * Richiede al server di eseguire l'apprendimento da file, specificando il nome della tabella e il raggio.
	 *
	 * @return la rappresentazione testuale dei cluster generati
	 * @throws SocketException se si verifica un errore di rete
	 * @throws ServerException se il server restituisce un errore logico
	 * @throws IOException se si verifica un errore I/O
	 * @throws ClassNotFoundException se il risultato letto non è riconosciuto
	 */
	private String learningFromFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(3);

		System.out.print("Table Name:");
		String tabName=Keyboard.readString();
		out.writeObject(tabName);
		double r=1.0;
		do{
			System.out.print("Radius:");
			r=Keyboard.readDouble();
		} while(r<=0);
		out.writeObject(r);
		String result = (String)in.readObject();
		if(result.equals("OK"))
			return (String)in.readObject();
		else throw new ServerException(result);

	}

	/**
	 * Chiede al server di caricare i dati da una tabella nel database.
	 *
	 * @throws SocketException se si verifica un errore di rete
	 * @throws ServerException se il server restituisce un errore logico
	 * @throws IOException se si verifica un errore I/O
	 * @throws ClassNotFoundException se il risultato letto non è riconosciuto
	 */
	private void storeTableFromDb() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(0);
		System.out.print("Table name:");
		String tabName=Keyboard.readString();
		out.writeObject(tabName);
		String result = (String)in.readObject();
		if(!result.equals("OK"))
			throw new ServerException(result);

	}

	/**
	 * Esegue il clustering sui dati caricati da una tabella nel database.
	 *
	 * @return la rappresentazione testuale dei cluster
	 * @throws SocketException se si verifica un errore di rete
	 * @throws ServerException se il server restituisce un errore logico
	 * @throws IOException se verifica un errore I/O
	 * @throws ClassNotFoundException se il risultato letto non è riconosciuto
	 */
	private String learningFromDbTable() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(1);
		double r=1.0;
		do{
			System.out.print("Radius:");
			r=Keyboard.readDouble();
		} while(r<=0);
		out.writeObject(r);
		String result = (String)in.readObject();
		if(result.equals("OK")){
			System.out.println("Number of Clusters:"+in.readObject());
			return (String)in.readObject();
		}
		else throw new ServerException(result);


	}

	/**
	 * Chiede al server di salvare i cluster generati in un file.
	 *
	 * @throws SocketException se si verifica un errore di rete
	 * @throws ServerException se il server restituisce un errore logico
	 * @throws IOException se si verifica un errore I/O
	 * @throws ClassNotFoundException se il risultato letto non è riconosciuto
	 */
	private void storeClusterInFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(2);
		String result = (String)in.readObject();
		if(!result.equals("OK"))
			throw new ServerException(result);

	}

	/**
	 * Metodo principale. Stabilisce la connessione e gestisce il flusso di interazione con l'utente.
	 *
	 * @param args contiene l'indirizzo IP e la porta del server
	 */
	public static void main(String[] args) {
		String ip=args[0];
		int port = Integer.parseInt(args[1]);
		MainTest main=null;
		try{
			main=new MainTest(ip,port);
		}
		catch (IOException e){
			System.out.println(e);
			return;
		}


		do{
			int menuAnswer=main.menu();
			switch(menuAnswer)
			{
				case 1:
					try {
						String kmeans=main.learningFromFile();
						System.out.println(kmeans);
					}
					catch (SocketException e) {
						System.out.println(e);
						return;
					}
					catch (FileNotFoundException e) {
						System.out.println(e);
						return ;
					} catch (IOException e) {
						System.out.println(e);
						return;
					} catch (ClassNotFoundException e) {
						System.out.println(e);
						return;
					}
					catch (ServerException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2:

					while(true){
						try{
							main.storeTableFromDb();
							break;
						}

						catch (SocketException e) {
							System.out.println(e);
							return;
						}
						catch (FileNotFoundException e) {
							System.out.println(e);
							return;

						} catch (IOException e) {
							System.out.println(e);
							return;
						} catch (ClassNotFoundException e) {
							System.out.println(e);
							return;
						}
						catch (ServerException e) {
							System.out.println(e.getMessage());
						}
					}

					char answer='y';
					do{
						try
						{
							String clusterSet=main.learningFromDbTable();
							System.out.println(clusterSet);

							main.storeClusterInFile();

						}
						catch (SocketException e) {
							System.out.println(e);
							return;
						}
						catch (FileNotFoundException e) {
							System.out.println(e);
							return;
						}
						catch (ClassNotFoundException e) {
							System.out.println(e);
							return;
						}catch (IOException e) {
							System.out.println(e);
							return;
						}
						catch (ServerException e) {
							System.out.println(e.getMessage());
						}
						System.out.print("Would you repeat?(y/n)");
						answer=Keyboard.readChar();
					}
					while(Character.toLowerCase(answer)=='y');
					break;
				default:
					System.out.println("Invalid option!");
			}

			System.out.print("would you choose a new operation from menu?(y/n)");
			if(Keyboard.readChar()!='y')
				break;
		}
		while(true);
	}
}