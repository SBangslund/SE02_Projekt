package aservio.domain.platform.interfaces.implementors;

import aservio.domain.platform.Aservio;
import aservio.domain.platform.InputLimitations;
import aservio.domain.platform.Repository;
import aservio.domain.platform.user.User;
import aservio.presentation.platform.controllers.Login;
import aservio.presentation.platform.interfaces.contracts.ILogin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ILoginWithDBImp implements ILogin {

    private final Repository repository;

    /**
     * Implementation classes utilize the repository to access the datalayer. 
     * They also implement interfaces (contracts) that specify their functionality.
     * @param repository 
     */
    public ILoginWithDBImp(Repository repository) {
        this.repository = repository;
    }
    
    /**
     * Manages login userinput and checks for bad input. 
     * This is done through the static InputLimitations.userLogin method,
     * that utilizes regex to return a boolean. The result is returned to the presentation layer as Strings.
     * @param username String from the textfield usernameField.
     * @param password String from the textfield passwordField.
     * @return Returns a String inputOK or String containing an error message.
     */
    @Override
    public String checkForNoIllegalInput(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return "Write your username and password to log in.";
        }
        String s = InputLimitations.userLogin(username) ? "inputOK" : "USERNAME contains special characters. Only 0-9, a-z and _ is permitted";
        if (s.equals("inputOK")) {
            return InputLimitations.userLogin(password) ? "inputOK" : "PASSWORD contains special characters. Only 0-9, a-z and _ is permitted";
        } else {
            return s;
        }
    }

    /**
     * verifyUser verifies if the userinput matches a user. The result is returned as a String. 
     * This specific method grants access to the repository from the domain layer.
     * @param username String from the textfield usernameField.
     * @param password String from the textfield passwordField.
     * @return String 
     */
    @Override
    public String verifyUser(String username, String password) {
        return repository.verifyUser(username, password);
    }

    /**
     * setUser sets a global (static) reference for the user that logs in.
     * This can afterwards be accessed by other parts of Aservio.
     * @param username String from the textfield usernameField.
     * @param password String from the textfield passwordField.
     */
    @Override
    public void setUser(String username, String password) {
        User.setCurrentUser(getUser(username, password));
    }

    /**
     * getUser returns a User object from the database.
     * This specific method grants access to the repository from the domain layer.
     * @param username String from the textfield usernameField.
     * @param password String from the textfield passwordField.
     * @return User object containing UserInfo, related activities and Address.
     */
    @Override
    public User getUser(String username, String password) {
        return repository.getUser(username, password);
    }

    /**
     * Responsible for loading the next scene. This is run on successful login.
     */
    @Override
    public void loadScene() {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/aservio/presentation/platform/views/Platform.fxml"));
            Aservio.getInstance().getPrimaryStage().getScene().setRoot(p);
            Aservio.getInstance().getPrimaryStage().setMaximized(true);
        } catch (IOException ex) {
            System.err.println("Couldn't load Platform.fxml file.");
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override //Not used with DB
    public void tempUserSetupByFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
