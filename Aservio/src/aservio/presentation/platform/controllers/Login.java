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

    public ImageView logoImageView;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private final ILogin interFace = PresentationInterfaceManager.getILogin();

    private File file;
    @FXML
    private Label inputWarningLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File file = new File("resources/logo/LogoLarge.png");
        Image logo = new Image(file.toURI().toString());
        logoImageView.setImage(logo);
        setUsersFile();
    }

    private void setUsersFile() {
        file = new File("users.txt");
        try {
            //TEMPORARY
            if (!file.exists()) {//Use once pr new file.
                file.createNewFile();
                //fake users created to test read, as a temporary solution.
                User user1 = new User("q", "q", new Caretaker(), new UserInfo(new Address("Solsikkemarken", "Danmark", 5260, "Odense M", "18", "1"),
                        null, 21212121, "Samuel", "Bangslund", "samuelbanglund@gmail.com", "handyCenter"));
                User user2 = new User("tove_1234", "rambo", new Caretaker(), new UserInfo(new Address("Solsikkemarken", "Danmark", 5260, "Odense M", "18", "1"),
                        null, 21212121, "Victor", "Clemmensen", "samuelbanglund@gmail.com",
                        "handyCenter"));
                User user3 = new User("Slagteren", "affaldssortering", new Caretaker(), new UserInfo(new Address("Solsikkemarken", "Danmark", 5260, "Odense M", "18", "1"),
                        null, 21212121, "Rene", "Bangslund", "samuelbanglund@gmail.com",
                        "handyCenter"));
                writeToFile(user1);
                appendWriteTOFile(user2);
                appendWriteTOFile(user3);
            }
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        interFace.setFile("users.txt");
        System.out.println("File set: " + file);
    }

    /**
     * Clicking the login button or hitting enter logs the user in, if the input
     * is acceptable.
     *
     * @param event
     */
    @FXML
    private void validateLogin(ActionEvent event) {
        String result = interFace.checkForNoIllegalInput(usernameField.getText(), passwordField.getText());
        if (result.equals("Access")) {
            //TEMP, read file, set current user, return true if succesful.
            try {
                Parent p = FXMLLoader.load(getClass().getResource("/aservio/presentation/platform/views/FXMLPlatform.fxml"));
                Aservio.getInstance().getPrimaryStage().getScene().setRoot(p);
                Aservio.getInstance().getPrimaryStage().setMaximized(true);
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            inputWarningLabel.setText(result);
        }
    }

    private void writeToFile(User u) {
        try (
                FileOutputStream fos = new FileOutputStream(file, true);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(u);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("Could not find file or read from file " + file.getAbsolutePath());
        }
    }

    private void appendWriteTOFile(User s) {
        try (
                FileOutputStream fos = new FileOutputStream(file, true);
                AppendingObjectOutputStream oos = new AppendingObjectOutputStream(fos)) {

            oos.writeObject(s);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("Could not find file or read from file " + file.getAbsolutePath());
        }
    }

    /**
     * Enter on login button equals pressing it.
     * @param event 
     */
    @FXML
    private void onLoginBtnEnter(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)){
            validateLogin(new ActionEvent());
        }
    }

    private class AppendingObjectOutputStream extends ObjectOutputStream {

        public AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }
}
