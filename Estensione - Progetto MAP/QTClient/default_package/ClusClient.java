package default_package;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClusClient {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket; // Aggiunto riferimento al socket

    public ClusClient(String ip, int port) throws IOException{
        InetAddress addr = InetAddress.getByName(ip);
        System.out.println("addr = " + addr);
        socket = new Socket(addr, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    protected String learningFromDbTable(double radius) throws IOException, ClassNotFoundException, ServerException{
        out.writeObject(1);
        out.writeObject(radius);
        String result  = (String)in.readObject();
        if(result.equals("OK")){
            int numClusters = (Integer)in.readObject(); // Leggi il numero di cluster
            return (String)in.readObject();
        }
        else throw new ServerException(result);
    }

    protected void storeClusterInFile() throws IOException, ClassNotFoundException, ServerException{
        out.writeObject(2);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    protected void storeTableFromDb(String table_name) throws IOException, ClassNotFoundException, ServerException{
        out.writeObject(0);
        out.writeObject(table_name);
        String result = (String)in.readObject();
        if(!result.equals("OK"))
            throw new ServerException(result);
    }

    protected String learningFromFile(String table_name, double radius) throws IOException, ClassNotFoundException, ServerException{
        out.writeObject(3);
        out.writeObject(table_name);
        out.writeObject(radius);
        String result = (String)in.readObject();
        if(result.equals("OK")){
            return (String)in.readObject();
        }
        else throw new ServerException(result);
    }

}