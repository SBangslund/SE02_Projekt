/*
 * Created by Samuel Bangslund, Odense SDU Software Engineering 1. semester.
 */
package aservio.platform;

import aservio.platform.user.User;
import aservio.platform.user.UserInfo;
import aservio.platform.user.Address;
import aservio.platform.user.roles.Caretaker;
import aservio.platform.user.roles.Citizen;
import aservio.platform.user.roles.Role;
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
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        primaryStage = stage;

        User user1 = new User("bent1234", "bent1234", new Caretaker(), new UserInfo(new Address("Solsikkemarken", "Danmark", 5260, "Odense M", "18", "1"),
                null, 21212121, "Samuel", "Bangslund", "samuelbanglund@gmail.com","handyCenter"));
        User user2 = new User("tove1234", "tove1234", new Caretaker(), new UserInfo(new Address("Solsikkemarken", "Danmark", 5260, "Odense M", "18", "1"),
                null, 21212121, "Victor", "Clemmensen", "samuelbanglund@gmail.com",
                "handyCenter"));
        User user3 = new User("hans1234", "hans1234", new Caretaker(), new UserInfo(new Address("Solsikkemarken", "Danmark", 5260, "Odense M", "18", "1"),
                null, 21212121, "Samuel", "Bangslund", "samuelbanglund@gmail.com",
                "handyCenter"));
        User.setCurrentUser(user2);

        //Parent root = FXMLLoader.load(getClass().getResource("../Management/views/FXMLManager.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/aservio/platform/FXMLLogin.fxml"));

        Scene scene = new Scene(root);

        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.setTitle("Aservio");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
        //UserInfo(Address address, Image image, String mobileNumber, String name, String mail, Address institution) {
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Aservio getInstance() {
        return instance;
    }

}
