package default_package;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 * Classe principale dell'applicazione JavaFX per il client QT-Miner.
 * Si occupa dell'avvio dell'applicazione, del caricamento del file FXML
 * della GUI e della connessione iniziale al server.
 */
public class Main extends Application {

    /**
     * Metodo principale che avvia l'applicazione JavaFX.
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String args[]) {
        launch(args);
    }

    /**
     * Inizializza e mostra la finestra principale dell'applicazione.
     * Carica il layout dalla risorsa FXML, imposta il controller,
     * connette il client al server e configura la finestra principale
     * con titolo, icona e dimensioni.
     * @param stage lo stage principale fornito da JavaFX
     * @throws Exception se si verifica un errore durante il caricamento della GUI o la connessione al server
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();
            ControllerMain controller = loader.getController();
            controller.ConnectToServer("localhost", 8080);

            stage.setTitle("Clustering: Quality Threshold");

            Image icona = new Image(getClass().getResourceAsStream("/media/icona_app.png"));
            stage.getIcons().add(icona);

            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
