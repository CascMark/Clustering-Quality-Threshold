package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {

    private int PORT = 8080;

    public static void main(String[] args){
        MultiServer multi_server = new MultiServer(8080);
    }

    MultiServer(int port){
        this.PORT = port;
        run();
    }

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
