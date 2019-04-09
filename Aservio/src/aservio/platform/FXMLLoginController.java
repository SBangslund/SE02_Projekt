package aservio.platform;

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

    private static final char[] ILLEGALCHARACTERS = new char[]{
        ';', '"', '\\'
    };

    private String correctUsername = "q";
    private String correctPassword = "q";
    @FXML
    private Label wrongPasswordLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    //If input is correct, hitting enter og clicking the loginbutton logs the user in.
    @FXML
    private void tryLogin(ActionEvent event) {
        if (checkForNoIllegalInput()) {
            //load next scene
            System.out.println("hey you made it");
            try {
                URL file = new File("src/aservio/management/views/FXMLManager.fxml").toURI().toURL();
                Parent p = FXMLLoader.load(file);
                Aservio.getInstance().getStage().setScene(new Scene(p));
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
            wrongPasswordLabel.setText("Write your username and password to log in");
            return false;
        }
        //Is Input in username ILLEGALCHARACTER?
        for (char J : ILLEGALCHARACTERS) {
            if (tempUser.contains(Character.toString(J))) {
                wrongPasswordLabel.setText("Username or password contains special characters: ; / \\ , . % &");
                return false;
            }
        }
        //Is Input in password ILLEGALCHARACTER?
        for (char J : ILLEGALCHARACTERS) {
            if (tempPassword.contains(Character.toString(J))) {
                wrongPasswordLabel.setText("Username or password contains special characters: ; / \\ , . % &");
                return false;
            }
        }
        //Is Input a user?
        if (!usernameField.getText().equals(correctUsername) && !passwordField.getText().equals(correctPassword)) {
            wrongPasswordLabel.setText("Wrong username or password");
            return false;
        }
        //All constraints are followed.
        return true;
    }
}
