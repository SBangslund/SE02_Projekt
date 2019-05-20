package aservio.presentation.platform.interfaces.contracts;

import aservio.domain.platform.user.User;

public interface ILogin {

    void tempUserSetupByFile(); //Only used by ILoginWithFileImp

    String checkForNoIllegalInput(String username, String password);

    String verifyUser(String username, String password);

    User getUser(String username, String password);

    void setUser(String username, String password);

    void loadScene();
    
    //void reaquireUsernameMail(); //WIP
    //void resetPassword(); //WIP
}
