package default_package;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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

    @FXML
    private TextFlow outputTextFlow;

    private void appendColoredText(String text, Color color) {
        Text coloredText = new Text(text);
        coloredText.setFill(color);
        outputTextFlow.getChildren().add(coloredText);
    }

    public void ConnectToServer(String ip, int port) {
        try {
            client = new ClusClient(ip, port);
            this.ServerStatus.setFill(Color.GREEN);
            appendColoredText("Connessione al server stabilita.\n", Color.GREEN);
        } catch (IOException e) {
            ServerStatus.setFill(Color.RED);
            appendColoredText("Connessione al server fallita: " + e.getMessage() + "\n", Color.RED);
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
        outputTextFlow.getChildren().clear();
        radius.clear();
        file_name.clear();
        table_name.clear();
    }

    public void db(ActionEvent e) {
        radius.setVisible(true);
        table_name.setVisible(true);
        file_name.setVisible(true);
        file_name_text.setVisible(true);
        outputTextFlow.getChildren().clear();
        radius.clear();
        file_name.clear();
        table_name.clear();
    }

    public void info(ActionEvent e) {
        infoScreen.setVisible(true);
        mainScreen.setVisible(false);
    }

    @FXML
    private void gobackStart(ActionEvent e) {
        startScreen.setVisible(false);
        mainScreen.setVisible(true);
        outputTextFlow.getChildren().clear();
        radius.clear();
        file_name.clear();
        table_name.clear();
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
        outputTextFlow.getChildren().clear();
    }

    @FXML
    public void avvia(ActionEvent e){
        outputTextFlow.getChildren().clear();
        try {
            String radiusText = radius.getText();
            String table_name_value = table_name.getText();
            String file_name_value = null;
            if (table_name_value == null || table_name_value.trim().isEmpty()) {
                throw new NoTextValues("Errore: inserire un valore nel campo 'Nome tabella'.");
            }
            if (radiusText == null || radiusText.trim().isEmpty()) {
                throw new NoTextValues("Errore: inserire un valore nel campo 'Raggio'.");
            }
            if (file_name.isVisible()) {
                file_name_value = file_name.getText();
                if (file_name_value == null || file_name_value.trim().isEmpty()) {
                    throw new NoTextValues("Errore: inserire un valore nel campo 'Nome file'.");
                }
            }
            double radius_value;
            try {
                radius_value = Double.parseDouble(radiusText);
            } catch (NumberFormatException nfe) {
                throw new NoTextValues("Errore: il valore inserito nel campo 'Raggio' non è un numero valido.");
            }
            if (radius_value <= 0) {
                throw new ClusteringRadiusException();
            }
            if (client == null) {
                throw new ServerException("Errore nella connessione al server.");
            }
            appendColoredText("Caricamento tabella dal database...\n", Color.GREEN);
            appendColoredText("\n", Color.GREEN);
            client.storeTableFromDb(table_name_value);
            appendColoredText("Tabella caricata con successo.\n", Color.GREEN);
            appendColoredText("\n", Color.GREEN);
            appendColoredText("Esecuzione clustering...\n", Color.GREEN);
            appendColoredText("\n", Color.GREEN);
            String clusteringResult = client.learningFromDbTable(radius_value);
            appendColoredText("Clustering completato.\n", Color.GREEN);
            appendColoredText("\n", Color.GREEN);
            appendColoredText("Risultato Clustering:\n", Color.GREEN);
            appendColoredText("\n", Color.GREEN);
            appendColoredText(clusteringResult, Color.BLACK);
            appendColoredText("\n", Color.BLACK);
            if (file_name.isVisible()) {
                appendColoredText("Salvataggio cluster su file...\n", Color.GREEN);
                appendColoredText("\n", Color.GREEN);
                client.storeClusterInFile(file_name_value);
                appendColoredText("Cluster salvati su file: " + file_name_value + "\n", Color.GREEN);
                appendColoredText("\n", Color.GREEN);
            }
        } catch (NoTextValues ex) {
            appendColoredText("Attenzione, campi di input vuoti: " + ex.getMessage() + "\n", Color.RED);
            appendColoredText("\n", Color.RED);
            ex.printStackTrace();
        } catch (ClusteringRadiusException ex) {
            appendColoredText("Errore: Raggio di clustering non valido. Il raggio deve essere maggiore di 0.\n", Color.RED);
            appendColoredText("\n", Color.RED);
            ex.printStackTrace();
        } catch (ServerException ex) {
            appendColoredText("Errore server: " + ex.getMessage() + "\n", Color.RED);
            appendColoredText("\n", Color.RED);
            ex.printStackTrace();
        } catch (IOException ex) {
            appendColoredText("Errore I/O: " + ex.getMessage() + "\n", Color.RED);
            appendColoredText("\n", Color.RED);
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            appendColoredText("Errore: Classe non trovata. " + ex.getMessage() + "\n", Color.RED);
            appendColoredText("\n", Color.RED);
            ex.printStackTrace();
        } catch (Exception ex) {
            appendColoredText("Si è verificato un errore inatteso: " + ex.getMessage() + "\n", Color.RED);
            appendColoredText("\n", Color.RED);
            ex.printStackTrace();
        }
    }
}