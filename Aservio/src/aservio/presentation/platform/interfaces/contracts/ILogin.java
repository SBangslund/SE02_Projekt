/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aservio.presentation.platform.interfaces.contracts;

/**
 *
 * @author Sigur
 */
public interface ILogin {
    
    void setFile(String fileName); //WIP Replace in data layer
    void tempUserSetupByFile(); //WIP Replace in data layer
    String checkForNoIllegalInput(String username, String password);
    String validateLogin(String username, String password);
    void loadScene();
    //void reaquireUsernameMail(); //WIP
    //void resetPassword(); //WIP
}

