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

    public ILoginWithDBImp(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String checkForNoIllegalInput(String username, String password) {
        //Is Input empty?
        if (username.isEmpty() || password.isEmpty()) {
            return "Write your username and password to log in.";
        }
        String s =  InputLimitations.userLogin(username) ? "inputOK" : "USERNAME contains special characters. Only 0-9, a-z and _ is permitted";
        if (s.equals("inputOK")){
            return InputLimitations.userLogin(password) ? "inputOK" : "PASSWORD contains special characters. Only 0-9, a-z and _ is permitted";
        }
        else{
            return s;
        }
    }

    @Override
    public String verifyUser(String username, String password) {
        return repository.verifyUser(username, password);
    }

    @Override
    public void setUser(String username, String password) {
        User.setCurrentUser(getUser(username, password));
    }

    @Override
    public User getUser(String username, String password) {
        return repository.getUser(username, password);
    }

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
