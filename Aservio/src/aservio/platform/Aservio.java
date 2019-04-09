/*
 * Created by Samuel Bangslund, Odense SDU Software Engineering 1. semester.
 */
package aservio.platform;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author sbang
 */
public class Aservio extends Application {
    
    private static Aservio instance;
    private Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLManager.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        Scene scene = new Scene(root);

        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.setTitle("Aservio");
        stage.setScene(scene);
        stage.show();
        
        this.stage = stage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static Aservio getInstance() {
        return instance;
    }
    
    public Stage getStage() {
        return stage;
    }
    
}
