/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.platform;

import aservio.management.overview.Overview;
import aservio.management.overview.OverviewMonth;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author victo
 */
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

    private String correctUsername = "qwerty";
    private String correctPassword = "qwerty";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    //Checks whether the inputs from the user are correct
    @FXML
    private void checkUserInput(ActionEvent event) {
        if (allowableInput()) {
            //load next scene
            System.out.println("hey you made it");
            Overview view = new OverviewMonth();

            try {
                view.setView(FXMLLoader.load(new URL("aservio/management/views/FXMLOverviewMonth.fxml")));
//                FXMLLoader.load(getClass().getResource("../management/views/FXMLManager.fxml"));
//                System.out.println("akjsda");
//                System.out.println(FXMLLoader.load(getClass().getResource("FXMLLogin.fxml")).toString());
//                view.setView(FXMLLoader.load(getClass().getResource("../management/views/FXMLManager.fxml")));
            } catch (IOException ex) {
                Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
                view.show();
        }
    }

    private boolean allowableInput() {
        String tempUser = usernameField.getText();
        String tempPassword = passwordField.getText();

        //Is Input empty?
        if (tempUser.isEmpty() && tempPassword.isEmpty()) {
            return false;
        }
        //Is Input in username ILLEGALCHARACTER?
        for (char J : ILLEGALCHARACTERS) {
            if (tempUser.contains(Character.toString(J))) {
                return false;
            }
        }
        //Is Input in password ILLEGALCHARACTER?
        for (char J : ILLEGALCHARACTERS) {
            if (tempPassword.contains(Character.toString(J))) {
                return false;
            }
        }
        //Is Input a user?
        if (!usernameField.getText().equals(correctUsername) && !passwordField.getText().equals(correctPassword)) {
            return false;
        }
        //All constraints are followed.
        return true;
    }
}
