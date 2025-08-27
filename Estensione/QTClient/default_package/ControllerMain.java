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

    private boolean db_mode;

    private boolean file_mode;

    @FXML
    private Circle ServerStatus;

    @FXML
    private TextField radius;

    @FXML
    private TextField table_name;

    @FXML
    private AnchorPane mainScreen;

    @FXML
    private AnchorPane startScreen;

    @FXML
    private AnchorPane infoScreen;

    @FXML
    private TextFlow outputTextFlow;

    @FXML
    private Text modeText;

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
        outputTextFlow.getChildren().clear();
        radius.clear();
        table_name.clear();
        modeText.setText("FILE");
        file_mode = true;
        db_mode = false;
    }

    public void db(ActionEvent e) {
        radius.setVisible(true);
        table_name.setVisible(true);
        outputTextFlow.getChildren().clear();
        radius.clear();
        table_name.clear();
        modeText.setText("DATABASE");
        db_mode = true;
        file_mode = false;
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
        modeText.setText("DATABASE");
        db_mode = true;
    }

    @FXML
    public void avvia(ActionEvent e){
        outputTextFlow.getChildren().clear();
        try {
            String radiusText = radius.getText();
            String table_name_value = table_name.getText();
            if (table_name_value == null || table_name_value.trim().isEmpty()) {
                throw new NoTextValues("Errore: inserire un valore nel campo 'Nome tabella'.");
            }
            if (radiusText == null || radiusText.trim().isEmpty()) {
                throw new NoTextValues("Errore: inserire un valore nel campo 'Raggio'.");
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
            if(db_mode){
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
                appendColoredText("Salvataggio del risultato nel file...\n", Color.GREEN);
                client.storeClusterInFile(); // Rimossa la chiamata con parametro fileName
                appendColoredText("Salvataggio completato!\n", Color.GREEN);
            }
            else if(file_mode){
                appendColoredText("Apertura file in corso...\n", Color.GREEN);
                appendColoredText("\n", Color.GREEN);
                String clusteringResult = client.learningFromFile(table_name_value, radius_value);
                appendColoredText("Clustering da file completato.\n", Color.GREEN);
                appendColoredText("\n", Color.GREEN);
                appendColoredText("Contenuto del file:\n", Color.GREEN);
                appendColoredText("\n", Color.GREEN);
                appendColoredText(clusteringResult, Color.BLACK);
                appendColoredText("\n", Color.BLACK);
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
            try {
                ConnectToServer("localhost", 8080);
            } catch (Exception reconnectEx) {
                appendColoredText("Impossibile ristabilire la connessione: " + reconnectEx.getMessage() + "\n", Color.RED);
            }
        } catch (IOException ex) {
            appendColoredText("Errore I/O: " + ex.getMessage() + "\n", Color.RED);
            appendColoredText("Tentativo di riconnessione...\n", Color.ORANGE);
            ex.printStackTrace();
            try {
                ConnectToServer("localhost", 8080);
            } catch (Exception reconnectEx) {
                appendColoredText("Impossibile ristabilire la connessione: " + reconnectEx.getMessage() + "\n", Color.RED);
            }
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