package aservio.platform;

import aservio.platform.user.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLLoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private static final char[] ILLEGALCHARACTERS = new char[]{ //use legal characters instead a-å
        ';', '"', '\\'
    };

    private String correctUsername = "q";
    private String correctPassword = "q";
    @FXML
    private Label inputWarningLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    //If input is correct, hitting enter og clicking the loginbutton logs the user in.
    @FXML
    private void attemptLogin(ActionEvent event) {
        if (checkForNoIllegalInput()) {
            //Log in username password - returns ID
            //get object via object inputstream
            //set singleton user reference to inputstream
            //User.setCurrentUser(objectstreamreference);
            
            //singleton (overvejes)
            //load next scene
            System.out.println("hey you made it");
            try { //flyttes ned i persistens data laget
                URL file = new File("src/aservio/management/views/FXMLManager.fxml").toURI().toURL();
                Parent p = FXMLLoader.load(file);
                Aservio.getInstance().getPrimaryStage().setScene(new Scene(p));
            } catch (IOException ex) {
                Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(getClass().getResource("FXMLOverviewMonth.fxml"));
            }
        }
    }

    //Controls the logininput for errors like illegal characters, spaces, and correct account identifikation.
    private boolean checkForNoIllegalInput() {
        String tempUser = usernameField.getText();
        String tempPassword = passwordField.getText();

        //Is Input empty?
        if (tempUser.isEmpty() || tempPassword.isEmpty()) {
            inputWarningLabel.setText("Write your username and password to log in");
            return false;
        }
        //Is Input in username ILLEGALCHARACTER?
        for (char J : ILLEGALCHARACTERS) {
            if (tempUser.contains(Character.toString(J))) {
                inputWarningLabel.setText("Username or password contains special characters: ; / \\ , . % &");
                return false;
            }
        }
        //Is Input in password ILLEGALCHARACTER?
        for (char J : ILLEGALCHARACTERS) {
            if (tempPassword.contains(Character.toString(J))) {
                inputWarningLabel.setText("Username or password contains special characters: ; / \\ , . % &");
                return false;
            }
        }
        //Is Input a user?
        //I en senere udgave skal der oprettes en loginAuthentiocation som tager brugernavn og kode i constructoren.
        if (!usernameField.getText().equals(correctUsername) && !passwordField.getText().equals(correctPassword)) {
            inputWarningLabel.setText("Wrong username or password");
            return false;
        }
        //All constraints are followed.
        return true;
    }

    @FXML
    private void checkUserInput(ActionEvent event) {
    }
}
