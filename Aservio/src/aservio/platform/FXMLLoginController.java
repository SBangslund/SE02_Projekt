package aservio.platform;

import aservio.platform.user.Address;
import aservio.platform.user.User;
import aservio.platform.user.UserInfo;
import aservio.platform.user.roles.Caretaker;
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
import java.nio.file.Path;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXMLLoginController implements Initializable {

    public ImageView logoImageView;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private static final char[] ILLEGALCHARACTERS = new char[]{ //use legal characters instead a-å
        ';', '"', '\\'
    };
    private char minCapLetter = 65;
    private char maxCapLetter = 90;
    private char minLetter = 97;
    private char maxLetter = 122;
    private char charUnderscore = 95;
    private char minNumber = 48;
    private char maxNumber = 57;

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
                User user1 = new User("Skurk", "grill", new Caretaker(), new UserInfo(new Address("Solsikkemarken", "Danmark", 5260, "Odense M", "18", "1"),
                        null, 21212121, "Skurl", "Bangslund", "samuelbanglund@gmail.com", "handyCenter"));
                User user2 = new User("xXpu55yde5tr0yerXx", "tove1234", new Caretaker(), new UserInfo(new Address("Solsikkemarken", "Danmark", 5260, "Odense M", "18", "1"),
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
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("File set: " + file);
    }

    /**
     * Clicking the login button or hitting enter logs the user in, if the input
     * is acceptable.
     *
     * @param event
     */
    @FXML
    private void attemptLogin(ActionEvent event) {
        if (checkForNoIllegalInput()) {
            //TEMP, read file, set current user, return true if succesful.
            try {
                URL file = new File("src/aservio/platform/views/FXMLPlatform.fxml").toURI().toURL();
                Parent p = FXMLLoader.load(file);
                Aservio.getInstance().getPrimaryStage().getScene().setRoot(p);
                Aservio.getInstance().getPrimaryStage().setMaximized(true);
            } catch (IOException ex) {
                Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * //Tests login input for errors like illegal characters, spaces, and
     * correct account identification.
     *
     * @return
     */
    private boolean checkForNoIllegalInput() {
        String tempUser = usernameField.getText();
        String tempPassword = passwordField.getText();

        //Is Input empty?
        if (tempUser.isEmpty() || tempPassword.isEmpty()) {
            inputWarningLabel.setText("Write your username and password to log in");
            return false;
        }

        //Is Input in username ILLEGALCHARACTER?
        for (char j : tempUser.toCharArray()) {
            boolean capitalLetterRange = j >= minCapLetter && j <= maxCapLetter;
            boolean smallLetterRange = j >= minLetter && j <= maxLetter;
            boolean numberRange = j >= minNumber && j <= maxNumber;
            if (!(capitalLetterRange || smallLetterRange || numberRange || j == charUnderscore)) {
                inputWarningLabel.setText("USERNAME contains special characters. Only 0-9, a-z and _ is permitted");
                System.out.println("USERNAME contains special characters. Only 0-9, a-z and _ is permitted");
                return false;
            }
        }
        //Is Input in username ILLEGALCHARACTER?
        for (char j : tempPassword.toCharArray()) {
            boolean capitalLetterRange = j >= minCapLetter && j <= maxCapLetter;
            boolean smallLetterRange = j >= minLetter && j <= maxLetter;
            boolean numberRange = j >= minNumber && j <= maxNumber;
            if (!(capitalLetterRange || smallLetterRange || numberRange || j == charUnderscore)) {
                inputWarningLabel.setText("PASSWORD contains special characters. Only 0-9, a-z and _ is permitted");
                System.out.println("PASSWORD contains special characters. Only 0-9, a-z and _ is permitted");
                return false;
            }
        }
        /*//Is Input in username ILLEGALCHARACTER?
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
        }*/
        //Is Input a user?
        if (!findUserInFile(usernameField.getText(), passwordField.getText())) {
            inputWarningLabel.setText("Wrong username or password");
            return false;
        }
        //All constraints are followed.
        return true;
    }

    @FXML
    private void checkUserInput(ActionEvent event) {
    }

    private boolean findUserInFile(String username, String password) {
        boolean cont = true;
        int count = 0;
        List<User> users = new ArrayList();
        try {
            FileInputStream fileStream = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileStream);

            while (cont) {
                Object o = objectIn.readObject();
                if (o != null) {
                    if (o instanceof User) {
                        users.add((User) o);
                        System.out.println(o);
                    }
                }
                count++;
            }
        } catch (EOFException ex) {
            System.out.println("reached end of file");
            System.out.println("count: " + count);
            System.out.println("--------");
            for (User u : users) {
                if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                    System.out.println("User with correct username and password found");
                    User.setCurrentUser(u);
                    System.out.println("The current users username is: " + User.getCurrentUser().getUsername());
                    return true;
                }
            }
        } catch (IOException ex) {
            System.err.println("----------");
            System.err.println("Could not find file or read from file. " + file.getAbsolutePath());
            System.err.println("Error usually caused by corrupted or modified file or path. Try deleting the file (see path above).");

        } catch (ClassNotFoundException ex) {
            System.out.println("Could not find the class User");
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void writeToFile(User u) {
        try (
                FileOutputStream fos = new FileOutputStream(file, true);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(u);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("Could not find file or read from file " + file.getAbsolutePath());
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
