package default_package;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClusClient {

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClusClient(String ip, int port) throws IOException{
        InetAddress addr = InetAddress.getByName(ip);
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /*private String learningFromDbTable(){

    }

    private void storeClusterInFile(){

    }

    //Chiede raggio
    private void storeTableFromDb(){

    }

    //Chiede raggio
    private String learningFromFile(){

    }*/

}
