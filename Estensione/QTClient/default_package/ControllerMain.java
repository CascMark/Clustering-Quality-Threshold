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

/**
 * Controller principale dell'applicazione JavaFX per il client QT-Miner.
 * Gestisce la connessione al server, l'interazione con l'utente tramite GUI,
 * la scelta della modalità (database o file), l'esecuzione del clustering e
 * la visualizzazione dei risultati.
 */
public class ControllerMain {

    /** Oggetto client per comunicare con il server QT-Miner */
    private ClusClient client;

    /** Flag che indica se la modalità attiva è database */
    private boolean db_mode;

    /** Flag che indica se la modalità attiva è file */
    private boolean file_mode;

    /** Indicatore dello stato del server nella GUI */
    @FXML
    private Circle ServerStatus;

    /** Campo di testo per il raggio del clustering */
    @FXML
    private TextField radius;

    /** Campo di testo per il nome della tabella */
    @FXML
    private TextField table_name;

    /** AnchorPane principale dell'interfaccia */
    @FXML
    private AnchorPane mainScreen;

    /** AnchorPane della schermata di avvio */
    @FXML
    private AnchorPane startScreen;

    /** AnchorPane della schermata informativa */
    @FXML
    private AnchorPane infoScreen;

    /** Flusso di testo per l'output a video */
    @FXML
    private TextFlow outputTextFlow;

    /** Testo per indicare la modalità attiva (FILE/DATABASE) */
    @FXML
    private Text modeText;

    /**
     * Aggiunge del testo colorato al TextFlow di output.
     *
     * @param text  il testo da aggiungere
     * @param color il colore del testo
     */
    private void appendColoredText(String text, Color color) {
        Text coloredText = new Text(text);
        coloredText.setFill(color);
        outputTextFlow.getChildren().add(coloredText);
    }

    /**
     * Connette il client al server QT-Miner all'indirizzo e porta specificati.
     *
     * @param ip   indirizzo IP del server
     * @param port porta del server
     */
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

    /**
     * Azione collegata al pulsante di connessione al server.
     *
     * @param e evento generato dall'azione
     */
    @FXML
    public void ConnectToServer(ActionEvent e) {
        ConnectToServer("localhost", 8080);
    }

    /**
     * Apre il browser predefinito sulla pagina GitHub dell'autore.
     *
     * @param e evento generato dall'azione
     */
    public void github(ActionEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/CascMark"));
        } catch (Exception exception) {
            exception.getMessage();
        }
    }

    /**
     * Imposta la modalità FILE e aggiorna l'interfaccia.
     *
     * @param e evento generato dall'azione
     */
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

    /**
     * Imposta la modalità DATABASE e aggiorna l'interfaccia.
     *
     * @param e evento generato dall'azione
     */
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

    /**
     * Mostra la schermata informativa.
     *
     * @param e evento generato dall'azione
     */
    public void info(ActionEvent e) {
        infoScreen.setVisible(true);
        mainScreen.setVisible(false);
    }

    /**
     * Torna alla schermata principale dalla schermata di start.
     *
     * @param e evento generato dall'azione
     */
    @FXML
    private void gobackStart(ActionEvent e) {
        startScreen.setVisible(false);
        mainScreen.setVisible(true);
        outputTextFlow.getChildren().clear();
        radius.clear();
        table_name.clear();
    }

    /**
     * Torna alla schermata principale dalla schermata info.
     *
     * @param e evento generato dall'azione
     */
    @FXML
    private void gobackInfo(ActionEvent e){
        infoScreen.setVisible(false);
        mainScreen.setVisible(true);
    }

    /**
     * Apre il browser sulla pagina di Wikipedia sul Clustering.
     *
     * @param e evento generato dall'azione
     */
    public void moreInfo(ActionEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://it.wikipedia.org/wiki/Clustering"));
        } catch (Exception exception) {
            exception.getMessage();
        }
    }

    /**
     * Mostra la schermata di start all'avvio dell'applicazione.
     *
     * @param e evento generato dall'azione
     */
    @FXML
    private void start(ActionEvent e) {
        mainScreen.setVisible(false);
        startScreen.setVisible(true);
        outputTextFlow.getChildren().clear();
        modeText.setText("DATABASE");
        db_mode = true;
    }

    /**
     * Avvia il processo di clustering, leggendo dati da database o file a seconda della modalità.
     * Gestisce anche l'output a video, i messaggi di errore e la riconnessione al server in caso di problemi.
     *
     * @param e evento generato dall'azione
     */
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
                client.storeClusterInFile();
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