package aservio.presentation.platform.controllers;

import aservio.domain.platform.Aservio;
import aservio.domain.platform.user.Address;
import aservio.domain.platform.user.User;
import aservio.domain.platform.user.UserInfo;
import aservio.domain.platform.user.roles.Caretaker;
import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.platform.interfaces.contracts.ILogin;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Login implements Initializable {

    private final ILogin interFace = PresentationInterfaceManager.getILogin();

    public ImageView logoImageView;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label inputWarningLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File file = new File("resources/logo/LogoLarge.png");
        Image logo = new Image(file.toURI().toString());
        logoImageView.setImage(logo);
        interFace.tempUserSetupByFile();
    }

    /**
     * WIP Clicking the login button or hitting enter logs the user in, if the
     * input is acceptable. checkForNoIllegalInput returns a string "Access" if
     * validated, else returns a string containing an error message.
     *
     * @param event
     */
    @FXML
    private void validateLogin(ActionEvent event) {
        String result = interFace.checkForNoIllegalInput(usernameField.getText(), passwordField.getText());
        if (result.equals("Access")) {
            interFace.loadScene();
        } else {
            inputWarningLabel.setText(result);
        }
    }

    /**
     * Enter on login button equals pressing it.
     *
     * @param event
     */
    @FXML
    private void onLoginBtnEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            validateLogin(new ActionEvent());
        }
    }
}
