package aservio.presentation.platform.controllers;

import aservio.domain.platform.user.User;
import aservio.presentation.PresentationInterfaceManager;
import aservio.presentation.platform.interfaces.contracts.ILogin;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        //interFace.tempUserSetupByFile();
    }

    /**
     * WIP Clicking the login button or hitting enter logs the user in, if the
     * input is acceptable. checkForNoIllegalInput returns a string "Access" if
     * validated, else returns a string containing an error message displaying
     * it to the user.
     *
     * @param event
     */
    @FXML
    private void validateLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        String result = interFace.checkForNoIllegalInput(username, password);
        if (result.equals("inputOK")) {
            String verifyResult = interFace.verifyUser(username, password);
            if (verifyResult.equals("true")) {
                interFace.setUser(username, password);
                interFace.loadScene();
            }
            else{
                inputWarningLabel.setText(verifyResult);
            }
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
