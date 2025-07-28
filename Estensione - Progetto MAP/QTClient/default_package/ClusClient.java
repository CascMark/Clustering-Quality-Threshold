package default_package;

import keyboardinput.Keyboard;

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

    protected String learningFromDbTable(double radius) throws IOException, ClassNotFoundException, ServerException{
        out.writeObject(1);
        out.writeObject(radius);
        String result  = (String)in.readObject();
        if(result.equals("OK")){
            return (String)in.readObject();
        }
        else throw new ServerException(result);
    }

    protected void storeClusterInFile(String file_name) throws IOException, ClassNotFoundException, ServerException{
        out.writeObject(2);
        if (!file_name.endsWith(".dat")) {
            file_name += ".dat";
        }
        out.writeObject(file_name);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    protected void storeTableFromDb(String table_name) throws IOException, ClassNotFoundException, ServerException{
        out.writeObject(0);
        System.out.print("Table name:");
        table_name = Keyboard.readString();
        out.writeObject(table_name);
        String result = (String)in.readObject();
        if(!result.equals("OK"))
            throw new ServerException(result);
    }

    protected String learningFromFile(String table_name, double radius) throws IOException, ClassNotFoundException, ServerException{
        out.writeObject(1);
        out.writeObject(table_name);
        out.writeObject(radius);
        String result = (String)in.readObject();
        if(result.equals("OK")){
            return (String)in.readObject();
        }
        else throw new ServerException(result);
    }

}
