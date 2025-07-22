package default_package;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

public class AppMain extends Application {

    private final int WIDTH = 1200;
    private final int HEIGHT = 750;

    public static void main(String args[]){
        launch();
    }

    /**
     * Metodo per creare lo stage che rappresenta il menù principale
     * @return stage
     * @throws IOException
     */
    public Stage menu_principale() throws IOException {
        Stage stage = new Stage();
        try {

            Parent root = FXMLLoader.load(getClass().getResource("AppMain.fxml"));

            Scene scene = new Scene(root, HEIGHT, WIDTH, Color.LIGHTGREEN);

            Image icon = new Image(getClass().getResourceAsStream("icona_app.png"));

            stage.setTitle("Clustering: Quality Threshold");
            stage.getIcons().add(icon);
            stage.setWidth(WIDTH);
            stage.setHeight(HEIGHT);
            stage.setResizable(false);
            stage.setScene(scene);
            return stage;
        }
        catch(IOException ioe) {
            ioe.getMessage();
            return stage;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        try{
            stage = menu_principale();
            stage.show();
        }
        catch(Exception e){
            e.getMessage();
        }
    }
}
