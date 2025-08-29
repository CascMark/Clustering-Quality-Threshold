package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe che rappresenta un server multi-client.
 * Si occupa di accettare connessioni da parte dei client
 * e di creare un thread per gestire ciascuna connessione.
 */
public class MultiServer {

    private int PORT = 8080;

    /**
     * Instanzia un oggetto di tipo MultiServer
     * @param args
     */
    public static void main(String[] args){
        MultiServer multi_server = new MultiServer(8080);
    }

    /**
     * Costruttore di classe, inizializza la porta ed invoca run()
     * @param port
     */
    MultiServer(int port){
        this.PORT = port;
        run();
    }

    /**
     * Istanzia un oggetto istanza della classe ServerSocket che pone in attesa di crichiesta di connessioni
     * da parte del client. Ad ogni nuova richiesta connessione si istanzia ServerOneClient.
     */
    void run(){
        try {
            ServerSocket server_socket = new ServerSocket(PORT);
            try(server_socket){
                System.out.println("Started " + server_socket);
                while(true){
                    Socket socket = server_socket.accept();
                    System.out.println("Connesione client " + socket);
                    try{
                        new ServerOneClient(socket);
                    }catch(IOException ioe){
                        System.out.println("Errore nell'istanziazione del socket: " + socket);
                        socket.close();
                    }
                }
            }
        }catch(IOException ioe){
            System.out.println("Errore " + ioe.getMessage());
        }
    }
}
