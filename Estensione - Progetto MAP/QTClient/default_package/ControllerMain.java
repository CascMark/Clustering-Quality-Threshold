package default_package;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class ControllerMain {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void github(ActionEvent e){
        try{
            Desktop.getDesktop().browse(new URI("https://github.com/CascMark"));
        }
        catch(Exception exception){
            exception.getMessage();
        }
    }

    public void file(ActionEvent e){

    }

    public void db(ActionEvent e){

    }

    public void info(ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("infoC.fxml"));
            Parent root = loader.load();
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
            Parent root = loader.load();
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
}
