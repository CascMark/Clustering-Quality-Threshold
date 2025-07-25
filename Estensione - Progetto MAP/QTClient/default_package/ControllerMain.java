package default_package;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class ControllerMain {

    private ClusClient client;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Circle ServerStatus;

    @FXML
    private TextField radius;

    @FXML
    private TextField table_name;

    @FXML
    private TextField file_name;

    @FXML
    private Text file_name_text;

    public void ConnectToServer(String ip, int port) {
        try {
            client = new ClusClient(ip, port);
            this.ServerStatus.setFill(Color.GREEN);
        } catch (IOException e) {
            ServerStatus.setFill(Color.RED);
            System.err.println("Connessione fallita: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void ConnectToServer(ActionEvent e){
        ConnectToServer("localhost", 8080);
    }

    public void github(ActionEvent e){
        try{
            Desktop.getDesktop().browse(new URI("https://github.com/CascMark"));
        }
        catch(Exception exception){
            exception.getMessage();
        }
    }

    public void file(ActionEvent e){
            radius.setVisible(true);
            table_name.setVisible(true);
            file_name.setVisible(false);
            file_name_text.setVisible(false);
    }

    public void db(ActionEvent e){
            radius.setVisible(true);
            table_name.setVisible(true);
            file_name.setVisible(true);
            file_name_text.setVisible(true);
    }

    public void info(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("infoC.fxml"));
            root = loader.load();
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException ioe){
            ioe.getMessage();
        }
    }

    public void goback(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            root = loader.load();
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException ioe){
            ioe.getMessage();
        }
    }

    public void moreInfo(ActionEvent e){
        try{
            Desktop.getDesktop().browse(new URI("https://it.wikipedia.org/wiki/Clustering"));
        }
        catch(Exception exception){
            exception.getMessage();
        }
    }

    public void start(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Start.fxml"));
            root = loader.load();
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException ioe){
            ioe.getMessage();
        }
    }

}
