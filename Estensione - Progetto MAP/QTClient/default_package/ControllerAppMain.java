package default_package;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerAppMain{

    private final int CREATOR_WIDTH = 600;
    private final int CREATOR_HEIGHT = 140;

    public void credits(ActionEvent a) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Creator.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Credits");
            stage.setWidth(CREATOR_WIDTH);
            stage.setHeight(CREATOR_HEIGHT);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void info(ActionEvent a){

    }

    public void file(ActionEvent a){

    }

    public void db(ActionEvent a){

    }

}
