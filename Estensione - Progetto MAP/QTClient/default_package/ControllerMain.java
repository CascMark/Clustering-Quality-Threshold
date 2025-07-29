package default_package;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import mining.ClusteringRadiusException;

public class ControllerMain {

    private ClusClient client;

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

    @FXML
    private AnchorPane mainScreen;

    @FXML
    private AnchorPane startScreen;

    @FXML
    private AnchorPane infoScreen;


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
        infoScreen.setVisible(true);
        mainScreen.setVisible(false);
    }

    @FXML
    private void gobackStart(ActionEvent e) {
        startScreen.setVisible(false);
        mainScreen.setVisible(true);
    }

    @FXML
    private void gobackInfo(ActionEvent e){
        infoScreen.setVisible(false);
        mainScreen.setVisible(true);
    }

    public void moreInfo(ActionEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://it.wikipedia.org/wiki/Clustering"));
        } catch (Exception exception) {
            exception.getMessage();
        }
    }

    @FXML
    private void start(ActionEvent e) {
        mainScreen.setVisible(false);
        startScreen.setVisible(true);
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
