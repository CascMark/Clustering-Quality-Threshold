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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import mining.ClusteringRadiusException;

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
    public void ConnectToServer(ActionEvent e) {
        ConnectToServer("localhost", 8080);
    }

    public void github(ActionEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/CascMark"));
        } catch (Exception exception) {
            exception.getMessage();
        }
    }

    public void file(ActionEvent e) {
        radius.setVisible(true);
        table_name.setVisible(true);
        file_name.setVisible(false);
        file_name_text.setVisible(false);
    }

    public void db(ActionEvent e) {
        radius.setVisible(true);
        table_name.setVisible(true);
        file_name.setVisible(true);
        file_name_text.setVisible(true);
    }

    public void info(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("infoC.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ioe) {
            ioe.getMessage();
        }
    }

    public void goback(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ioe) {
            ioe.getMessage();
        }
    }

    public void moreInfo(ActionEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://it.wikipedia.org/wiki/Clustering"));
        } catch (Exception exception) {
            exception.getMessage();
        }
    }


    /*
    PROBLEMA QUA
     */
    @FXML
    public void start(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Start.fxml"));
            root = loader.load();
            ControllerStart startController = loader.getController();
            startController.setClient(client);
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    @FXML
    public void avvia(ActionEvent e){
        try {
            double radius_value = Double.parseDouble(radius.getText());
            String table_name_value = table_name.getText();
            String file_name_value = null;
            if (file_name.isVisible()) {
                file_name_value = file_name.getText();
                if (file_name_value == null || file_name_value.trim().isEmpty()) {
                    throw new NoTextValues("Errore, inserire i valori mancanti");
                }
            }
            if (radius_value <= 0) {
                throw new ClusteringRadiusException();
            }
            if (table_name_value == null || table_name_value.trim().isEmpty()) {
                throw new NoTextValues("Errore, inserire i valori mancanti");
            }
            if (client == null) {
                throw new ServerException("Errore nella connessione al server");
            }
            client.storeTableFromDb(table_name_value);
            client.learningFromDbTable(radius_value);
            if (file_name.isVisible()) {
                client.storeClusterInFile(file_name_value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
