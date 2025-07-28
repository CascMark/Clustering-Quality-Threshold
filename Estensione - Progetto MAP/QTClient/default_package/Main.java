package default_package;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {

    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();
            ControllerMain controller = loader.getController();
            controller.ConnectToServer("localhost", 8080);
            stage.setTitle("Clustering: Quality Threshold");
            Image icona = new Image(getClass().getResourceAsStream("img/icona_app.png"));
            stage.getIcons().add(icona);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
