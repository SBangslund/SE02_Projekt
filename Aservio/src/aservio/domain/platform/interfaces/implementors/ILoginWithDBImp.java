package aservio.domain.platform.interfaces.implementors;

import aservio.domain.platform.Aservio;
import aservio.domain.platform.Repository;
import aservio.presentation.platform.controllers.Login;
import aservio.presentation.platform.interfaces.contracts.ILogin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ILoginWithDBImp implements ILogin {

    private final Repository repository;

    //Limits for keyboardinputs in username and password.
    private final char minCapLetter = 'A'; //65; //A-Z
    private final char maxCapLetter = 'Z'; //90;
    private final char minLetter = 'a'; //97; //a-z
    private final char maxLetter = 'z'; //122;
    private final char charUnderscore = '_'; //95; //_
    private final char minNumber = '0'; //48; //0-9
    private final char maxNumber = '9'; //57;

    public ILoginWithDBImp(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String checkForNoIllegalInput(String username, String password) {
        //Is Input empty?
        if (username.isEmpty() || password.isEmpty()) {
            return "Write your username and password to log in.";
        }
        //Is Input in username ILLEGALCHARACTER?
        for (char j : username.toCharArray()) {
            boolean capitalLetterRange = j >= minCapLetter && j <= maxCapLetter;
            boolean smallLetterRange = j >= minLetter && j <= maxLetter;
            boolean numberRange = j >= minNumber && j <= maxNumber;
            if (!(capitalLetterRange || smallLetterRange || numberRange || j == charUnderscore)) {
                return "USERNAME contains special characters. Only 0-9, a-z and _ is permitted";
            }
        }
        //Is Input in username ILLEGALCHARACTER?
        for (char j : password.toCharArray()) {
            boolean capitalLetterRange = j >= minCapLetter && j <= maxCapLetter;
            boolean smallLetterRange = j >= minLetter && j <= maxLetter;
            boolean numberRange = j >= minNumber && j <= maxNumber;
            if (!(capitalLetterRange || smallLetterRange || numberRange || j == charUnderscore)) {
                return "PASSWORD contains special characters. Only 0-9, a-z and _ is permitted";
            }
        }
        return "inputOK";
    }
    //Is Input a user?

    /* if (!findUserInFile(username, password)) {
            return "Wrong username or password";
        }
        //All constraints are followed.
        return "Access";
    }
            //Is Input a user?
        if (verifyUser(username, password).equals(maxNumber)) {
            return "Wrong username or password";
        }
        //All constraints are followed.
        return "Unexpected error, returned true, but unexpected string.";
    }*/

    @Override
    public String verifyUser(String username, String password) {
        return repository.verifyUser(username, password);
    }

    @Override
    public void loadScene() {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/aservio/presentation/platform/views/Platform.fxml"));
            Aservio.getInstance().getPrimaryStage().getScene().setRoot(p);
            Aservio.getInstance().getPrimaryStage().setMaximized(true);
        } catch (IOException ex) {
            System.out.println("Couldn't load Platform.fxml file.");
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void tempUserSetupByFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
